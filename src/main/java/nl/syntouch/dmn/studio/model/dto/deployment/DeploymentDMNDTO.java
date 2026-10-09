package nl.syntouch.dmn.studio.model.dto.deployment;

import nl.syntouch.dmn.studio.model.DMN;
import nl.syntouch.dmn.studio.model.DMNVersion;

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
    ) {
        public static DMNVersionSubDTO from(DMNVersion version) {
            return new DMNVersionSubDTO(version.getVersion(), version.getStatus(), version.getModifiedBy(),
                    version.getModifiedDate(), version.getCreatedBy(), version.getCreatedDate());
        }
    }
}
