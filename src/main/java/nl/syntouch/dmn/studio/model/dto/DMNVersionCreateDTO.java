package nl.syntouch.dmn.studio.model.dto;

public record DMNVersionCreateDTO(
        Integer dmnId,
        byte[] fileBlob,
        String createdBy
) {}
