package nl.syntouch.dmn.studio.service;
import io.vertx.ext.auth.User;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import jakarta.enterprise.context.ApplicationScoped;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

@ApplicationScoped
public class KeycloakService {
    Keycloak keycloak;

    @ConfigProperty(name = "quarkus.rest.client.keycloak-api.url")
    String serverUrl;

    @ConfigProperty(name = "quarkus.rest.client.keycloak-api.realm")
    String realm;
    
    @ConfigProperty(name = "quarkus.oidc.client-id")
    String clientId;

    @ConfigProperty(name = "quarkus.oidc.credentials.secret")
    String clientSecret;

    @PostConstruct
    public void initKeycloak() {
        keycloak = KeycloakBuilder
                .builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }

    @PreDestroy
    public void closeKeycloak() {
        keycloak.close();
    }

    public String fetchToken() {
        return keycloak
                .tokenManager()
                .getAccessTokenString();
    }

    public List<UserRepresentation> getUsersByRole(String roleName) {
        return keycloak
                .realm(realm)
                .roles()
                .get(roleName)
                .getUserMembers();
    }

    public List<UserRepresentation> getUsers() {
        return keycloak
                .realm(realm)
                .users()
                .list();
    }

    public UserRepresentation getUserByUsername(String username) {
        List<UserRepresentation> users = keycloak
                .realm(realm)
                .users()
                .search(username, 0, 1); // search by username, limit 1

        if (users.isEmpty()) {
            return null;
        }
        return users.getFirst();
    }
}
