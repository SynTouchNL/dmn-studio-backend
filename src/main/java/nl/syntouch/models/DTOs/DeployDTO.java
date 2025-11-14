package nl.syntouch.models.DTOs;
import nl.syntouch.models.DMN;
import nl.syntouch.models.Environment;

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
