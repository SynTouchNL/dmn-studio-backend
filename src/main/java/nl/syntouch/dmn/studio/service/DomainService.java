package nl.syntouch.dmn.studio.service;

import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import lombok.RequiredArgsConstructor;
import nl.syntouch.dmn.studio.model.Domain;
import nl.syntouch.dmn.studio.model.dto.*;
import nl.syntouch.dmn.studio.repository.DomainRepository;
import nl.syntouch.dmn.studio.repository.DmnRepository;
import org.keycloak.representations.idm.AbstractUserRepresentation;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@ApplicationScoped
@Transactional
@RequiredArgsConstructor
public class DomainService {
    private final DomainRepository domains;
    private final DmnRepository dmns;
    private final KeycloakService keycloak;
    private final SecurityIdentity identity;

    public DomainPageResponseDTO list(int page, int size) {
        if (page < 0 || size < 1) throw new BadRequestException("Invalid pagination");
        var query = domains.findAll(Sort.by("id")).page(page, size);
        long totalElements = query.count();
        int totalPages = totalElements == 0 ? 0 : (int) ((totalElements - 1) / size + 1);
        List<DomainResponseDTO> items = query.list().stream().map(this::response).toList();
        return new DomainPageResponseDTO(items, page, size, totalElements, totalPages);
    }

    public DomainResponseDTO get(Long id) { return response(find(id)); }

    public DomainResponseDTO create(DomainRequestDTO request) {
        Domain domain = new Domain();
        domain.setName(normalizeAndEnsureUniqueName(request.name(), null));
        domain.setOwner(findEnabledOwner(request.ownerId()));
        domain.setActive(request.active());
        domain.setCreatedDate(Instant.now());
        domain.setCreatedBy(identity.getPrincipal().getName());
        audit(domain);

        domains.persist(domain);
        return response(domain);
    }

    public DomainResponseDTO update(Long id, DomainRequestDTO request) {
        Domain domain = find(id);
        if (!domain.isActive() && !request.active()) {
            throw new WebApplicationException("Domain is inactive and read-only", 409);
        }

        domain.setName(normalizeAndEnsureUniqueName(request.name(), id));
        domain.setOwner(findEnabledOwner(request.ownerId()));
        domain.setActive(request.active());
        audit(domain);

        domains.persist(domain);
        return response(domain);
    }

    public void delete(Long id) {
        Domain domain = find(id);
        if (dmns.count("domain.id", id) != 0) throw new WebApplicationException("Domain still contains DMNs", 409);
        domains.delete(domain);
    }

    private Domain find(Long id) {
        Domain domain = domains.findById(id);
        if (domain == null) throw new NotFoundException("Domain not found");
        return domain;
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
        domain.setModifiedBy(identity.getPrincipal().getName());
        domain.setModifiedDate(Instant.now());
    }

    private DomainResponseDTO response(Domain domain) {
        String ownerId = "Onbekend".equals(domain.getOwner()) ? null : domain.getOwner();
        String displayName = ownerId == null ? "Onbekend" : keycloak.transformUUIDToUsername(ownerId).orElse("Onbekende gebruiker");
        return new DomainResponseDTO(domain.getId(), domain.getName(), ownerId, displayName, domain.isActive(),
                domain.getCreatedBy(), domain.getModifiedBy(), domain.getCreatedDate(), domain.getModifiedDate());
    }
}
