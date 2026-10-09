package nl.syntouch.dmn.studio.model.dto.dmn;

public record DMNCreateDTO(
        String name,
        String owner,
        Long domainId,
        byte[] fileBlob,
        String createdBy
) {}