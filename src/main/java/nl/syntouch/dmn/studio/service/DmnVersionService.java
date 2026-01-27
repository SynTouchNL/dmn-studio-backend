package nl.syntouch.dmn.studio.service;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import nl.syntouch.dmn.studio.model.DMN;
import nl.syntouch.dmn.studio.model.DMNVersion;
import nl.syntouch.dmn.studio.model.composites.DMNVersionId;
import nl.syntouch.dmn.studio.model.dto.DMNUpdateFileDTO;
import nl.syntouch.dmn.studio.model.dto.DMNVersionCreateDTO;
import nl.syntouch.dmn.studio.model.dto.DMNVersionUpdateDTO;
import nl.syntouch.dmn.studio.repository.DmnRepository;
import nl.syntouch.dmn.studio.repository.DmnVersionRepository;

@Transactional
@RequiredArgsConstructor
@ApplicationScoped
public class DmnVersionService {

    private final SecurityIdentity identity;
    private final DmnRepository dmnRepository;
    private final DmnVersionRepository dmnVersionRepository;

    public DMNVersion addVersion(Long dmnId, DMNVersionCreateDTO versionDTO) {
        DMN dmn = dmnRepository.findByIdOptional(dmnId).orElseThrow(() -> new NotFoundException("DMN not found"));
        Long nextVersion = dmnVersionRepository.getNextVersion(dmnId);

        DMNVersion version = new DMNVersion();
        version.setVersion(nextVersion);
        version.setDmn(dmn);
        version.setFileBlob(versionDTO.fileBlob());
        version.setCreatedBy(identity.getPrincipal().getName());
        version.setModifiedBy(identity.getPrincipal().getName());
        dmnVersionRepository.persist(version);
        return version;
    }

    public DMNVersion getDmnVersion(Long dmnId, Long versionId) {
        return dmnVersionRepository.findByIdOptional(new DMNVersionId(dmnId, versionId)).orElseThrow(() -> new NotFoundException("DMN Version not found"));
    }

    public void updateFile(Long dmnId, Long versionId, DMNUpdateFileDTO versionDTO) {
        DMNVersion dmnVersion = dmnVersionRepository.findByIdOptional(new DMNVersionId(dmnId, versionId)).orElseThrow(() -> new NotFoundException("DMN Version not found"));
        dmnVersion.setFileBlob(versionDTO.fileBlob());
        dmnVersion.setModifiedBy(identity.getPrincipal().getName());
        dmnVersion.persist();
    }
}
