package nl.syntouch.dmn.studio.model.dto.dmn;

public record DMNVersionDTO(
        Long id,
        Long version,
        byte[] fileBlob,
        Long status
) {
}