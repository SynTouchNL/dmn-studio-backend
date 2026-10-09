package nl.syntouch.dmn.studio.model.dto.dmn;

public record DMNVersionCreateDTO(
        Integer dmnId,
        byte[] fileBlob,
        String createdBy
) {}
