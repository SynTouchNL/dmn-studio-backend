package nl.syntouch.dmn.studio.model.dto;

public record DMNVersionDTO(
        Long id,
        Long version,
        byte[] fileBlob,
        Integer status
) {
}