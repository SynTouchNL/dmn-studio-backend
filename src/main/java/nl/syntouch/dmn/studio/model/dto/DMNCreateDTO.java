package nl.syntouch.dmn.studio.model.dto;

public record DMNCreateDTO(
        String name,
        String owner,
        Integer domainId,
        byte[] fileBlob,
        String createdBy
) {}