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
import nl.syntouch.dmn.studio.model.dto.DMNResponseDTO;
import nl.syntouch.dmn.studio.repository.DmnRepository;
import nl.syntouch.dmn.studio.repository.DomainRepository;
import org.keycloak.representations.idm.AbstractUserRepresentation;

import java.util.Collections;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@ApplicationScoped
public class DmnService {

    private final SecurityIdentity identity;
    private final DomainRepository domainRepository;
    private final DmnRepository dmnRepository;
    private final KeycloakService keycloakService;

    public DMN createDmn(DMNCreateDTO dmnDTO) {
        Domain domain = domainRepository.findByIdOptional(dmnDTO.domainId())
                .orElseThrow(() -> new NotFoundException("Domain not found"));

        String ownerId = keycloakService.getUserByUsername(dmnDTO.owner())
                .map(AbstractUserRepresentation::getId)
                .orElse(null);

        DMN dmn = new DMN();
        dmn.setName(dmnDTO.name());
        dmn.setOwner(ownerId);
        dmn.setDomain(domain);

        DMNVersion version = new DMNVersion();
        version.setDmn(dmn);
        version.setFileBlob(dmnDTO.fileBlob());
        version.setCreatedBy(identity.getPrincipal().getName());

        dmn.setVersions(Collections.singletonList(version));
        dmnRepository.persist(dmn);

        return dmn;
    }

    public List<DMNResponseDTO> getDMNs() {
        return dmnRepository.listAll().stream()
                .map(dmn -> DMNResponseDTO.from(dmn, getOwnerDisplayName(dmn.getOwner())))
                .toList();
    }

    private String getOwnerDisplayName(String ownerId) {
        return keycloakService.getUserById(ownerId)
                .map(owner -> owner.getFirstName() + " " + owner.getLastName())
                .orElse(ownerId); // previously used username as owner, so ownerId will be username
    }
}
