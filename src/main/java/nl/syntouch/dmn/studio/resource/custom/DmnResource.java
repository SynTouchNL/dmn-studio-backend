package nl.syntouch.dmn.studio.resource.custom;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import nl.syntouch.dmn.studio.model.DMN;
import nl.syntouch.dmn.studio.model.DMNVersion;
import nl.syntouch.dmn.studio.model.dto.*;
import nl.syntouch.dmn.studio.service.DmnService;
import nl.syntouch.dmn.studio.service.DmnVersionService;

import java.net.URI;
import java.util.List;

@Authenticated
@Path("/dmns")
@ApplicationScoped
@AllArgsConstructor
@Produces("application/json")
@Consumes("application/json")
public class DmnResource {

    private final DmnService dmnService;
    private final DmnVersionService dmnVersionService;

    @POST
    public Response createDMN(DMNCreateDTO dmnDTO) {
        DMN createdDmn = dmnService.createDmn(dmnDTO);
        return Response.created(URI.create("/dmns/" + createdDmn.getId()))
                .entity(createdDmn)
                .build();
    }

    @GET
    public Response getDMNs() {
        List<DMN> dmns = dmnService.getDMNs();
        return Response.ok(dmns).build();
    }

    @Path("/{dmnId}/")
    @POST
    public Response addVersion(@PathParam("dmnId") Long dmnId, DMNVersionCreateDTO versionDTO) {
        DMNVersion dmnVersion = dmnVersionService.addVersion(dmnId, versionDTO);
        return Response.created(URI.create("/dmns/%s/%s".formatted(dmnId, dmnVersion.getVersion())))
                .entity(dmnVersion)
                .build();
    }

    @Path("/{dmnId}/{versionId}/file")
    @GET
    public Response getFile(@PathParam("dmnId") Long dmnId, @PathParam("versionId") Long versionId) {
        DMNVersion dmnVersion =  dmnVersionService.getDmnVersion(dmnId, versionId);
        DMNVersionDTO dto = new DMNVersionDTO(dmnVersion.getDmn().getId(), dmnVersion.getVersion(), dmnVersion.getFileBlob(), dmnVersion.getStatus());
        return Response.ok(dto).build();
    }

    @Path("/{dmnId}/{versionId}/file")
    @PUT
    public Response updateFile(@PathParam("dmnId") Long dmnId, @PathParam("versionId") Long versionId, DMNUpdateFileDTO versionDTO) {
        dmnVersionService.updateFile(dmnId, versionId, versionDTO);
        return Response.ok(versionDTO).build();
    }
}
