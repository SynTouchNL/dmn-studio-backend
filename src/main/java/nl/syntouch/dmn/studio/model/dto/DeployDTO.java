package nl.syntouch.dmn.studio.model.dto;
import nl.syntouch.dmn.studio.model.DMN;
import nl.syntouch.dmn.studio.model.Environment;

import java.time.OffsetDateTime;

public record DeployDTO (
    DMN dmn,
    Integer version,
    Environment environment,
    String tenantId,
    String deploymentSource,
    boolean deployChangedOnly,
    boolean enableDuplicateFiltering,
    String deploymentName,
    OffsetDateTime deploymentActivationTime,
    String data
) {}
