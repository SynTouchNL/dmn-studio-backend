package nl.syntouch.dmn.studio.model.dto.dmn;

public record DMNUpdateFileDTO(
        byte[] fileBlob,
        String updatedBy
) {}