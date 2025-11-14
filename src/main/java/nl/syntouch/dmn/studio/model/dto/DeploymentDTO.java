package nl.syntouch.dmn.studio.model.dto;

import nl.syntouch.dmn.studio.model.DMN;
import nl.syntouch.dmn.studio.model.DMNVersion;

import java.time.Instant;

public record DeploymentDTO (
        Long id,
        String deployedBy,
        Instant deployedTime,
        String environmentName,
        Long dmnId,
        DMNVersion dmnVersion,
        DMN dmnName,
        String deploymentRef
) {}
