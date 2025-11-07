package nl.syntouch.rest.custom;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import nl.syntouch.models.DTOs.DeploymentDTO;
import nl.syntouch.models.Deployment;

import java.util.List;

@Authenticated
@Path("/deployments")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class DeploymentResourceCustom {

    @GET
    public List<DeploymentDTO> getDeployments() {
        List<Deployment> deployments = Deployment.listAll();
        return deployments.stream()
                .map(deployment -> {
                    return new DeploymentDTO(deployment); // Constructor should handle enrichment
                })
                .toList();
    }

    @Path("/{deploymentId}")
    @GET
    public DeploymentDTO getDeploymentById(@PathParam("deploymentId") Integer deploymentId) {
        Deployment deployment = Deployment.find("id=?1", deploymentId).firstResult();
        if (deployment == null) {
            throw new NotFoundException("Deployment not found");
        }
        return new DeploymentDTO(deployment);
    }
}
