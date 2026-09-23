package nl.syntouch.dmn.studio.resource.custom;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.core.Context;
import lombok.RequiredArgsConstructor;
import nl.syntouch.dmn.studio.model.dto.DomainRequestDTO;
import nl.syntouch.dmn.studio.model.dto.DomainPageResponseDTO;
import nl.syntouch.dmn.studio.model.dto.DomainResponseDTO;
import nl.syntouch.dmn.studio.service.DomainService;

import static nl.syntouch.dmn.studio.DmnStudioConstants.*;

@Path("/domain")
@ApplicationScoped
@RequiredArgsConstructor
@Produces("application/json")
@Consumes("application/json")
public class DomainResource {
    private final DomainService service;

    @RolesAllowed({ROLE_ADMIN, ROLE_DEVELOPER, ROLE_APPROVER, ROLE_DEPLOYER, ROLE_READ})
    @GET
    public DomainPageResponseDTO list(@QueryParam("page") @DefaultValue("0") int page,
                                       @QueryParam("size") @DefaultValue("20") int size) {
        return service.list(page, size);
    }

    @RolesAllowed({ROLE_ADMIN, ROLE_DEVELOPER, ROLE_APPROVER, ROLE_DEPLOYER, ROLE_READ})
    @GET
    @Path("/{id}")
    public DomainResponseDTO get(@PathParam("id") Long id) { return service.get(id); }

    @POST
    @RolesAllowed(ROLE_ADMIN)
    public Response create(
            @Valid
            DomainRequestDTO request,
            @Context UriInfo uriInfo) {
        DomainResponseDTO result = service.create(request);
        return Response.created(uriInfo.getAbsolutePathBuilder().path(result.id().toString()).build()).entity(result).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed(ROLE_ADMIN)
    public DomainResponseDTO update(@PathParam("id") Long id,
                                    @Valid DomainRequestDTO request) {
        return service.update(id, request);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed(ROLE_ADMIN)
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
