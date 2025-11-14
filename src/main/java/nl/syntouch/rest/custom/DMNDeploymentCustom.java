package nl.syntouch.rest.custom;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import nl.syntouch.models.DTOs.DeployDTO;
import nl.syntouch.services.OperatonService;
import org.openapi.quarkus.operaton_rest_api_json.model.DeploymentWithDefinitionsDto;


import java.io.IOException;
import java.util.Map;

@Path("/deploy")
@Authenticated
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class DMNDeploymentCustom {

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
