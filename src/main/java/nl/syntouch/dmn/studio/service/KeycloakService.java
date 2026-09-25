package nl.syntouch.dmn.studio.service;
import io.vertx.ext.auth.User;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.resteasy.reactive.ClientWebApplicationException;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.Optional;

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

    public Optional<UserRepresentation> getUserByUsername(String username) {
        if(username == null) return Optional.empty();

        List<UserRepresentation> users = keycloak
                .realm(realm)
                .users()
                .searchByUsername(username, true);

        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.getFirst());
    }

    public Optional<UserRepresentation> getUserById(String id) {
        if (id == null) return Optional.empty();

        UserRepresentation user;

        try{
            user = keycloak
                    .realm(realm)
                    .users()
                    .get(id)
                    .toRepresentation();
        } catch (ClientWebApplicationException e) {
            if(e.getResponse().getStatus() == HttpStatus.SC_NOT_FOUND) {
                return Optional.empty();
            }

            throw e;
        }

        return Optional.of(user);
    }

    public Optional<String> transformUUIDToUsername(String id) {
       return getUserById(id)
                .map(owner -> owner.getFirstName() + " " + owner.getLastName());
    }
}
