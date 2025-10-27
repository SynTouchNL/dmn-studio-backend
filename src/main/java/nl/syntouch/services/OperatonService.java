package nl.syntouch.services;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import nl.syntouch.models.DMN;
import nl.syntouch.models.DMNVersion;
import nl.syntouch.models.DTOs.DeployDTO;
import nl.syntouch.models.Deployment;
import nl.syntouch.models.clientresponses.DeploymentResponse;
import nl.syntouch.models.forms.DeploymentForm;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import nl.syntouch.clients.OperatonClient;
import org.jboss.logging.Logger;

import java.util.Map;

@ApplicationScoped
public class OperatonService {

    @Inject
    @RestClient
    OperatonClient operatonClient;

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Transactional
    public DeploymentResponse deployDMN(DeploymentForm form, DeployDTO deployDTO) {
        Response resp = operatonClient.deployDMN(form);

        DeploymentResponse body = null;
        try {
            if (resp.hasEntity()) {
                body = resp.readEntity(DeploymentResponse.class);
            }

            if (resp.getStatus() >= 400) {
                throw new RuntimeException("Remote deploy failed: status=" + resp.getStatus() + ", body=" + body);
            }

            DMN selected_DMN = DMN.findById(deployDTO.dmn.getId());
            if (selected_DMN == null) {
                throw new IllegalStateException("DMN not found with id: " + deployDTO.dmn.getId());
            }

            DMNVersion selected_Version = selected_DMN.getVersions().get(deployDTO.version - 1);
            if (selected_Version == null) {
                throw new IllegalStateException("DMN Version not found");
            }

            if (selected_Version.getId() == null && selected_Version.getId() == null) {
                throw new IllegalStateException("Invalid DMNVersion: missing id/parent");
            }
            System.out.println("IDs: " + selected_DMN.getId() + " " + selected_Version.getVersion() + " " + deployDTO.environment.getId());
            long existing = Deployment.count("id = ?1 and version.version = ?2 and deployedTo.id = ?3", selected_DMN.getId(), selected_Version.getVersion(), deployDTO.environment.getId());
            System.out.println(existing == 0 ? "No existing deployment found." : "Existing deployment found: " + existing);

            if (existing > 0) {
                throw new RuntimeException("Deployment already exists");
            }

            // Create and populate new Deployment in database.
            Deployment new_deployment = new Deployment();
            new_deployment.setId(deployDTO.dmn.getId());
            System.out.println(deployDTO.dmn.getVersions());
            new_deployment.setVersion(selected_Version);
            new_deployment.setDeployedTo(deployDTO.environment);
            new_deployment.setDeployedBy("Mark Akkermans");
            new_deployment.setDeploymentRef(body.getId());
            new_deployment.persist();

            return body;
        } catch (RuntimeException re) {
            throw new RuntimeException("Error " + re.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Failed to call/parse remote deploy: " + e.getMessage() + " body=" + body, e);
        } finally {
            resp.close();
        }
    }

    public Map<String, Object> getDeployment(String deploymentId) {
        Response resp = operatonClient.getDeployment(deploymentId);

        String body = "";
        try {
            if (resp.hasEntity()) {
                body = resp.readEntity(String.class);
            }
            if (resp.getStatus() >= 400) {
                throw new RuntimeException("Remote get deployment failed: status=" + resp.getStatus() + ", body=" + body);
            }
            return MAPPER.readValue(body, new TypeReference<Map<String, Object>>() {});
        } catch (RuntimeException re) {
            throw new RuntimeException("Unexpected error during get deployment: " + re.getMessage() + " body=" + body, re);
        } catch (Exception e) {
            throw new RuntimeException("Failed to call/parse remote get deployment: " + e.getMessage() + " body=" + body, e);
        } finally {
            resp.close();
        }
    }

    public void deleteDeployment(String deploymentId, boolean cascade) {
        Response resp = operatonClient.deleteDeployment(deploymentId, cascade);

        String body = "";
        try {
            if (resp.hasEntity()) {
                body = resp.readEntity(String.class);
            }
            if (resp.getStatus() >= 400) {
                throw new RuntimeException("Remote delete deployment failed: status=" + resp.getStatus() + ", body=" + body);
            }
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception e) {
            throw new RuntimeException("Failed to call remote delete deployment: " + e.getMessage() + " body=" + body, e);
        } finally {
            resp.close();
        }
    }
}