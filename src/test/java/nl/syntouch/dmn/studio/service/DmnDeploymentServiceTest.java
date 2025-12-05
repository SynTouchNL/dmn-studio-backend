package nl.syntouch.dmn.studio.service;

import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import nl.syntouch.dmn.studio.model.*;
import nl.syntouch.dmn.studio.model.composites.DMNVersionId;
import nl.syntouch.dmn.studio.model.dto.DeployDTO;
import nl.syntouch.dmn.studio.repository.DeploymentRepository;
import nl.syntouch.dmn.studio.repository.DmnRepository;
import nl.syntouch.dmn.studio.repository.DmnVersionRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openapi.quarkus.operaton_rest_api_json.api.DeploymentApi;
import org.openapi.quarkus.operaton_rest_api_json.model.DeploymentDto;
import org.openapi.quarkus.operaton_rest_api_json.model.DeploymentWithDefinitionsDto;

import java.io.IOException;
import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
@DisplayName("DMN Deployment Service Tests")
class DmnDeploymentServiceTest {

    @InjectMock
    DmnRepository dmnRepository;

    @InjectMock
    DmnVersionRepository dmnVersionRepository;

    @InjectMock
    DeploymentRepository deploymentRepository;

    @InjectMock
    @RestClient
    DeploymentApi deploymentApi;

    @InjectMock
    SecurityIdentity securityIdentity;

    @Inject
    DmnDeploymentService dmnDeploymentService;

    private DMN testDmn;
    private Environment testEnvironment;

    @BeforeEach
    @Transactional
    void setup() {
        Domain testDomain = new Domain();
        testDomain.setName("Test Domain");
        testDomain.persist();

        testDmn = new DMN();
        testDmn.setName("Test DMN");
        testDmn.setOwner("Test Owner");
        testDmn.setDomain(testDomain);
        testDmn.persist();

        testEnvironment = new Environment();
        testEnvironment.setName("Test Environment");
        testEnvironment.persist();

        Principal mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("testUser");
        when(securityIdentity.getPrincipal()).thenReturn(mockPrincipal);
    }

    @Test
    @Transactional
    @DisplayName("Should create deployment when valid data is provided")
    void createsDeploymentWhenValidDataProvided() throws IOException {
        Long versionId = 1L;
        DeployDTO deployDTO = createDefaultDeployDTO(testDmn, versionId);
        DMNVersion dmnVersion = createDmnVersion(versionId);
        DeploymentWithDefinitionsDto deploymentResponse = createDeploymentResponse("deploymentRef");

        when(dmnRepository.findByIdOptional(testDmn.getId())).thenReturn(Optional.of(testDmn));
        when(dmnVersionRepository.findByIdOptional(new DMNVersionId(testDmn.getId(), versionId)))
                .thenReturn(Optional.of(dmnVersion));
        when(deploymentApi.createDeployment(any())).thenReturn(deploymentResponse);

        DeploymentWithDefinitionsDto result = dmnDeploymentService.createDeployment(deployDTO);

        assertNotNull(result);
        assertEquals("deploymentRef", result.getId());
        verify(deploymentRepository).persist(any(Deployment.class));
    }

    @Test
    @DisplayName("Should throw NoSuchElementException when DMN does not exist")
    void throwsExceptionWhenDmnDoesNotExist() {
        DMN nonExistentDmn = createNonExistentDmn();
        DeployDTO deployDTO = createDefaultDeployDTO(nonExistentDmn, 1L);

        when(dmnRepository.findByIdOptional(nonExistentDmn.getId())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> dmnDeploymentService.createDeployment(deployDTO));
    }

    @Test
    @DisplayName("Should throw NoSuchElementException when DMN version does not exist")
    void throwsExceptionWhenDmnVersionDoesNotExist() {
        Long nonExistentVersion = 999L;
        DeployDTO deployDTO = createDefaultDeployDTO(testDmn, nonExistentVersion);

        when(dmnRepository.findByIdOptional(testDmn.getId())).thenReturn(Optional.of(testDmn));
        when(dmnVersionRepository.findByIdOptional(new DMNVersionId(testDmn.getId(), nonExistentVersion)))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> dmnDeploymentService.createDeployment(deployDTO));
    }

    @Test
    @DisplayName("Should get deployment when valid ID is provided")
    void getsDeploymentWhenValidIdProvided() {
        String deploymentId = "deploymentRef";
        DeploymentDto deploymentDto = createDeploymentDto(deploymentId, "Test Deployment");

        when(deploymentApi.getDeployment(deploymentId)).thenReturn(deploymentDto);

        DeploymentDto result = dmnDeploymentService.getDeployment(deploymentId);

        assertNotNull(result);
        assertEquals(deploymentId, result.getId());
        assertEquals("Test Deployment", result.getName());
        verify(deploymentApi).getDeployment(deploymentId);
    }

    @Test
    @DisplayName("Should delete deployment with cascade when cascade is true")
    void deletesDeploymentWithCascadeTrue() {
        String deploymentId = "deploymentRef";
        dmnDeploymentService.deleteDeployment(deploymentId, true);
        verify(deploymentApi).deleteDeployment(deploymentId, true, true, true);
    }

    @Test
    @DisplayName("Should delete deployment without cascade when cascade is false")
    void deletesDeploymentWithCascadeFalse() {
        String deploymentId = "deploymentRef";
        dmnDeploymentService.deleteDeployment(deploymentId, false);
        verify(deploymentApi).deleteDeployment(deploymentId, false, true, true);
    }

    @Test
    @Transactional
    @DisplayName("Should set deployment properties correctly when creating deployment")
    void setsDeploymentPropertiesCorrectlyWhenCreating() throws IOException {
        Long versionId = 1L;
        String newDeploymentRef = "newDeploymentRef";
        DeployDTO deployDTO = createCustomDeployDTO(testDmn, versionId);
        DMNVersion dmnVersion = createDmnVersion(versionId);
        DeploymentWithDefinitionsDto deploymentResponse = createDeploymentResponse(newDeploymentRef);

        when(dmnRepository.findByIdOptional(testDmn.getId())).thenReturn(Optional.of(testDmn));
        when(dmnVersionRepository.findByIdOptional(new DMNVersionId(testDmn.getId(), versionId)))
                .thenReturn(Optional.of(dmnVersion));
        when(deploymentApi.createDeployment(any())).thenReturn(deploymentResponse);

        DeploymentWithDefinitionsDto result = dmnDeploymentService.createDeployment(deployDTO);

        assertNotNull(result);
        assertEquals(newDeploymentRef, result.getId());
        verify(deploymentRepository).persist(argThat((Deployment deployment) ->
                deployment.getDeploymentRef().equals(newDeploymentRef) &&
                deployment.getDeployedBy().equals("testUser") &&
                deployment.getDeployedTo().equals(testEnvironment)
        ));
    }

    private DMNVersion createDmnVersion(Long version) {
        DMNVersion dmnVersion = new DMNVersion();
        dmnVersion.setDmn(testDmn);
        dmnVersion.setVersion(version);
        dmnVersion.setFileBlob(new byte[]{1, 2, 3});
        dmnVersion.setCreatedBy("TestUser");
        return dmnVersion;
    }

    private static DMN createNonExistentDmn() {
        DMN dmn = new DMN();
        dmn.setId(999L);
        return dmn;
    }

    private DeployDTO createDefaultDeployDTO(DMN dmn, Long version) {
        return new DeployDTO(
                dmn,
                version,
                testEnvironment,
                "tenantId",
                "source",
                true,
                true,
                "deploymentName",
                OffsetDateTime.now(),
                "data"
        );
    }

    private DeployDTO createCustomDeployDTO(DMN dmn, Long version) {
        return new DeployDTO(
                dmn,
                version,
                testEnvironment,
                "tenantId",
                "source",
                false,
                false,
                "customDeploymentName",
                OffsetDateTime.now(),
                "customData"
        );
    }

    private static DeploymentWithDefinitionsDto createDeploymentResponse(String deploymentId) {
        DeploymentWithDefinitionsDto dto = new DeploymentWithDefinitionsDto();
        dto.setId(deploymentId);
        return dto;
    }

    private static DeploymentDto createDeploymentDto(String id, String name) {
        DeploymentDto dto = new DeploymentDto();
        dto.setId(id);
        dto.setName(name);
        return dto;
    }
}
