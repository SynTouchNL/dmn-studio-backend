package nl.syntouch.dmn.studio.model.dto;

public record DMNUpdateFileDTO(
        byte[] fileBlob,
        String updatedBy
) {}