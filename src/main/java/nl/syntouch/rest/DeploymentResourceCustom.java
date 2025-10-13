package nl.syntouch.rest;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import nl.syntouch.models.Deployment;

import java.util.List;

@Path("/deployments")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class DeploymentResourceCustom {
    @Authenticated
    @GET
    public List<PanacheEntityBase> getDeployments() {
        return Deployment.listAll();
    }
}
