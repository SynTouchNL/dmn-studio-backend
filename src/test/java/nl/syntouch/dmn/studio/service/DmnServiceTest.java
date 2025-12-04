package nl.syntouch.dmn.studio.service;

import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import nl.syntouch.dmn.studio.model.DMN;
import nl.syntouch.dmn.studio.model.Domain;
import nl.syntouch.dmn.studio.model.dto.DMNCreateDTO;
import nl.syntouch.dmn.studio.repository.DmnRepository;
import nl.syntouch.dmn.studio.repository.DomainRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
@DisplayName("DMN Service Tests")
class DmnServiceTest {

    @InjectMock
    DomainRepository domainRepository;

    @InjectMock
    DmnRepository dmnRepository;

    @InjectMock
    SecurityIdentity securityIdentity;

    @Inject
    DmnService dmnService;

    private void mockSecurityIdentity(String username) {
        Principal mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn(username);
        when(securityIdentity.getPrincipal()).thenReturn(mockPrincipal);
    }

    @Test
    @DisplayName("Should create DMN with version when valid data is provided")
    void createDmnCreatesNewDmnWithVersion() {
        Long domainId = 1L;
        Domain domain = createDomain(domainId);
        DMNCreateDTO dmnDTO = createDmnDTO(domainId, "Test DMN", "Test Owner");
        mockSecurityIdentity("testUser");

        when(domainRepository.findByIdOptional(domainId)).thenReturn(Optional.of(domain));

        DMN result = dmnService.createDmn(dmnDTO);

        assertNotNull(result);
        assertEquals("Test DMN", result.getName());
        assertEquals("Test Owner", result.getOwner());
        assertEquals(domain, result.getDomain());
        assertNotNull(result.getVersions());
        assertEquals(1, result.getVersions().size());
        assertEquals("testUser", result.getVersions().get(0).getCreatedBy());
        assertArrayEquals(new byte[]{1, 2, 3}, result.getVersions().get(0).getFileBlob());
        verify(dmnRepository).persist(result);
    }

    @Test
    @DisplayName("Should throw NotFoundException when domain does not exist")
    void createDmnThrowsNotFoundExceptionWhenDomainDoesNotExist() {
        Long nonExistentDomainId = 999L;
        DMNCreateDTO dmnDTO = createDmnDTO(nonExistentDomainId, "Test DMN", "Test Owner");

        when(domainRepository.findByIdOptional(nonExistentDomainId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> dmnService.createDmn(dmnDTO));
        verify(dmnRepository, never()).persist(any(DMN.class));
    }

    @Test
    @DisplayName("Should set version DMN reference correctly")
    void createDmnSetsVersionDmnReferenceCorrectly() {
        Long domainId = 1L;
        Domain domain = createDomain(domainId);
        DMNCreateDTO dmnDTO = createDmnDTO(domainId, "Test DMN", "Test Owner");
        mockSecurityIdentity("testUser");

        when(domainRepository.findByIdOptional(domainId)).thenReturn(Optional.of(domain));

        DMN result = dmnService.createDmn(dmnDTO);

        assertNotNull(result);
        assertNotNull(result.getVersions());
        assertEquals(1, result.getVersions().size());
        assertEquals(result, result.getVersions().get(0).getDmn());
    }

    @Test
    @DisplayName("Should use correct file blob from DTO")
    void createDmnUsesCorrectFileBlobFromDTO() {
        Long domainId = 1L;
        Domain domain = createDomain(domainId);
        byte[] customFileBlob = new byte[]{10, 20, 30, 40};
        DMNCreateDTO dmnDTO = new DMNCreateDTO("Test DMN", "Test Owner", domainId, customFileBlob, "testUser");
        mockSecurityIdentity("testUser");

        when(domainRepository.findByIdOptional(domainId)).thenReturn(Optional.of(domain));

        DMN result = dmnService.createDmn(dmnDTO);

        assertNotNull(result);
        assertNotNull(result.getVersions());
        assertEquals(1, result.getVersions().size());
        assertArrayEquals(customFileBlob, result.getVersions().get(0).getFileBlob());
    }

    @Test
    @DisplayName("Should use authenticated user as creator")
    void createDmnUsesAuthenticatedUserAsCreator() {
        Long domainId = 1L;
        Domain domain = createDomain(domainId);
        DMNCreateDTO dmnDTO = createDmnDTO(domainId, "Test DMN", "Test Owner");
        mockSecurityIdentity("authenticatedUser");

        when(domainRepository.findByIdOptional(domainId)).thenReturn(Optional.of(domain));

        DMN result = dmnService.createDmn(dmnDTO);

        assertNotNull(result);
        assertNotNull(result.getVersions());
        assertEquals(1, result.getVersions().size());
        assertEquals("authenticatedUser", result.getVersions().get(0).getCreatedBy());
    }

    private static Domain createDomain(Long id) {
        Domain domain = new Domain();
        domain.setId(id);
        domain.setName("Test Domain");
        return domain;
    }

    private static DMNCreateDTO createDmnDTO(Long domainId, String name, String owner) {
        return new DMNCreateDTO(
                name,
                owner,
                domainId,
                new byte[]{1, 2, 3},
                "testUser"
        );
    }
}

