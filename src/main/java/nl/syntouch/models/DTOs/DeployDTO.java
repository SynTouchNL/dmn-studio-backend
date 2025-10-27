package nl.syntouch.models.DTOs;
import nl.syntouch.models.DMN;
import nl.syntouch.models.Environment;

public class DeployDTO {
    public DMN dmn;
    public Integer version;
    public Environment environment;
    public String tenantId;
    public String deploymentSource;
    public boolean deployChangedOnly;
    public boolean enableDuplicateFiltering;
    public String deploymentName;
    public String deploymentActivationTime;
    public String data;

    public DeployDTO(DMN dmn, Integer version, Environment environment, String tenantId, String deploymentSource, boolean deployChangedOnly, boolean enableDuplicateFiltering, String deploymentName, String deploymentActivationTime, String data) {
        this.dmn = dmn;
        this.version = version;
        this.environment = environment;
        this.tenantId = tenantId;
        this.deploymentSource = deploymentSource;
        this.deployChangedOnly = deployChangedOnly;
        this.enableDuplicateFiltering = enableDuplicateFiltering;
        this.deploymentName = deploymentName;
        this.deploymentActivationTime = deploymentActivationTime;
        this.data = data;
    }
}
