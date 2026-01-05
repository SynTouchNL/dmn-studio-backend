package nl.syntouch.dmn.studio.resource.custom;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import nl.syntouch.dmn.studio.service.KeycloakService;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

import static nl.syntouch.dmn.studio.DmnStudioConstants.*;

@Path("/auth")
@RequiredArgsConstructor
public class AuthResource {

    private final KeycloakService keycloakService;

    @GET
    @Path("/{role}/users")
    @RolesAllowed({ROLE_DEVELOPER, ROLE_APPROVER, ROLE_DEPLOYER})
    public Response getUsersByRole(@PathParam("role") String role) {
        List<UserRepresentation> users = keycloakService.getUsersByRole(role);
        return Response.ok(users).build();
    }

    @GET
    @Path("/users")
    @RolesAllowed({ROLE_DEVELOPER, ROLE_APPROVER, ROLE_DEPLOYER})
    public Response getAllUsers() {
        List<UserRepresentation> users = keycloakService.getUsers();
        return Response.ok(users).build();
    }

    @GET
    @Path("/user/{username}")
    @RolesAllowed({ROLE_DEVELOPER, ROLE_APPROVER, ROLE_DEPLOYER})
    public Response getUserByUsername(@PathParam("username") String username) {
        UserRepresentation user = keycloakService.getUserByUsername(username);
        return Response.ok(user).build();
    }

    @GET
    @Path("/token")
    @RolesAllowed({ROLE_DEVELOPER, ROLE_APPROVER, ROLE_DEPLOYER})
    public Response getToken() {
        var tokenResponse = keycloakService.fetchToken();
        return Response.ok(tokenResponse).build();
    }
}
