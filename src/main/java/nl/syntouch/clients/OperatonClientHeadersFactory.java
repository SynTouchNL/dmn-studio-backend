// java
package nl.syntouch.clients;

import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.ext.Provider;
import jakarta.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Provider
public class OperatonClientHeadersFactory implements ClientHeadersFactory {

    @Inject
    @ConfigProperty(name = "operaton.client.username")
    String username;

    @Inject
    @ConfigProperty(name = "operaton.client.password")
    String password;

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders,
                                                 MultivaluedMap<String, String> clientOutgoingHeaders) {
        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>(incomingHeaders);
        String token = "Basic " + Base64.getEncoder()
                .encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
        headers.putSingle("Authorization", token);
        return headers;
    }

}
