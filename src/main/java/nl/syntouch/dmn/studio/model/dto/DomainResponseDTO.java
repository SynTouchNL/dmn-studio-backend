package nl.syntouch.dmn.studio.model.dto;

import java.time.Instant;

public record DomainResponseDTO(Long id, String name, String ownerId, String owner, boolean active,
                                String createdBy, String editedBy, Instant createdAt, Instant editedAt) {}
