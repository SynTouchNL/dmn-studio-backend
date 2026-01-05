package nl.syntouch.dmn.studio.resource.custom;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import nl.syntouch.dmn.studio.model.dto.DeployTestDTO;
import nl.syntouch.dmn.studio.service.UnitTestService;

import java.io.IOException;

import static nl.syntouch.dmn.studio.DmnStudioConstants.*;

@Path("/test-deployment")
@ApplicationScoped
@AllArgsConstructor
@Produces("application/json")
@Consumes("application/json")
public class TestDeploymentResource {

    private final UnitTestService unitTestService;

    @POST
    @RolesAllowed({ROLE_DEVELOPER, ROLE_DEPLOYER})
    public Response deployTestDeployment(DeployTestDTO deployTestDTO) throws IOException {
        return Response.ok(unitTestService.handleTestDeployment(deployTestDTO)).build();
    }

    @GET
    @Path("/{dmnId}/{version}/tests")
    @RolesAllowed({ROLE_DEVELOPER, ROLE_APPROVER, ROLE_DEPLOYER})
    public Response getTests(@PathParam("dmnId") Long dmnId, @PathParam("version") Long version) {
        return Response.ok(unitTestService.getTests(dmnId, version)).build();
    }

    @DELETE
    @Path("/{dmnId}/{version}/tests/{testId}")
    @RolesAllowed({ROLE_DEVELOPER, ROLE_DEPLOYER})
    public Response deleteTests(@PathParam("dmnId") Long dmnId, @PathParam("version") Long version, @PathParam("testId") Long testId) {
        unitTestService.deleteTest(dmnId, version, testId);
        return Response.noContent().build();
    }
}
