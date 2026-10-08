package nl.syntouch.dmn.studio.model.dto;

import java.time.Instant;

public record EnvironmentDeploymentDTO(
        Long id,
        DeploymentDMNDTO.DMNVersionSubDTO version,
        String deployedBy,
        Instant deployedTime,
        String deploymentRef,
        Long dmnId,
        String dmnName
) {}
