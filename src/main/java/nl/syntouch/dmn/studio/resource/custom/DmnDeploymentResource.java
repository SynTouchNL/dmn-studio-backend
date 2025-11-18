package nl.syntouch.dmn.studio.resource.custom;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import nl.syntouch.dmn.studio.model.dto.DeployDTO;
import nl.syntouch.dmn.studio.service.DmnDeploymentService;
import org.openapi.quarkus.operaton_rest_api_json.model.DeploymentWithDefinitionsDto;


import java.io.IOException;
import java.net.URI;

@Path("/deploy")
@Authenticated
@ApplicationScoped
@AllArgsConstructor
@Produces("application/json")
@Consumes("application/json")
public class DmnDeploymentResource {

    private final DmnDeploymentService dmnDeploymentService;

    @POST
    public Response createDeployment(DeployDTO deployDTO) throws IOException {
        DeploymentWithDefinitionsDto deployment = dmnDeploymentService.createDeployment(deployDTO);
        return Response
                .created(URI.create("/deploy/%s".formatted(deployment.getId())))
                .entity(deployment)
                .build();
    }

    @GET
    @Path("/info/{deploymentId}")
    public Response getDeployment(@PathParam("deploymentId") String deploymentId) {
        return Response
                .ok(dmnDeploymentService.getDeployment(deploymentId))
                .build();
    }

    @DELETE
    @Path("/{deploymentId}")
    public Response deleteDeployment(@PathParam("deploymentId") String deploymentId, @QueryParam("cascade") boolean cascade) {
        dmnDeploymentService.deleteDeployment(deploymentId, cascade);
        return Response.noContent().build();
    }

}
