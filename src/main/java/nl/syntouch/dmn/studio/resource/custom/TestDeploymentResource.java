package nl.syntouch.dmn.studio.resource.custom;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import nl.syntouch.dmn.studio.model.dto.DeployTestDTO;
import nl.syntouch.dmn.studio.service.UnitTestService;

import java.io.IOException;

@Authenticated
@Path("/test-deployment")
@ApplicationScoped
@AllArgsConstructor
@Produces("application/json")
@Consumes("application/json")
public class TestDeploymentResource {

    private final UnitTestService unitTestService;


    @POST
    public Response deployTestDeployment(DeployTestDTO deployTestDTO) throws IOException {
        return unitTestService.handleTestDeployment(deployTestDTO);
    }

    @GET
    @Path("/{dmnId}/{version}")
    public Response getTests(@PathParam("dmnId") Long dmnId, @PathParam("version") Long version) {
        return unitTestService.getTests(dmnId, version);
    }
}
