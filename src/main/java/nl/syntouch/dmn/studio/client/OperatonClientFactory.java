package nl.syntouch.dmn.studio.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;
import nl.syntouch.dmn.studio.model.Environment;
import nl.syntouch.dmn.studio.service.CredentialEncryptionService;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.openapi.quarkus.operaton_rest_api_json.api.DeploymentApi;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
@RequiredArgsConstructor
public class OperatonClientFactory {
    private final CredentialEncryptionService encryption;

    public DeploymentApi deploymentApi(Environment environment) {
        if (environment.getUrl() == null) {
            throw new WebApplicationException("Environment '%s' has no URL configured".formatted(environment.getName()), 409);
        }
        return builder(environment.getUrl(), environment.getUsername(), storedPassword(environment))
                .build(DeploymentApi.class);
    }

    public DeploymentApi connectionTestApi(String url, String username, String password) {
        return builder(url, username, password)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build(DeploymentApi.class);
    }

    public String storedPassword(Environment environment) {
        return environment.getPasswordEncrypted() == null ? null : encryption.decrypt(environment.getPasswordEncrypted());
    }

    private static RestClientBuilder builder(String url, String username, String password) {
        return RestClientBuilder.newBuilder()
                .baseUri(URI.create(url))
                .register(new BasicAuthFilter(username, password), Priorities.AUTHENTICATION + 100);
    }

    private static final class BasicAuthFilter implements ClientRequestFilter {
        private final String header;

        BasicAuthFilter(String username, String password) {
            this.header = username == null ? null : "Basic " + Base64.getEncoder()
                    .encodeToString((username + ":" + (password == null ? "" : password)).getBytes(StandardCharsets.UTF_8));
        }

        @Override
        public void filter(ClientRequestContext context) {
            if (header == null) {
                context.getHeaders().remove(HttpHeaders.AUTHORIZATION);
            } else {
                context.getHeaders().putSingle(HttpHeaders.AUTHORIZATION, header);
            }
        }
    }
}
