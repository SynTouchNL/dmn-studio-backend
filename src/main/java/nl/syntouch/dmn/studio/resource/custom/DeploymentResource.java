package nl.syntouch.dmn.studio.resource.custom;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import nl.syntouch.dmn.studio.model.Deployment;
import nl.syntouch.dmn.studio.model.dto.DeploymentDTO;

import java.util.List;

@Authenticated
@Path("/deployments")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class DeploymentResource {

    @GET
    public List<DeploymentDTO> getDeployments() {
        List<Deployment> deployments = Deployment.listAll();
        return deployments.stream()
                .map(DeploymentResource::getDeploymentDTO)
                .toList();
    }

    @Path("/{deploymentId}")
    @GET
    public DeploymentDTO getDeploymentById(@PathParam("deploymentId") Integer deploymentId) {
        Deployment deployment = Deployment.find("id=?1", deploymentId).firstResult();
        if (deployment == null) {
            throw new NotFoundException("Deployment not found");
        }
        return getDeploymentDTO(deployment);
    }

    private static DeploymentDTO getDeploymentDTO(Deployment deployment) {
        return new DeploymentDTO(
                deployment.getId(),
                deployment.getDeployedBy(),
                deployment.getDeployedTime(),
                deployment.getDeployedTo().getName(),
                deployment.getVersion().getDmn().getId(),
                deployment.getVersion(),
                deployment.getVersion().getDmn(),
                deployment.getDeploymentRef()
        );
    }
}
