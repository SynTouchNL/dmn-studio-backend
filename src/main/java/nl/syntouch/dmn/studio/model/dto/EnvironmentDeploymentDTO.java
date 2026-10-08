package nl.syntouch.dmn.studio.model.dto;

import java.time.Instant;

/**
 * Same JSON shape as the Deployment entity previously serialized under Environment.deployments,
 * extended with dmnId and dmnName.
 */
public record EnvironmentDeploymentDTO(
        Long id,
        DeploymentDMNDTO.DMNVersionSubDTO version,
        String deployedBy,
        Instant deployedTime,
        String deploymentRef,
        Long dmnId,
        String dmnName
) {}
