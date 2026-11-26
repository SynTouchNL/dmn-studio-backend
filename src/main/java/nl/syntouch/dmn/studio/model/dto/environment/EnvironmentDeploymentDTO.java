package nl.syntouch.dmn.studio.model.dto.environment;

import nl.syntouch.dmn.studio.model.dto.deployment.DeploymentDMNDTO;

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
