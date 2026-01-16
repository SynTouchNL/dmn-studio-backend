package nl.syntouch.dmn.studio.resource.custom;

import jakarta.annotation.security.RolesAllowed;
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

import static nl.syntouch.dmn.studio.DmnStudioConstants.*;

@Path("/dmns")
@ApplicationScoped
@AllArgsConstructor
@Produces("application/json")
@Consumes("application/json")
public class DmnResource {

    private final DmnService dmnService;
    private final DmnVersionService dmnVersionService;

    @POST
    @RolesAllowed({ROLE_ADMIN, ROLE_DEVELOPER, ROLE_DEPLOYER})
    public Response createDMN(DMNCreateDTO dmnDTO) {
        DMN createdDmn = dmnService.createDmn(dmnDTO);
        return Response.created(URI.create("/dmns/" + createdDmn.getId()))
                .entity(createdDmn)
                .build();
    }

    @GET
    @RolesAllowed({ROLE_ADMIN, ROLE_DEVELOPER, ROLE_APPROVER, ROLE_DEPLOYER, ROLE_READ})
    public Response getDMNs() {
        List<DMN> dmns = dmnService.getDMNs();
        return Response.ok(dmns).build();
    }

    @Path("/{dmnId}/")
    @POST
    @RolesAllowed({ROLE_ADMIN, ROLE_DEVELOPER, ROLE_DEPLOYER})
    public Response addVersion(@PathParam("dmnId") Long dmnId, DMNVersionCreateDTO versionDTO) {
        DMNVersion dmnVersion = dmnVersionService.addVersion(dmnId, versionDTO);
        return Response.created(URI.create("/dmns/%s/%s".formatted(dmnId, dmnVersion.getVersion())))
                .entity(dmnVersion)
                .build();
    }

    @Path("/{dmnId}/{versionId}/file")
    @GET
    @RolesAllowed({ROLE_ADMIN, ROLE_DEVELOPER, ROLE_APPROVER, ROLE_DEPLOYER, ROLE_READ})
    public Response getFile(@PathParam("dmnId") Long dmnId, @PathParam("versionId") Long versionId) {
        DMNVersion dmnVersion =  dmnVersionService.getDmnVersion(dmnId, versionId);
        DMNVersionDTO dto = new DMNVersionDTO(dmnVersion.getDmn().getId(), dmnVersion.getVersion(), dmnVersion.getFileBlob(), dmnVersion.getStatus());
        return Response.ok(dto).build();
    }

    @Path("/{dmnId}/{versionId}/file")
    @PUT
    @RolesAllowed({ROLE_ADMIN, ROLE_DEVELOPER, ROLE_DEPLOYER})
    public Response updateFile(@PathParam("dmnId") Long dmnId, @PathParam("versionId") Long versionId, DMNUpdateFileDTO versionDTO) {
        dmnVersionService.updateFile(dmnId, versionId, versionDTO);
        return Response.ok(versionDTO).build();
    }
}
