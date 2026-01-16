package nl.syntouch.dmn.studio.service;

import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import nl.syntouch.dmn.studio.model.*;
import nl.syntouch.dmn.studio.model.composites.DMNVersionId;
import nl.syntouch.dmn.studio.model.dto.DeployDTO;
import nl.syntouch.dmn.studio.repository.DmnRepository;
import nl.syntouch.dmn.studio.repository.DmnVersionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.Principal;
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
    @Transactional
    @DisplayName("Should get deployment with DMN when valid ID is provided")
    void getsDeploymentWithDmnWhenValidIdProvided() {
        Long deploymentId = 1L;
        Long versionId = 1L;

        DMNVersion dmnVersion = createDmnVersion(versionId);
        dmnVersion.persist();

        Deployment deployment = new Deployment();
        deployment.setId(deploymentId);
        deployment.setVersion(dmnVersion);
        deployment.setDeployedTo(testEnvironment);
        deployment.setDeployedBy("testUser");
        deployment.setDeploymentRef("deploymentRef");
        deployment.persist();

        var result = dmnDeploymentService.getDeploymentWithDMN(deploymentId);

        assertNotNull(result);
        assertEquals(deploymentId, result.deploymentId());
        assertEquals(testDmn.getId(), result.dmnId());
        assertEquals("testUser", result.deployedBy());
    }

    private DMNVersion createDmnVersion(Long version) {
        DMNVersion dmnVersion = new DMNVersion();
        dmnVersion.setDmn(testDmn);
        dmnVersion.setVersion(version);
        dmnVersion.setFileBlob(new byte[]{1, 2, 3});
        dmnVersion.setCreatedBy("TestUser");
        return dmnVersion;
    }

    private DeployDTO createDefaultDeployDTO(DMN dmn, Long version) {
        Environment mockEnv = new Environment();
        mockEnv.setId(1L);
        mockEnv.setName("Test Environment");

        return new DeployDTO(
                dmn,
                version,
                mockEnv,
                "tenantId",
                "source",
                true,
                true,
                "deploymentName",
                null,
                "data"
        );
    }
}
