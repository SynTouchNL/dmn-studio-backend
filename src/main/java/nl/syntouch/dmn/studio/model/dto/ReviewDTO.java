package nl.syntouch.dmn.studio.model.dto;

public record ReviewDTO(
        Long changeID,
        boolean approved,
        String comment
)
{ }
