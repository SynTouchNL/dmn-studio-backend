package nl.syntouch.rest.custom;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import nl.syntouch.models.DTOs.DeployDTO;
import nl.syntouch.models.clientresponses.DeploymentResponse;
import nl.syntouch.models.forms.DeploymentForm;
import nl.syntouch.services.OperatonService;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Map;

@Path("/deploy")
//@Authenticated
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class DMNDeploymentCustom {

    @Inject
    OperatonService operatonService;

    @POST
    public DeploymentResponse createDeployment(DeployDTO deployDTO) throws IOException {
        String decoded = new String(Base64.getDecoder().decode(deployDTO.data));
        File tempFile = Files.createTempFile("deployment-", ".dmn").toFile();
        Files.writeString(tempFile.toPath(), decoded, StandardCharsets.UTF_8);
        System.out.println("Created DMN deployment file: " + tempFile.getAbsolutePath());

        InputStream inputStream = new FileInputStream(tempFile);
        try {
            DeploymentForm form = new DeploymentForm();
            form.tenantId = deployDTO.tenantId;
            form.deploymentSource = deployDTO.deploymentSource;
            form.deployChangedOnly = deployDTO.deployChangedOnly;
            form.enableDuplicateFiltering = deployDTO.enableDuplicateFiltering;
            form.deploymentName = deployDTO.deploymentName;
            form.deploymentActivationTime = deployDTO.deploymentActivationTime;
            form.data = inputStream;

            return operatonService.deployDMN(form, deployDTO);
        } finally {
            inputStream.close();
        }
    }

    @GET
    @Path("/info/{deploymentId}")
    public Map<String, Object> getDeployment(@PathParam("deploymentId") String deploymentId) {
        return operatonService.getDeployment(deploymentId);
    }

    @DELETE
    @Path("/{deploymentId}")
    public Response deleteDeployment(@PathParam("deploymentId") String deploymentId, @QueryParam("cascade") boolean cascade) {
        operatonService.deleteDeployment(deploymentId, cascade);
        return Response.noContent().build();
    }

}
