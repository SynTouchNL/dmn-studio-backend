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
import java.time.Duration;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
@RequiredArgsConstructor
public class OperatonClientFactory {
    private final CredentialEncryptionService encryption;

    public DeploymentApi deploymentApi(Environment environment) {
        if (environment.getUrl() == null || environment.getUrl().isBlank()) {
            throw new WebApplicationException("Environment '%s' has no URL configured".formatted(environment.getName()), 409);
        }
        return deploymentApi(environment.getUrl(), environment.getUsername(), storedPassword(environment), null);
    }

    public DeploymentApi deploymentApi(String url, String username, String password, Timeouts timeouts) {
        RestClientBuilder builder = RestClientBuilder.newBuilder()
                .baseUri(URI.create(url))
                .register(new BasicAuthFilter(username, password), Priorities.AUTHENTICATION + 100);
        if (timeouts != null) {
            builder.connectTimeout(timeouts.connect().toMillis(), TimeUnit.MILLISECONDS)
                    .readTimeout(timeouts.read().toMillis(), TimeUnit.MILLISECONDS);
        }
        return builder.build(DeploymentApi.class);
    }

    public String storedPassword(Environment environment) {
        if (environment.getPasswordEncrypted() == null) {
            return null;
        }
        if (!encryption.isConfigured()) {
            throw new WebApplicationException("Encryption key is not configured (DMN_STUDIO_ENCRYPTION_KEY)", 500);
        }
        try {
            return encryption.decrypt(environment.getPasswordEncrypted());
        } catch (IllegalStateException e) {
            throw new WebApplicationException(
                    "Stored password of environment '%s' could not be decrypted; re-enter it".formatted(environment.getName()), e, 500);
        }
    }

    public record Timeouts(Duration connect, Duration read) {}

    static final class BasicAuthFilter implements ClientRequestFilter {
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
