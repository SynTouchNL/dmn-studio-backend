package nl.syntouch.dmn.studio.service;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import nl.syntouch.dmn.studio.model.DMN;
import nl.syntouch.dmn.studio.model.DMNVersion;
import nl.syntouch.dmn.studio.model.Domain;
import nl.syntouch.dmn.studio.model.dto.DMNCreateDTO;
import nl.syntouch.dmn.studio.repository.DmnRepository;
import nl.syntouch.dmn.studio.repository.DomainRepository;

import java.util.Collections;

@Transactional
@RequiredArgsConstructor
@ApplicationScoped
public class DmnService {

    private final SecurityIdentity identity;
    private final DomainRepository domainRepository;
    private final DmnRepository dmnRepository;

    public DMN createDmn(DMNCreateDTO dmnDTO) {
        Domain domain = domainRepository.findByIdOptional(dmnDTO.domainId())
                .orElseThrow(() -> new NotFoundException("Domain not found"));

        DMN dmn = new DMN();
        dmn.setName(dmnDTO.name());
        dmn.setOwner(dmnDTO.owner());
        dmn.setDomain(domain);

        DMNVersion version = new DMNVersion();
        version.setDmn(dmn);
        version.setFileBlob(dmnDTO.fileBlob());
        version.setCreatedBy(identity.getPrincipal().getName());

        dmn.setVersions(Collections.singletonList(version));
        dmnRepository.persist(dmn);

        return dmn;
    }
}
