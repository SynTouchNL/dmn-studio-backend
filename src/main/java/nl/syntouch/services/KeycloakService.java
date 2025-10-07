package nl.syntouch.services;
import nl.syntouch.clients.KeycloakClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;

@ApplicationScoped
public class KeycloakService {

    @Inject
    @RestClient
    KeycloakClient keycloakClient;

    public Map<String, Object> fetchToken() { //TODO !!SECRET IN CODE!!
        return keycloakClient.getToken("test-master", "iEM8UPcz2QgY1OfithUUzR6lLpW5LEtT", "client_credentials");
    }

    public Map<String, Object> fetchUsers(String token) {
        return keycloakClient.getUsers("Bearer " + token);
    }
}
