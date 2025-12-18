package nl.syntouch.dmn.studio.model.dto;

import nl.syntouch.dmn.studio.model.DMN;

import java.time.Instant;
import java.util.List;

public record DeploymentDMNDTO(
        Long deploymentId,
        Long dmnId,
        String deployedBy,
        Instant deployedTime,
        Long environmentId,
        String environmentName,
        String deploymentRef,
        DMNVersionSubDTO dmnVersion,
        DMN dmn
) {
    public record DMNVersionSubDTO(
            Long version,
            Long status,
            String modifiedBy,
            Instant modifiedDate,
            String createdBy,
            Instant createdDate
    ) {}
}
