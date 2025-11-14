package nl.syntouch.dmn.studio.resource.custom;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;
import nl.syntouch.dmn.studio.service.KeycloakService;

import java.util.List;
import java.util.Map;

@Path("/auth")
public class AuthResource {

    @Inject
    KeycloakService keycloakService;

    @GET
    @Path("/token")
    public Response getToken() {
        var tokenResponse = keycloakService.fetchToken();
        return Response.ok(tokenResponse).build();
    }

    @GET
    @Path("/users")
    public Response getUsers() {
        var tokenResponse = keycloakService.fetchToken();
        String accessToken = (String) tokenResponse.get("access_token");
        List<Map<String, Object>> usersResponse = keycloakService.fetchUsers(accessToken);
        return Response.ok(usersResponse).build();
    }

    @GET
    @Path("/group-users/{groupId}")
    public Response getGroupUsers(@PathParam("groupId") String groupId) {
        var tokenResponse = keycloakService.fetchToken();
        String accessToken = (String) tokenResponse.get("access_token");
        List<Map<String, Object>> groupUsersResponse = keycloakService.fetchGroupUsers(groupId, accessToken);
        return Response.ok(groupUsersResponse).build();
    }
}
