package nl.syntouch.dmn.studio.service;
import nl.syntouch.dmn.studio.client.KeycloakClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;

@ApplicationScoped
public class KeycloakService {

    @Inject
    @ConfigProperty(name = "quarkus.oidc.credentials.secret")
    String serviceSecret;

    @Inject
    @RestClient
    KeycloakClient keycloakClient;

    public Map<String, Object> fetchToken() {
        return keycloakClient.getToken("quarkus-backend", serviceSecret, "client_credentials");
    }

    public List<Map<String, Object>> fetchUsers(String token) {
        return keycloakClient.getUsers("Bearer " + token);
    }

    public List<Map<String, Object>> fetchGroupUsers(String groupId, String token) {
        return keycloakClient.getGroupUsers(groupId, "Bearer " + token);
    }
}
