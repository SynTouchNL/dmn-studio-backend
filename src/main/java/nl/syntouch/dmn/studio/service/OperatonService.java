package nl.syntouch.dmn.studio.service;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nl.syntouch.dmn.studio.model.DMN;
import nl.syntouch.dmn.studio.model.DMNVersion;
import nl.syntouch.dmn.studio.model.dto.DeployDTO;
import nl.syntouch.dmn.studio.model.Deployment;
import nl.syntouch.dmn.studio.model.composites.DMNVersionId;
import nl.syntouch.dmn.studio.repository.DeploymentRepository;
import nl.syntouch.dmn.studio.repository.DmnRepository;
import nl.syntouch.dmn.studio.repository.DmnVersionRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.openapi.quarkus.operaton_rest_api_json.api.DeploymentApi;
import org.openapi.quarkus.operaton_rest_api_json.model.DeploymentDto;
import org.openapi.quarkus.operaton_rest_api_json.model.DeploymentWithDefinitionsDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RequiredArgsConstructor
@ApplicationScoped
public class OperatonService {

    @RestClient
    DeploymentApi deploymentApi;

    private final SecurityIdentity identity;
    private final DmnRepository dmnRepository;
    private final DmnVersionRepository dmnVersionRepository;
    private final DeploymentRepository deploymentRepository;

    @Transactional
    public DeploymentWithDefinitionsDto createDeployment(DeployDTO deployDTO) throws IOException {
        DMN dmn = dmnRepository.findByIdOptional(Long.valueOf(deployDTO.dmn().getId())).orElseThrow();
        DMNVersion dmnVersion = dmnVersionRepository.findByIdOptional(new DMNVersionId(dmn.getId(), deployDTO.version())).orElseThrow();

        var form = getCreateDeploymentMultipartForm(deployDTO, dmnVersion.getFileBlob());
        DeploymentWithDefinitionsDto deploymentWithDefinitionsDto = deploymentApi.createDeployment(form);

        Deployment deployment = getDeployment(deployDTO, dmnVersion, deploymentWithDefinitionsDto);
        deploymentRepository.persist(deployment);

        return deploymentWithDefinitionsDto;
    }

    private Deployment getDeployment(DeployDTO deployDTO, DMNVersion dmnVersion, DeploymentWithDefinitionsDto deploymentWithDefinitionsDto) {
        Deployment deployment = new Deployment();
        deployment.setId(deployDTO.dmn().getId());
        deployment.setVersion(dmnVersion);
        deployment.setDeployedTo(deployDTO.environment());
        deployment.setDeployedBy(identity.getPrincipal().getName());
        deployment.setDeploymentRef(deploymentWithDefinitionsDto.getId());
        return deployment;
    }

    private static DeploymentApi.CreateDeploymentMultipartForm getCreateDeploymentMultipartForm(DeployDTO deployDTO, byte[] data) throws IOException {
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
        Files.write(tempFile.toPath(), data);
        return tempFile;
    }

    public DeploymentDto getDeployment(String deploymentId) {
        return deploymentApi.getDeployment(deploymentId);

    }

    public void deleteDeployment(String deploymentId, boolean cascade) {
        deploymentApi.deleteDeployment(deploymentId, cascade, true, true);
    }
}