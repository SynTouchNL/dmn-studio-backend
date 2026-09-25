package nl.syntouch.dmn.studio.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import nl.syntouch.dmn.studio.model.Domain;
import nl.syntouch.dmn.studio.model.dto.DomainRequestDTO;
import nl.syntouch.dmn.studio.repository.DmnRepository;
import nl.syntouch.dmn.studio.repository.DomainRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.ArgumentCaptor;

import java.security.Principal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@QuarkusTest
@DisplayName("Domain Service Tests")
class DomainServiceTest {

    private List<Domain> domains;

    @InjectMock
    DomainRepository domainRepository;

    @InjectMock
    DmnRepository dmnRepository;

    @InjectMock
    KeycloakService keycloakService;

    @InjectMock
    SecurityIdentity securityIdentity;

    @Inject
    DomainService domainService;

    @BeforeEach
    void setup() {
        var domain1 = new Domain();
        domain1.setName("Domain 1");
        domain1.setOwner("Owner 1");
        domain1.setActive(true);
        domain1.setCreatedBy("User 1");
        domain1.setModifiedBy("User 1");
        domain1.setCreatedDate(Instant.now());
        domain1.setModifiedDate(Instant.now());

        var domain2 = new Domain();
        domain2.setName("Domain 2");
        domain2.setOwner("Owner 2");
        domain2.setActive(false);
        domain2.setCreatedBy("User 2");
        domain2.setModifiedBy("User 2");
        domain2.setCreatedDate(Instant.now());
        domain2.setModifiedDate(Instant.now());

        domains = List.of(domain1, domain2);
    }

    @Test
    @DisplayName("Should list domains when valid pagination parameters are provided")
    void listListsDomains() {
        PanacheQuery<Domain> query = mock();
        when(domainRepository.findAll(any(Sort.class))).thenReturn(query);
        when(query.page(anyInt(), anyInt())).thenReturn(query);
        when(query.count()).thenReturn(12L);
        when(query.list()).thenReturn(domains);
        when(keycloakService.transformUUIDToUsername(any(String.class)))
                .thenAnswer(invocation -> Optional.of(invocation.getArgument(0)));

        var result = domainService.list(0, 10);

        assertEquals(2, result.items().size());
        assertEquals(0, result.page());
        assertEquals(10, result.size());
        assertEquals(12, result.totalElements());
        assertEquals(2, result.totalPages());
        verify(query).page(0, 10);
    }

    @Test
    @DisplayName("Should return empty pagination metadata when there are no domains")
    void listReturnsEmptyPage() {
        PanacheQuery<Domain> query = mock();
        when(domainRepository.findAll(any(Sort.class))).thenReturn(query);
        when(query.page(anyInt(), anyInt())).thenReturn(query);
        when(query.list()).thenReturn(List.of());

        var result = domainService.list(0, 20);

        assertTrue(result.items().isEmpty());
        assertEquals(0, result.totalElements());
        assertEquals(0, result.totalPages());
    }

    @Test
    @DisplayName("Should retain the requested page when it is beyond the last page")
    void listReturnsOutOfRangePage() {
        PanacheQuery<Domain> query = mock();
        when(domainRepository.findAll(any(Sort.class))).thenReturn(query);
        when(query.page(anyInt(), anyInt())).thenReturn(query);
        when(query.count()).thenReturn(2L);
        when(query.list()).thenReturn(List.of());

        var result = domainService.list(3, 1);

        assertTrue(result.items().isEmpty());
        assertEquals(3, result.page());
        assertEquals(2, result.totalElements());
        assertEquals(2, result.totalPages());
    }

    @ParameterizedTest(name = "page={0}, size={1}")
    @CsvSource({
            "-1, 10",
            "0, 0"
    })
    @DisplayName("Should reject invalid pagination parameters")
    void listRejectsInvalidPagination(int page, int size) {
        var exception = assertThrows(BadRequestException.class, () -> domainService.list(page, size));

        assertEquals("Invalid pagination", exception.getMessage());
        verifyNoInteractions(domainRepository);
    }

    @Test
    @DisplayName("Should get a domain by id")
    void getReturnsDomain() {
        var domain = domains.getFirst();
        domain.setId(1L);
        when(domainRepository.findById(1L)).thenReturn(domain);
        when(keycloakService.transformUUIDToUsername("Owner 1")).thenReturn(Optional.of("Owner name 1"));

        var result = domainService.get(1L);

        assertEquals(1L, result.id());
        assertEquals("Domain 1", result.name());
        assertEquals("Owner 1", result.ownerId());
        assertEquals("Owner name 1", result.owner());
        assertTrue(result.active());
        verify(domainRepository).findById(1L);
    }

    @Test
    @DisplayName("Should create a domain")
    void createPersistsAndReturnsDomain() {
        var owner = new UserRepresentation();
        owner.setId("owner-id");
        Principal principal = mock();
        when(principal.getName()).thenReturn("authenticated-user");
        when(securityIdentity.getPrincipal()).thenReturn(principal);
        when(keycloakService.getUserById("owner-id")).thenReturn(Optional.of(owner));
        when(keycloakService.transformUUIDToUsername("owner-id")).thenReturn(Optional.of("Owner Name"));

        var result = domainService.create(new DomainRequestDTO("  New Domain  ", "owner-id", false));

        ArgumentCaptor<Domain> domainCaptor = ArgumentCaptor.forClass(Domain.class);
        verify(domainRepository).persist(domainCaptor.capture());
        var persistedDomain = domainCaptor.getValue();
        assertEquals("New Domain", persistedDomain.getName());
        assertEquals("owner-id", persistedDomain.getOwner());
        assertEquals("authenticated-user", persistedDomain.getCreatedBy());
        assertEquals("authenticated-user", persistedDomain.getModifiedBy());
        assertNotNull(persistedDomain.getCreatedDate());
        assertNotNull(persistedDomain.getModifiedDate());
        assertFalse(persistedDomain.isActive());
        assertEquals("New Domain", result.name());
        assertEquals("owner-id", result.ownerId());
        assertEquals("Owner Name", result.owner());
        assertFalse(result.active());
    }

    @ParameterizedTest(name = "current active={0}, requested active={1}")
    @CsvSource({
            "true, false",
            "false, true"
    })
    @DisplayName("Should update an active domain or reactivate an inactive domain")
    void updateChangesFieldsAndActiveStatus(boolean currentActive, boolean requestedActive) {
        var domain = domains.getFirst();
        domain.setId(1L);
        domain.setActive(currentActive);
        var owner = new UserRepresentation();
        owner.setId("new-owner-id");
        Principal principal = mock();
        when(principal.getName()).thenReturn("updating-user");
        when(securityIdentity.getPrincipal()).thenReturn(principal);
        when(domainRepository.findById(1L)).thenReturn(domain);
        when(keycloakService.getUserById("new-owner-id")).thenReturn(Optional.of(owner));
        when(keycloakService.transformUUIDToUsername("new-owner-id")).thenReturn(Optional.of("New Owner"));

        var result = domainService.update(
                1L,
                new DomainRequestDTO("Updated Domain", "new-owner-id", requestedActive)
        );

        assertEquals("Updated Domain", domain.getName());
        assertEquals("new-owner-id", domain.getOwner());
        assertEquals(requestedActive, domain.isActive());
        assertEquals("updating-user", domain.getModifiedBy());
        assertEquals(requestedActive, result.active());
        verify(domainRepository).persist(domain);
    }

    @Test
    @DisplayName("Should reject updates that leave an inactive domain inactive")
    void updateRejectsChangesToInactiveDomain() {
        var domain = domains.get(1);
        domain.setId(2L);
        when(domainRepository.findById(2L)).thenReturn(domain);

        var exception = assertThrows(
                WebApplicationException.class,
                () -> domainService.update(2L, new DomainRequestDTO("Changed Domain", "owner-id", false))
        );

        assertEquals(409, exception.getResponse().getStatus());
        assertEquals("Domain is inactive and read-only", exception.getMessage());
        verify(domainRepository, never()).persist(any(Domain.class));
    }

    @Test
    @DisplayName("Should delete an active domain without DMNs")
    void deleteRemovesActiveDomainWithoutDmns() {
        var domain = domains.getFirst();
        domain.setId(1L);
        when(domainRepository.findById(1L)).thenReturn(domain);
        when(dmnRepository.count("domain.id", 1L)).thenReturn(0L);

        domainService.delete(1L);

        verify(dmnRepository).count("domain.id", 1L);
        verify(domainRepository).delete(domain);
    }

    @Test
    @DisplayName("Should reject deleting a domain that contains DMNs")
    void deleteRejectsDomainContainingDmns() {
        var domain = domains.getFirst();
        domain.setId(1L);
        when(domainRepository.findById(1L)).thenReturn(domain);
        when(dmnRepository.count("domain.id", 1L)).thenReturn(1L);

        var exception = assertThrows(WebApplicationException.class, () -> domainService.delete(1L));

        assertEquals(409, exception.getResponse().getStatus());
        assertEquals("Domain still contains DMNs", exception.getMessage());
        verify(domainRepository, never()).delete(any(Domain.class));
    }

    @Test
    @DisplayName("Should delete an inactive domain without DMNs")
    void deleteRemovesInactiveDomainWithoutDmns() {
        var domain = domains.get(1);
        domain.setId(2L);
        when(domainRepository.findById(2L)).thenReturn(domain);
        when(dmnRepository.count("domain.id", 2L)).thenReturn(0L);

        domainService.delete(2L);

        verify(dmnRepository).count("domain.id", 2L);
        verify(domainRepository).delete(domain);
    }

    @Test
    @DisplayName("Should reject deleting a domain that does not exist")
    void deleteRejectsMissingDomain() {
        when(domainRepository.findById(99L)).thenReturn(null);

        var exception = assertThrows(NotFoundException.class, () -> domainService.delete(99L));

        assertEquals("Domain not found", exception.getMessage());
        verifyNoInteractions(dmnRepository);
        verify(domainRepository, never()).delete(any(Domain.class));
    }
}
