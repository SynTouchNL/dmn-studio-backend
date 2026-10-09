package nl.syntouch.dmn.studio.service;

import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import nl.syntouch.dmn.studio.model.Environment;
import nl.syntouch.dmn.studio.model.dto.environment.EnvironmentRequestDTO;
import nl.syntouch.dmn.studio.repository.EnvironmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.security.Principal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@QuarkusTest
@DisplayName("Environment Service Tests")
class EnvironmentServiceTest {

    @InjectMock
    EnvironmentRepository environmentRepository;

    @InjectMock
    CredentialEncryptionService encryption;

    @InjectMock
    SecurityIdentity securityIdentity;

    @Inject
    EnvironmentService environmentService;

    @Inject
    Validator validator;

    private Environment environment;

    @BeforeEach
    void setup() {
        environment = new Environment();
        environment.setId(1L);
        environment.setName("Productie");
        environment.setUrl("https://engine.example.com/engine-rest");
        environment.setActive(true);
        environment.setCreatedAt(Instant.now());
        environment.setUpdatedAt(Instant.now());
        environment.setCreatedBy("User 1");
        environment.setUpdatedBy("User 1");

        Principal principal = mock();
        when(principal.getName()).thenReturn("admin-user");
        when(securityIdentity.getPrincipal()).thenReturn(principal);
        when(encryption.encrypt(anyString())).thenAnswer(invocation -> "v1:enc(" + invocation.getArgument(0) + ")");
    }

    @Test
    @DisplayName("Should create an environment with an encrypted password and normalized fields")
    void createPersistsEncryptedPassword() {
        var result = environmentService.create(new EnvironmentRequestDTO(
                "  Acceptatie  ", "https://acc.example.com/engine-rest/", " demo ", "secret", true));

        ArgumentCaptor<Environment> captor = ArgumentCaptor.forClass(Environment.class);
        verify(environmentRepository).persist(captor.capture());
        var persisted = captor.getValue();
        assertEquals("Acceptatie", persisted.getName());
        assertEquals("https://acc.example.com/engine-rest", persisted.getUrl());
        assertEquals("demo", persisted.getUsername());
        assertEquals("v1:enc(secret)", persisted.getPasswordEncrypted());
        assertEquals("admin-user", persisted.getCreatedBy());
        assertEquals("admin-user", persisted.getUpdatedBy());
        assertNotNull(persisted.getCreatedAt());
        assertTrue(result.passwordSet());
        assertTrue(result.deployments().isEmpty());
    }

    @Test
    @DisplayName("Should reject a duplicate name case-insensitively")
    void createRejectsDuplicateName() {
        when(environmentRepository.count("lower(name) = lower(?1) and internal = false", "productie")).thenReturn(1L);

        var exception = assertThrows(WebApplicationException.class, () -> environmentService.create(
                new EnvironmentRequestDTO("productie", "https://engine.example.com", null, null, true)));

        assertEquals(409, exception.getResponse().getStatus());
        assertEquals("Environment name already exists", exception.getMessage());
        verify(environmentRepository, never()).persist(any(Environment.class));
    }

    @Test
    @DisplayName("Should only accept absolute http(s) URLs")
    void requestValidatesUrl() {
        for (String invalid : new String[]{"ftp://engine.example.com", "engine.example.com", "http:///engine-rest", " https://engine.example.com",
                "https://user:pass@engine.example.com", "https://engine.example.com/engine-rest?x=1", "https://engine.example.com#frag"}) {
            var violations = validator.validate(new EnvironmentRequestDTO("Ontwikkel", invalid, null, null, true));
            assertEquals(1, violations.size(), invalid);
            assertEquals("URL must be an absolute http(s) URL", violations.iterator().next().getMessage());
        }
        assertTrue(validator.validate(new EnvironmentRequestDTO("Ontwikkel", "HTTPS://engine.example.com:8443/engine-rest", null, null, true)).isEmpty());
    }

    @Test
    @DisplayName("Should require a password when a username is set for the first time")
    void createRejectsUsernameWithoutPassword() {
        var exception = assertThrows(BadRequestException.class, () -> environmentService.create(
                new EnvironmentRequestDTO("Ontwikkel", "https://engine.example.com", "demo", null, true)));

        assertEquals("Password is required when a username is provided", exception.getMessage());
    }

    @Test
    @DisplayName("Should keep the stored password when none is sent on update")
    void updateKeepsStoredPassword() {
        environment.setUsername("demo");
        environment.setPasswordEncrypted("v1:stored");
        when(environmentRepository.findVisible(1L)).thenReturn(environment);

        environmentService.update(1L, new EnvironmentRequestDTO(
                "Productie", "https://engine.example.com/engine-rest", "demo", null, true));

        assertEquals("v1:stored", environment.getPasswordEncrypted());
        assertEquals("admin-user", environment.getUpdatedBy());
        verify(encryption, never()).encrypt(anyString());
    }

    @Test
    @DisplayName("Should clear the password when the username is cleared")
    void updateClearsCredentials() {
        environment.setUsername("demo");
        environment.setPasswordEncrypted("v1:stored");
        when(environmentRepository.findVisible(1L)).thenReturn(environment);

        var result = environmentService.update(1L, new EnvironmentRequestDTO(
                "Productie", "https://engine.example.com/engine-rest", " ", null, true));

        assertNull(environment.getUsername());
        assertNull(environment.getPasswordEncrypted());
        assertFalse(result.passwordSet());
    }

    @Test
    @DisplayName("Should reject updates that leave an inactive environment inactive")
    void updateRejectsChangesToInactiveEnvironment() {
        environment.setActive(false);
        when(environmentRepository.findVisible(1L)).thenReturn(environment);

        var exception = assertThrows(WebApplicationException.class, () -> environmentService.update(1L,
                new EnvironmentRequestDTO("Changed", "https://engine.example.com", null, null, false)));

        assertEquals(409, exception.getResponse().getStatus());
        assertEquals("Environment is inactive and read-only", exception.getMessage());
        verify(environmentRepository, never()).persist(any(Environment.class));
    }

    @Test
    @DisplayName("Should reactivate an inactive environment")
    void updateReactivatesInactiveEnvironment() {
        environment.setActive(false);
        when(environmentRepository.findVisible(1L)).thenReturn(environment);

        var result = environmentService.update(1L,
                new EnvironmentRequestDTO("Productie", "https://engine.example.com", null, null, true));

        assertTrue(result.active());
        verify(environmentRepository).persist(environment);
    }

    @Test
    @DisplayName("Should delete an environment")
    void deleteRemovesEnvironment() {
        when(environmentRepository.findVisible(1L)).thenReturn(environment);

        environmentService.delete(1L);

        verify(environmentRepository).delete(environment);
    }

    @Test
    @DisplayName("Should treat internal or missing environments as not found")
    void internalEnvironmentIsNotFound() {
        when(environmentRepository.findVisible(2L)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> environmentService.get(2L));
        assertThrows(NotFoundException.class, () -> environmentService.delete(2L));
        assertThrows(NotFoundException.class, () -> environmentService.testConnection(2L));
        verify(environmentRepository, never()).delete(any(Environment.class));
    }
}
