package nl.syntouch.dmn.studio.model.dto;

public record DMNCreateDTO(
        String name,
        String owner,
        Long domainId,
        byte[] fileBlob,
        String createdBy
) {}