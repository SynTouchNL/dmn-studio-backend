package nl.syntouch.dmn.studio.model.dto;

public record DMNVersionDTO(
        Long id,
        Integer version,
        byte[] fileBlob,
        Integer status
) {
}