package nl.syntouch.rest;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import nl.syntouch.models.DMN;
import nl.syntouch.models.DTOs.DeploymentDTO;
import nl.syntouch.models.Deployment;

import java.util.List;

@Path("/deployments")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class DeploymentResourceCustom {
    @Authenticated
    @GET
    public List<DeploymentDTO> getDeployments() {
        List<Deployment> deployments = Deployment.listAll();
        return deployments.stream()
                .map(deployment -> {
                    return new DeploymentDTO(deployment); // Constructor should handle enrichment
                })
                .toList();
    }
}
