package nl.syntouch.dmn.studio.service;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import nl.syntouch.dmn.studio.model.DMN;
import nl.syntouch.dmn.studio.model.DMNVersion;
import nl.syntouch.dmn.studio.model.Domain;
import nl.syntouch.dmn.studio.model.composites.DMNVersionId;
import nl.syntouch.dmn.studio.model.dto.DMNUpdateFileDTO;
import nl.syntouch.dmn.studio.model.dto.DMNVersionCreateDTO;
import nl.syntouch.dmn.studio.model.dto.DMNVersionUpdateDTO;
import nl.syntouch.dmn.studio.repository.DmnRepository;
import nl.syntouch.dmn.studio.repository.DmnVersionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
@DisplayName("DMN Version Service Tests")
class DmnVersionServiceTest {

    @InjectMock
    DmnRepository dmnRepository;

    @InjectMock
    DmnVersionRepository dmnVersionRepository;

    @Inject
    DmnVersionService dmnVersionService;

    private DMN testDmn;

    @BeforeEach
    @Transactional
    void setup() {
        // Persist a test Domain first (required by DMN)
        Domain testDomain = new Domain();
        testDomain.setName("Test Domain");
        testDomain.persist();

        // Persist a test DMN to the database to satisfy foreign key constraints
        testDmn = new DMN();
        testDmn.setName("Test DMN");
        testDmn.setOwner("Test Owner");
        testDmn.setDomain(testDomain);
        testDmn.persist();
    }

    @Test
    @DisplayName("Should create new version when DMN exists")
    void addVersionCreatesNewVersionWhenDmnExists() {
        Long dmnId = 1L;
        DMN dmn = new DMN();
        DMNVersionCreateDTO versionDTO = createVersionCreateDTO(dmnId, "createdBy");
        when(dmnRepository.findByIdOptional(dmnId)).thenReturn(Optional.of(dmn));
        when(dmnVersionRepository.getNextVersion(dmnId)).thenReturn(2L);

        DMNVersion result = dmnVersionService.addVersion(dmnId, versionDTO);

        assertNotNull(result);
        assertEquals(2L, result.getVersion());
        assertEquals(dmn, result.getDmn());
        assertArrayEquals(new byte[]{1, 2, 3}, result.getFileBlob());
        assertEquals("createdBy", result.getCreatedBy());
        verify(dmnVersionRepository).persist(result);
    }

    @Test
    @DisplayName("Should throw NotFoundException when DMN does not exist")
    void addVersionThrowsNotFoundExceptionWhenDmnDoesNotExist() {
        Long dmnId = 1L;
        DMNVersionCreateDTO versionDTO = createVersionCreateDTO(dmnId, "createdBy");
        when(dmnRepository.findByIdOptional(dmnId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> dmnVersionService.addVersion(dmnId, versionDTO));
    }

    @Test
    @Transactional
    @DisplayName("Should update status and modifiedBy when version exists")
    void updateVersionUpdatesStatusAndModifiedByWhenVersionExists() {
        Long versionId = 1L;
        DMNVersion dmnVersion = createDmnVersion(versionId, 2);
        DMNVersionUpdateDTO versionDTO = createVersionUpdateDTO(3, "modifiedBy");
        when(dmnVersionRepository.findByIdOptional(new DMNVersionId(testDmn.getId(), versionId)))
                .thenReturn(Optional.of(dmnVersion));

        DMNVersion result = dmnVersionService.updateVersion(testDmn.getId(), versionId, versionDTO);

        assertNotNull(result);
        assertEquals(3, result.getStatus());
        assertEquals("modifiedBy", result.getModifiedBy());
    }

    @Test
    @Transactional
    @DisplayName("Should throw BadRequestException when version is production or archived")
    void updateVersionThrowsBadRequestExceptionWhenVersionIsProductionOrArchived() {
        Long versionId = 1L;
        DMNVersion dmnVersion = createDmnVersion(versionId, 4);
        DMNVersionUpdateDTO versionDTO = createVersionUpdateDTO(3, "modifiedBy");
        when(dmnVersionRepository.findByIdOptional(new DMNVersionId(testDmn.getId(), versionId)))
                .thenReturn(Optional.of(dmnVersion));

        assertThrows(BadRequestException.class,
                () -> dmnVersionService.updateVersion(testDmn.getId(), versionId, versionDTO));
    }

    @Test
    @DisplayName("Should throw NotFoundException when version does not exist during update")
    void updateVersionThrowsNotFoundExceptionWhenVersionDoesNotExist() {
        Long dmnId = 1L;
        Long versionId = 1L;
        DMNVersionUpdateDTO versionDTO = createVersionUpdateDTO(3, "modifiedBy");
        when(dmnVersionRepository.findByIdOptional(new DMNVersionId(dmnId, versionId)))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> dmnVersionService.updateVersion(dmnId, versionId, versionDTO));
    }

    @Test
    @DisplayName("Should return version when it exists")
    void getDmnVersionReturnsVersionWhenExists() {
        Long versionId = 1L;
        DMNVersion dmnVersion = createDmnVersion(versionId, 2);
        when(dmnVersionRepository.findByIdOptional(new DMNVersionId(testDmn.getId(), versionId)))
                .thenReturn(Optional.of(dmnVersion));

        DMNVersion result = dmnVersionService.getDmnVersion(testDmn.getId(), versionId);

        assertNotNull(result);
        assertEquals(dmnVersion, result);
    }

    @Test
    @DisplayName("Should throw NotFoundException when version does not exist during get")
    void getDmnVersionThrowsNotFoundExceptionWhenVersionDoesNotExist() {
        Long dmnId = 1L;
        Long versionId = 1L;
        when(dmnVersionRepository.findByIdOptional(new DMNVersionId(dmnId, versionId)))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> dmnVersionService.getDmnVersion(dmnId, versionId));
    }

    @Test
    @Transactional
    @DisplayName("Should update file blob and modifiedBy when version exists")
    void updateFileUpdatesFileBlobAndModifiedByWhenVersionExists() {
        Long versionId = 1L;
        DMNVersion dmnVersion = createDmnVersion(versionId, 2);
        DMNUpdateFileDTO versionDTO = createUpdateFileDTO("modifiedBy");
        when(dmnVersionRepository.findByIdOptional(new DMNVersionId(testDmn.getId(), versionId)))
                .thenReturn(Optional.of(dmnVersion));

        dmnVersionService.updateFile(testDmn.getId(), versionId, versionDTO);

        assertArrayEquals(new byte[]{4, 5, 6}, dmnVersion.getFileBlob());
        assertEquals("modifiedBy", dmnVersion.getModifiedBy());
    }

    @Test
    @DisplayName("Should throw NotFoundException when version does not exist during file update")
    void updateFileThrowsNotFoundExceptionWhenVersionDoesNotExist() {
        Long dmnId = 1L;
        Long versionId = 1L;
        DMNUpdateFileDTO versionDTO = createUpdateFileDTO("modifiedBy");
        when(dmnVersionRepository.findByIdOptional(new DMNVersionId(dmnId, versionId)))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> dmnVersionService.updateFile(dmnId, versionId, versionDTO));
    }

    private DMNVersion createDmnVersion(Long versionId, int status) {
        DMNVersion dmnVersion = new DMNVersion();
        dmnVersion.setDmn(testDmn);
        dmnVersion.setVersion(versionId);
        dmnVersion.setStatus(status);
        dmnVersion.setCreatedBy("TestUser");
        dmnVersion.setFileBlob(new byte[]{1, 2, 3});
        return dmnVersion;
    }

    private static DMNVersionCreateDTO createVersionCreateDTO(Long dmnId, String createdBy) {
        return new DMNVersionCreateDTO(dmnId.intValue(), new byte[]{1, 2, 3}, createdBy);
    }

    private static DMNVersionUpdateDTO createVersionUpdateDTO(int status, String modifiedBy) {
        return new DMNVersionUpdateDTO(status, modifiedBy);
    }

    private static DMNUpdateFileDTO createUpdateFileDTO(String modifiedBy) {
        return new DMNUpdateFileDTO(new byte[]{4, 5, 6}, modifiedBy);
    }
}
