package nl.syntouch.dmn.studio.model.dto.environment;

import java.time.Instant;
import java.util.List;

public record EnvironmentResponseDTO(Long id, String name, String url, String username, boolean passwordSet,
                                     boolean active, String createdBy, String updatedBy, Instant createdAt,
                                     Instant updatedAt, List<EnvironmentDeploymentDTO> deployments) {}
