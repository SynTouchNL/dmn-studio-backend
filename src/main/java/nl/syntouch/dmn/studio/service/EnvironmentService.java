package nl.syntouch.dmn.studio.service;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;
import lombok.RequiredArgsConstructor;
import nl.syntouch.dmn.studio.client.OperatonClientFactory;
import nl.syntouch.dmn.studio.model.Deployment;
import nl.syntouch.dmn.studio.model.Environment;
import nl.syntouch.dmn.studio.model.dto.*;
import nl.syntouch.dmn.studio.repository.EnvironmentRepository;
import org.jboss.logging.Logger;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@ApplicationScoped
@Transactional
@RequiredArgsConstructor
public class EnvironmentService {
    private static final Logger LOG = Logger.getLogger(EnvironmentService.class);

    private final EnvironmentRepository environments;
    private final CredentialEncryptionService encryption;
    private final OperatonClientFactory clients;
    private final SecurityIdentity identity;

    public List<EnvironmentResponseDTO> list() {
        return environments.listVisibleWithDeployments().stream().map(this::response).toList();
    }

    public EnvironmentResponseDTO get(Long id) { return response(find(id)); }

    public EnvironmentResponseDTO create(EnvironmentRequestDTO request) {
        Environment environment = new Environment();
        environment.setName(normalizeAndEnsureUniqueName(request.name(), null));
        environment.setUrl(normalizeUrl(request.url()));
        applyCredentials(environment, request.username(), request.password());
        environment.setActive(request.active());
        environment.setCreatedAt(Instant.now());
        environment.setCreatedBy(identity.getPrincipal().getName());
        audit(environment);

        environments.persist(environment);
        return response(environment);
    }

    public EnvironmentResponseDTO update(Long id, EnvironmentRequestDTO request) {
        Environment environment = find(id);
        if (!environment.isActive() && !request.active()) {
            throw new WebApplicationException("Environment is inactive and read-only", 409);
        }

        environment.setName(normalizeAndEnsureUniqueName(request.name(), id));
        environment.setUrl(normalizeUrl(request.url()));
        applyCredentials(environment, request.username(), request.password());
        environment.setActive(request.active());
        audit(environment);

        environments.persist(environment);
        return response(environment);
    }

    public void delete(Long id) {
        environments.delete(find(id));
    }

    public ConnectionTestResultDTO testConnection(Long id) {
        Environment environment = find(id);
        if (environment.getUrl() == null) {
            return new ConnectionTestResultDTO(false, null, "Environment has no URL configured", 0);
        }
        return testConnection(environment.getUrl(), environment.getUsername(), clients.storedPassword(environment));
    }

    public ConnectionTestResultDTO testConnection(ConnectionTestRequestDTO request) {
        String username = trimToNull(request.username());
        String password = blankToNull(request.password());
        if (username != null && password == null && request.environmentId() != null) {
            password = clients.storedPassword(find(request.environmentId()));
        }
        return testConnection(normalizeUrl(request.url()), username, password);
    }

    private ConnectionTestResultDTO testConnection(String url, String username, String password) {
        long start = System.nanoTime();
        try {
            clients.connectionTestApi(url, username, password)
                    .getDeploymentsCount(null, null, null, null, null, null, null, null, null, null);
            return new ConnectionTestResultDTO(true, 200, "Connection successful", elapsedMs(start));
        } catch (WebApplicationException e) {
            int status = e.getResponse().getStatus();
            String message = switch (status) {
                case 401 -> "Invalid credentials";
                case 403 -> "Credentials lack permission to read deployments";
                case 404 -> "Operaton REST API not found at this URL; check the context path";
                default -> "Engine responded with HTTP " + status;
            };
            return new ConnectionTestResultDTO(false, status, message, elapsedMs(start));
        } catch (ProcessingException e) {
            LOG.debugf(e, "Connection test to %s failed", url);
            return new ConnectionTestResultDTO(false, null, "Engine unreachable: " + rootMessage(e), elapsedMs(start));
        } catch (RuntimeException e) {
            LOG.warnf(e, "Connection test to %s returned an unexpected response", url);
            return new ConnectionTestResultDTO(false, null, "Unexpected response; is this an Operaton REST API URL?", elapsedMs(start));
        }
    }

    private Environment find(Long id) {
        Environment environment = environments.findVisible(id);
        if (environment == null) throw new NotFoundException("Environment not found");
        return environment;
    }

    private String normalizeAndEnsureUniqueName(String value, Long id) {
        String name = value.trim();
        boolean exists = id == null ? environments.count("lower(name) = lower(?1) and internal = false", name) > 0
                : environments.count("lower(name) = lower(?1) and internal = false and id <> ?2", name, id) > 0;
        if (exists) throw new WebApplicationException("Environment name already exists", 409);
        return name;
    }

    private static String normalizeUrl(String url) {
        int end = url.length();
        while (end > 0 && url.charAt(end - 1) == '/') end--;
        return url.substring(0, end);
    }

    private void applyCredentials(Environment environment, String requestedUsername, String requestedPassword) {
        String username = trimToNull(requestedUsername);
        String password = blankToNull(requestedPassword);
        if (username == null && password != null) {
            throw new BadRequestException("Username is required when a password is provided");
        }
        if (username == null) {
            environment.setPasswordEncrypted(null);
        } else if (password != null) {
            environment.setPasswordEncrypted(encryption.encrypt(password));
        } else if (environment.getPasswordEncrypted() == null) {
            throw new BadRequestException("Password is required when a username is provided");
        }
        environment.setUsername(username);
    }

    private void audit(Environment environment) {
        environment.setUpdatedBy(identity.getPrincipal().getName());
        environment.setUpdatedAt(Instant.now());
    }

    private EnvironmentResponseDTO response(Environment environment) {
        List<EnvironmentDeploymentDTO> deploymentDTOs = environment.getDeployments() == null ? List.of()
                : environment.getDeployments().stream().map(EnvironmentService::deploymentResponse).toList();
        return new EnvironmentResponseDTO(environment.getId(), environment.getName(), environment.getUrl(),
                environment.getUsername(), environment.getPasswordEncrypted() != null, environment.isActive(),
                environment.getCreatedBy(), environment.getUpdatedBy(), environment.getCreatedAt(),
                environment.getUpdatedAt(), deploymentDTOs);
    }

    private static EnvironmentDeploymentDTO deploymentResponse(Deployment deployment) {
        var version = deployment.getVersion();
        return new EnvironmentDeploymentDTO(deployment.getId(), DeploymentDMNDTO.DMNVersionSubDTO.from(version),
                deployment.getDeployedBy(), deployment.getDeployedTime(), deployment.getDeploymentRef(),
                version.getDmn().getId(), version.getDmn().getName());
    }

    private static String trimToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    // Passwords are never trimmed; leading/trailing whitespace may be significant.
    private static String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value;
    }

    private static long elapsedMs(long startNanos) {
        return Duration.ofNanos(System.nanoTime() - startNanos).toMillis();
    }

    private static String rootMessage(Throwable e) {
        Throwable root = e;
        while (root.getCause() != null) root = root.getCause();
        return root.getMessage() == null ? root.getClass().getSimpleName() : root.getMessage();
    }
}
