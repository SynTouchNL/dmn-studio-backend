package nl.syntouch.rest;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import nl.syntouch.models.DMNVersion;
import nl.syntouch.models.DTOs.DMNVersionDTO;

@Path("/dmn/{dmnId}")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class DMNVersionFileResource {
    @Path("/{versionId}/file")
    @Authenticated
    @GET
    public DMNVersionDTO getFile(@PathParam("dmnId") Integer dmnId, @PathParam("versionId") Integer versionId) {
        DMNVersion entity = DMNVersion.getFile(dmnId, versionId);
        if (entity == null) {
            throw new NotFoundException("DMN version not found for dmnId " + dmnId + " and versionId " + versionId);
        }
        return new DMNVersionDTO(entity);
    }

}
