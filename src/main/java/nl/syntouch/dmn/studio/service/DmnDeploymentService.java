package nl.syntouch.dmn.studio.service;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;
import lombok.RequiredArgsConstructor;
import nl.syntouch.dmn.studio.model.DMN;
import nl.syntouch.dmn.studio.model.DMNVersion;
import nl.syntouch.dmn.studio.model.Deployment;
import nl.syntouch.dmn.studio.model.composites.DMNVersionId;
import nl.syntouch.dmn.studio.model.dto.DeployDTO;
import nl.syntouch.dmn.studio.model.dto.DeploymentDMNDTO;
import nl.syntouch.dmn.studio.model.dto.DeploymentDTO;
import nl.syntouch.dmn.studio.repository.DeploymentRepository;
import nl.syntouch.dmn.studio.repository.DmnRepository;
import nl.syntouch.dmn.studio.repository.DmnVersionRepository;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.jboss.logging.Logger;
import org.openapi.quarkus.operaton_rest_api_json.api.DeploymentApi;
import org.openapi.quarkus.operaton_rest_api_json.model.DeploymentWithDefinitionsDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@ApplicationScoped
public class DmnDeploymentService {

    private static final Logger LOG = Logger.getLogger(DmnDeploymentService.class);

    @ConfigProperty(name = "quarkus.rest-client.operaton_rest_api_test.url")
    String testUrl;

    @ConfigProperty(name = "quarkus.rest-client.operaton_rest_api_acc.url")
    String accUrl;

    @ConfigProperty(name = "quarkus.rest-client.operaton_rest_api_prod.url")
    String prodUrl;

    @ConfigProperty(name = "operaton.context-path")
    String contextPath;

    private final SecurityIdentity identity;
    private final DmnRepository dmnRepository;
    private final DmnVersionRepository dmnVersionRepository;
    private final DeploymentRepository deploymentRepository;

    private String selectUrl(Long envId) {
        String envUrl = "";
        if (envId == 1L) {
            envUrl = testUrl;
        } else if (envId == 2L) {
            envUrl = accUrl;
        } else if (envId == 3L) {
            envUrl = prodUrl;
        } else {
            throw new NotFoundException("Environment not found");
        }
        return envUrl + contextPath;
    }

    @Transactional
    public DeploymentWithDefinitionsDto createDeployment(DeployDTO deployDTO) throws IOException {
        DeploymentApi customClient = RestClientBuilder.newBuilder()
                .baseUri(selectUrl(deployDTO.environment().getId()))
                .build(DeploymentApi.class);

        DMN dmn = dmnRepository.findByIdOptional(deployDTO.dmn().getId()).orElseThrow();
        DMNVersion dmnVersion = dmnVersionRepository.findByIdOptional(new DMNVersionId(dmn.getId(), deployDTO.version())).orElseThrow();

        var form = getCreateDeploymentMultipartForm(deployDTO, dmnVersion.getFileBlob());

        try {
            DeploymentWithDefinitionsDto deploymentWithDefinitionsDto = customClient.createDeployment(form);
            Deployment deployment = getDeployment(deployDTO, dmnVersion, deploymentWithDefinitionsDto);
            persistDeploymentOrCompensate(customClient, deployment, deploymentWithDefinitionsDto.getId());
            return deploymentWithDefinitionsDto;
        } catch (Exception e) {
            throw new IOException("Kan DMN niet deployen: " + e.getMessage());
        } finally {
            deleteTempFile(form.data);
        }
    }

    private void persistDeploymentOrCompensate(DeploymentApi customClient, Deployment deployment,
                                               String remoteDeploymentId) throws IOException {
        try {
            deploymentRepository.persistAndFlush(deployment);
        } catch (Exception persistenceException) {
            try {
                customClient.deleteDeployment(remoteDeploymentId, true, true, true);
            } catch (Exception compensationException) {
                persistenceException.addSuppressed(compensationException);
                LOG.errorf(compensationException,
                        "Could not remove Operaton deployment %s after local persistence failed",
                        remoteDeploymentId);
                throw new IOException(
                        "DMN is deployed in Operaton, but could not be stored locally or rolled back",
                        persistenceException);
            }
            throw new IOException(
                    "DMN deployment was rolled back because it could not be stored locally",
                    persistenceException);
        }
    }

    public Deployment getDeployment(DeployDTO deployDTO, DMNVersion dmnVersion, DeploymentWithDefinitionsDto deploymentWithDefinitionsDto) {
        Deployment deployment = new Deployment();
        deployment.setId(deployDTO.dmn().getId());
        deployment.setVersion(dmnVersion);
        deployment.setDeployedTo(deployDTO.environment());
        deployment.setDeployedBy(identity.getPrincipal().getName());
        deployment.setDeploymentRef(deploymentWithDefinitionsDto.getId());
        return deployment;
    }

    static DeploymentApi.CreateDeploymentMultipartForm getCreateDeploymentMultipartForm(DeployDTO deployDTO, byte[] data) throws IOException {
        var form = new DeploymentApi.CreateDeploymentMultipartForm();
        form.tenantId = deployDTO.tenantId();
        form.deploymentSource = deployDTO.deploymentSource();
        form.deployChangedOnly = deployDTO.deployChangedOnly();
        form.enableDuplicateFiltering = deployDTO.enableDuplicateFiltering();
        form.deploymentName = deployDTO.deploymentName();
        form.deploymentActivationTime = deployDTO.deploymentActivationTime();
        form.data = getFile(data);
        return form;
    }

    private static File getFile(byte[] data) throws IOException {
        var tempFile = Files.createTempFile("deployment-", ".dmn").toFile();
        try {
            Files.write(tempFile.toPath(), data);
            return tempFile;
        } catch (IOException e) {
            deleteTempFile(tempFile);
            throw e;
        }
    }

    static void deleteTempFile(File tempFile) {
        try {
            Files.deleteIfExists(tempFile.toPath());
        } catch (IOException e) {
            LOG.warnf(e, "Could not delete temporary DMN file %s", tempFile);
        }
    }

    public void deleteDeployment(Long deploymentId, Long envId) throws NotFoundException {
        Deployment deploymentFound = deploymentRepository.find("id = ?1", deploymentId).firstResult();
        if (deploymentFound == null) {
            throw new NotFoundException("Deployment not found in local database");
        }
        String deploymentRef = deploymentFound.getDeploymentRef();
        Long deployedEnvironmentId = deploymentFound.getDeployedTo().getId();
        if (!Objects.equals(envId, deployedEnvironmentId)) {
            throw new BadRequestException("Deployment belongs to environment " + deployedEnvironmentId);
        }

        DeploymentApi customClient = RestClientBuilder.newBuilder()
                .baseUri(selectUrl(deployedEnvironmentId))
                .build(DeploymentApi.class);
        try {
            customClient.deleteDeployment(deploymentRef, true, true, true);
        } catch (WebApplicationException e) {
            int status = e.getResponse().getStatus();
            if (status == 404) {
                throw new NotFoundException("Deployment not found in target environment", e);
            }
            throw new WebApplicationException(
                    "Operaton rejected deployment deletion with HTTP status " + status,
                    e,
                    status);
        } catch (ProcessingException e) {
            throw new WebApplicationException("Target Operaton environment is unavailable", e, 503);
        }
        deploymentRepository.delete(deploymentFound);
    }

    public DeploymentDMNDTO getDeploymentWithDMN(Long deploymentId) {
        Optional<Deployment> foundDeploymentOpt = Deployment.find("id = ?1", deploymentId).firstResultOptional();
        Deployment foundDeployment = foundDeploymentOpt.orElseThrow(() -> new NotFoundException("Deployment not found"));
        DMN foundDmn = foundDeployment.getVersion().getDmn();

        DeploymentDMNDTO.DMNVersionSubDTO subDTO = new DeploymentDMNDTO.DMNVersionSubDTO(
                foundDeployment.getVersion().getVersion(),
                foundDeployment.getVersion().getStatus(),
                foundDeployment.getVersion().getModifiedBy(),
                foundDeployment.getVersion().getModifiedDate(),
                foundDeployment.getVersion().getCreatedBy(),
                foundDeployment.getVersion().getCreatedDate()
        );
        return new DeploymentDMNDTO(
                foundDeployment.getId(),
                foundDmn.getId(),
                foundDeployment.getDeployedBy(),
                foundDeployment.getDeployedTime(),
                foundDeployment.getDeployedTo().getId(),
                foundDeployment.getDeployedTo().getName(),
                foundDeployment.getDeploymentRef(),
                subDTO,
                foundDmn);
    }

    public List<DeploymentDMNDTO> getDeploymentsWithDMN() {
        List<Deployment> deployments = Deployment.listAll();
        return deployments.stream().map(deployment -> {
            DMN dmn = deployment.getVersion().getDmn();
            DeploymentDMNDTO.DMNVersionSubDTO subDTO = new DeploymentDMNDTO.DMNVersionSubDTO(
                    deployment.getVersion().getVersion(),
                    deployment.getVersion().getStatus(),
                    deployment.getVersion().getModifiedBy(),
                    deployment.getVersion().getModifiedDate(),
                    deployment.getVersion().getCreatedBy(),
                    deployment.getVersion().getCreatedDate()
            );
            return new DeploymentDMNDTO(
                    deployment.getId(),
                    dmn.getId(),
                    deployment.getDeployedBy(),
                    deployment.getDeployedTime(),
                    deployment.getDeployedTo().getId(),
                    deployment.getDeployedTo().getName(),
                    deployment.getDeploymentRef(),
                    subDTO,
                    dmn);
        }).toList();
    }

    public DeploymentDTO getDeploymentDTO(Deployment deployment) {
        Objects.requireNonNull(deployment, "deployment is null");
        return new DeploymentDTO(
                deployment.getId(),
                deployment.getDeployedBy(),
                deployment.getDeployedTime(),
                deployment.getDeployedTo() != null ? deployment.getDeployedTo().getName() : null,
                deployment.getVersion() != null && deployment.getVersion().getDmn() != null ? deployment.getVersion().getDmn().getId() : null,
                deployment.getVersion(),
                deployment.getVersion() != null ? deployment.getVersion().getDmn() : null,
                deployment.getDeploymentRef()
        );
    }
}
