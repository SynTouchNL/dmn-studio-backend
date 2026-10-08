package nl.syntouch.dmn.studio.resource.custom;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import nl.syntouch.dmn.studio.model.dto.ConnectionTestResultDTO;
import nl.syntouch.dmn.studio.model.dto.EnvironmentRequestDTO;
import nl.syntouch.dmn.studio.model.dto.EnvironmentResponseDTO;
import nl.syntouch.dmn.studio.service.EnvironmentService;

import java.util.List;

import static nl.syntouch.dmn.studio.DmnStudioConstants.ROLE_ADMIN;
import static nl.syntouch.dmn.studio.DmnStudioConstants.ROLE_DEPLOYER;

@Path("/environment")
@ApplicationScoped
@RequiredArgsConstructor
@Produces("application/json")
@Consumes("application/json")
public class EnvironmentResource {
    private final EnvironmentService service;

    @RolesAllowed({ROLE_ADMIN, ROLE_DEPLOYER})
    @GET
    public List<EnvironmentResponseDTO> list() { return service.list(); }

    @RolesAllowed({ROLE_ADMIN, ROLE_DEPLOYER})
    @GET
    @Path("/{id}")
    public EnvironmentResponseDTO get(@PathParam("id") Long id) { return service.get(id); }

    @POST
    @RolesAllowed(ROLE_ADMIN)
    public Response create(@Valid EnvironmentRequestDTO request, @Context UriInfo uriInfo) {
        EnvironmentResponseDTO result = service.create(request);
        return Response.created(uriInfo.getAbsolutePathBuilder().path(result.id().toString()).build()).entity(result).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed(ROLE_ADMIN)
    public EnvironmentResponseDTO update(@PathParam("id") Long id, @Valid EnvironmentRequestDTO request) {
        return service.update(id, request);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed(ROLE_ADMIN)
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @POST
    @Path("/{id}/connection-test")
    @RolesAllowed(ROLE_ADMIN)
    public ConnectionTestResultDTO testConnection(@PathParam("id") Long id) { return service.testConnection(id); }
}
