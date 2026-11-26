package nl.syntouch.dmn.studio.model.dto.environment;

public record ConnectionTestResultDTO(boolean success, Integer httpStatus, String message, long durationMs) {}
