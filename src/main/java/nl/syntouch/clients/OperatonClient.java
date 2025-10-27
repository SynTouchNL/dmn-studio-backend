package nl.syntouch.clients;

import io.vertx.ext.web.FileUpload;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.syntouch.models.forms.DeploymentForm;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "operaton-api")
@Path("/engine-rest")
@Produces(MediaType.APPLICATION_JSON)
public interface OperatonClient {

    @POST
    @Path("/deployment/create")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    Response deployDMN(DeploymentForm deploymentForm);

    @GET
    @Path("/deployment/{deploymentId}")
    Response getDeployment(@PathParam("deploymentId") String deploymentId);

    @POST
    @Path("/deployment/{deploymentId}/redeploy")
    Response redeployDeployment(@PathParam("deploymentId") String deploymentId);

    @DELETE
    @Path("/deployment/{deploymentId}")
    Response deleteDeployment(@PathParam("deploymentId") String deploymentId, @QueryParam("cascade") Boolean cascade);

    @GET
    @Path("/tenant")
    Response getTenants();
}
