package nl.syntouch.dmn.studio.resource.custom;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import nl.syntouch.dmn.studio.model.dto.deployment.DeployDTO;
import nl.syntouch.dmn.studio.service.DmnDeploymentService;
import org.openapi.quarkus.operaton_rest_api_json.model.DeploymentWithDefinitionsDto;


import java.io.IOException;
import java.net.URI;

import static nl.syntouch.dmn.studio.DmnStudioConstants.ROLE_ADMIN;
import static nl.syntouch.dmn.studio.DmnStudioConstants.ROLE_DEPLOYER;
import static nl.syntouch.dmn.studio.DmnStudioConstants.ROLE_DEVELOPER;

@Path("/deploy")
@ApplicationScoped
@AllArgsConstructor
@Produces("application/json")
@Consumes("application/json")
@RolesAllowed({ROLE_ADMIN, ROLE_DEPLOYER})
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
    @Path("/{deploymentId}")
    @RolesAllowed({ROLE_ADMIN, ROLE_DEPLOYER, ROLE_DEVELOPER})
    public Response getDeployment(@PathParam("deploymentId") Long deploymentId) {
        return Response
                .ok(dmnDeploymentService.getDeploymentWithDMN(deploymentId))
                .build();
    }

    @GET
    @Path("/")
    @RolesAllowed({ROLE_ADMIN, ROLE_DEPLOYER, ROLE_DEVELOPER})
    public Response getAllDeployments() {
        return Response
                .ok(dmnDeploymentService.getDeploymentsWithDMN())
                .build();
    }

    @DELETE
    @Path("/{envId}/{deploymentId}")
    public Response deleteDeployment(@PathParam("envId") Long envId, @PathParam("deploymentId") Long deploymentId) {
        dmnDeploymentService.deleteDeployment(deploymentId, envId);
        return Response.noContent().build();
    }

}
