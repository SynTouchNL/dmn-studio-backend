package nl.syntouch.dmn.studio.model.dto.test;

public record UnittestResultDTO (
        Boolean result,
        String expected,
        String actual
){}