package nl.syntouch.dmn.studio.model.dto.change;

public record SubmissionDTO(
    Long dmnId,
    Long version,
    String changeDescription,
    String assignedTo
) {}
