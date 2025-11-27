package nl.syntouch.dmn.studio.model.dto;

public record UnittestResultDTO (
        Boolean result,
        String expected,
        String actual
){}