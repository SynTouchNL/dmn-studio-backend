package nl.syntouch.dmn.studio.service;

import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import lombok.RequiredArgsConstructor;
import nl.syntouch.dmn.studio.model.Domain;
import nl.syntouch.dmn.studio.model.dto.*;
import nl.syntouch.dmn.studio.repository.DomainRepository;
import nl.syntouch.dmn.studio.repository.DmnRepository;
import org.keycloak.representations.idm.AbstractUserRepresentation;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
@RequiredArgsConstructor
public class DomainService {
    private final DomainRepository domains;
    private final DmnRepository dmns;
    private final KeycloakService keycloak;
    private final SecurityIdentity identity;

    public List<DomainResponseDTO> list(int page, int size) {
        if (page < 0 || size < 1) throw new BadRequestException("Invalid pagination");
        return domains.findAll(Sort.by("id")).page(page, size).list().stream().map(this::response).toList();
    }

    public DomainResponseDTO get(Long id) { return response(find(id)); }

    public DomainResponseDTO create(DomainRequestDTO request) {
        Domain domain = new Domain();
        domain.setName(normalizeAndEnsureUniqueName(request.name(), null));
        domain.setOwner(findEnabledOwner(request.ownerId()));
        domain.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));
        domain.setCreatedBy(identity.getPrincipal().getName());
        audit(domain);

        domains.persist(domain);
        return response(domain);
    }

    public DomainResponseDTO update(Long id, DomainRequestDTO request) {
        Domain domain = find(id);
        requireActive(domain);

        domain.setName(normalizeAndEnsureUniqueName(request.name(), id));
        domain.setOwner(findEnabledOwner(request.ownerId()));
        audit(domain);

        domains.persist(domain);
        return response(domain);
    }

    public DomainResponseDTO setActive(Long id, boolean active) {
        Domain domain = find(id);

        if (domain.isActive() != active) {
            domain.setActive(active);
            audit(domain);
            domains.persist(domain);
        }

        return response(domain);
    }

    public void delete(Long id) {
        Domain domain = find(id);
        requireActive(domain);
        if (dmns.count("domain.id", id) != 0) throw new WebApplicationException("Domain still contains DMNs", 409);
        domains.delete(domain);
    }

    private Domain find(Long id) {
        Domain domain = domains.findById(id);
        if (domain == null) throw new NotFoundException("Domain not found");
        return domain;
    }

    public static void requireActive(Domain domain) {
        if (domain == null) throw new NotFoundException("Domain not found");
        if (!domain.isActive()) throw new WebApplicationException("Domain is inactive and read-only", 409);
    }

    private String normalizeAndEnsureUniqueName(String value, Long id) {
        String name = value.trim();
        boolean exists = id == null ? domains.count("lower(name) = lower(?1)", name) > 0
                : domains.count("lower(name) = lower(?1) and id <> ?2", name, id) > 0;
        if (exists) throw new WebApplicationException("Domain name already exists", 409);
        return name;
    }

    private String findEnabledOwner(String ownerId) {
        return keycloak.getUserById(ownerId)
                .map(AbstractUserRepresentation::getId)
                .orElseThrow(() -> new BadRequestException("Owner does not exist"));
    }

    private void audit(Domain domain) {
        domain.setEditedBy(identity.getPrincipal().getName());
        domain.setEditedAt(LocalDateTime.now(ZoneOffset.UTC));
    }

    private DomainResponseDTO response(Domain domain) {
        String ownerId = "Onbekend".equals(domain.getOwner()) ? null : domain.getOwner();
        String displayName = ownerId == null ? "Onbekend" : keycloak.transformUUIDToUsername(ownerId).orElse("Onbekende gebruiker");
        return new DomainResponseDTO(domain.getId(), domain.getName(), ownerId, displayName, domain.isActive(),
                domain.getCreatedBy(), domain.getEditedBy(), domain.getCreatedAt(), domain.getEditedAt());
    }
}
