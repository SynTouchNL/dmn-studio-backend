package nl.syntouch.dmn.studio.resource.custom;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import nl.syntouch.dmn.studio.model.dto.DeployDTO;
import nl.syntouch.dmn.studio.service.OperatonService;
import org.openapi.quarkus.operaton_rest_api_json.model.DeploymentWithDefinitionsDto;


import java.io.IOException;

@Path("/deploy")
@Authenticated
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class DmnDeploymentResource {

    @Inject
    OperatonService operatonService;

    @POST
    public DeploymentWithDefinitionsDto createDeployment(DeployDTO deployDTO) throws IOException {
        return operatonService.createDeployment(deployDTO);
    }

    @GET
    @Path("/info/{deploymentId}")
    public Response getDeployment(@PathParam("deploymentId") String deploymentId) {
        return Response
                .ok(operatonService.getDeployment(deploymentId))
                .build();
    }

    @DELETE
    @Path("/{deploymentId}")
    public Response deleteDeployment(@PathParam("deploymentId") String deploymentId, @QueryParam("cascade") boolean cascade) {
        operatonService.deleteDeployment(deploymentId, cascade);
        return Response.noContent().build();
    }

}
