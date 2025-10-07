package nl.syntouch.clients;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import java.util.Map;

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
    @Path("/admin/realms/dmn_tool/users/profile")
    Map<String, Object> getUsers(
            @HeaderParam("Authorization") String authorization
    );
}
