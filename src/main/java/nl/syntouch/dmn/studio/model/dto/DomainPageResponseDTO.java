package nl.syntouch.dmn.studio.model.dto;

import java.util.List;

public record DomainPageResponseDTO(
        List<DomainResponseDTO> items,
        int page,
        int size,
        long totalElements,
        int totalPages
) {}
