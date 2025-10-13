package nl.syntouch.clients;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import java.util.Map;

//TODO realm name to env variable
@RegisterRestClient(configKey = "keycloak-api")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface KeycloakClient {

    @POST
    @Path("/realms/master/protocol/openid-connect/token")
    Map<String, Object> getToken(
            @FormParam("client_id") String clientId,
            @FormParam("client_secret") String clientSecret,
            @FormParam("grant_type") String grantType
    );

    @GET
    @Path("/admin/realms/dmn_tool/users")
    List<Map<String, Object>> getUsers(
            @HeaderParam("Authorization") String authorization
    );

    @GET
    @Path("/admin/realms/dmn_tool/groups/{groupId}/members")
    List<Map<String, Object>> getGroupUsers(
            @PathParam("groupId") String groupId,
            @HeaderParam("Authorization") String authorization
    );

    @GET
    @Path("/admin/realms/dmn_tool/users/{userId}/role-mappings/realm")
    List<Map<String,Object>> getUserRealmRoles(
            @PathParam("userId") String userId,
            @HeaderParam("Authorization") String bearerToken
    );
}
