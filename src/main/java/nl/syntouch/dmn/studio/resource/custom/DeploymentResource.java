package nl.syntouch.dmn.studio.resource.custom;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import nl.syntouch.dmn.studio.model.Deployment;
import nl.syntouch.dmn.studio.model.dto.DeploymentDTO;
import nl.syntouch.dmn.studio.service.DmnDeploymentService;

import java.util.List;

@Authenticated
@Path("/deployments")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class DeploymentResource {

    static DmnDeploymentService dmnDeploymentService;

    @GET
    public List<DeploymentDTO> getDeployments() {
        List<Deployment> deployments = Deployment.listAll();
        return deployments.stream()
                .map(dmnDeploymentService::getDeploymentDTO)
                .toList();
    }

    @GET
    @Path("/{deploymentId}")
    public DeploymentDTO getDeploymentById(@PathParam("deploymentId") Integer deploymentId) {
        return dmnDeploymentService.getDeploymentDTO(Deployment.find("id=?1", deploymentId).firstResult());
    }

    @GET
    @Path("/{dmnId}/{versionId}/{envId}")
    public Long getDeploymentVersionInProduction(@PathParam("dmnId") Long dmnId, @PathParam("versionId") Long versionId, @PathParam("envId") Long envId) {
        Deployment foundDeployment = Deployment.find("deployedTo.id = ?1 and version.dmn.id = ?2 and version.version = ?3", envId, dmnId, versionId).firstResult();
        if (foundDeployment == null) {
            return 0L;
        }
            return foundDeployment.getVersion().getVersion();
    }
}
