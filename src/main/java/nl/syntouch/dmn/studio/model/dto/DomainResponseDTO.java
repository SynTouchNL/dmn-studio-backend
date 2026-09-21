package nl.syntouch.dmn.studio.model.dto;

import java.time.LocalDateTime;

public record DomainResponseDTO(Long id, String name, String ownerId, String owner, boolean active,
                                String createdBy, String editedBy, LocalDateTime createdAt, LocalDateTime editedAt) {}
