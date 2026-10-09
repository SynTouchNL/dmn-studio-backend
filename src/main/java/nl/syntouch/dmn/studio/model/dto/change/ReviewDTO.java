package nl.syntouch.dmn.studio.model.dto.change;

public record ReviewDTO(
        Long changeID,
        boolean approved,
        String comment
)
{ }
