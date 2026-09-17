package nl.syntouch.dmn.studio.model.dto;

import nl.syntouch.dmn.studio.model.DMN;
import nl.syntouch.dmn.studio.model.DMNVersion;
import nl.syntouch.dmn.studio.model.Domain;

import java.util.List;

public record DMNResponseDTO(
        Long id,
        String name,
        String owner,
        Domain domain,
        List<DMNVersion> versions
) {
    public static DMNResponseDTO from(DMN dmn, String owner) {
        return new DMNResponseDTO(
                dmn.getId(),
                dmn.getName(),
                owner,
                dmn.getDomain(),
                dmn.getVersions()
        );
    }
}
