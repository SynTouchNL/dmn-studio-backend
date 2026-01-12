package nl.syntouch.dmn.studio.resource.custom;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import nl.syntouch.dmn.studio.model.Deployment;
import nl.syntouch.dmn.studio.model.dto.DeploymentDTO;
import nl.syntouch.dmn.studio.service.DmnDeploymentService;
import java.util.List;

import static nl.syntouch.dmn.studio.DmnStudioConstants.*;

@Path("/deployments")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class DeploymentResource {

    @Inject
    DmnDeploymentService dmnDeploymentService;

    @GET
    @RolesAllowed({ROLE_ADMIN, ROLE_DEPLOYER, ROLE_DEVELOPER})
    public List<DeploymentDTO> getDeployments() {
        List<Deployment> deployments = Deployment.listAll();
        return deployments.stream()
                .map(dmnDeploymentService::getDeploymentDTO)
                .toList();
    }

    @GET
    @Path("/{deploymentId}")
    @RolesAllowed({ROLE_ADMIN, ROLE_DEPLOYER})
    public DeploymentDTO getDeploymentById(@PathParam("deploymentId") Integer deploymentId) {
        return dmnDeploymentService.getDeploymentDTO(Deployment.find("id=?1", deploymentId).firstResult());
    }

    @GET
    @Path("/{dmnId}/{versionId}/{envId}")
    @RolesAllowed({ROLE_ADMIN, ROLE_DEPLOYER, ROLE_DEVELOPER})
    public Long getDeploymentVersionInProduction(@PathParam("dmnId") Long dmnId, @PathParam("versionId") Long versionId, @PathParam("envId") Long envId) {
        Deployment foundDeployment = Deployment.find("deployedTo.id = ?1 and version.dmn.id = ?2 and version.version = ?3", envId, dmnId, versionId).firstResult();
        if (foundDeployment == null) {
            return 0L;
        }
            return foundDeployment.getVersion().getVersion();
    }
}
