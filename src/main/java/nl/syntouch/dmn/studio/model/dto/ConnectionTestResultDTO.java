package nl.syntouch.dmn.studio.model.dto;

public record ConnectionTestResultDTO(boolean success, Integer httpStatus, String message, long durationMs) {}
