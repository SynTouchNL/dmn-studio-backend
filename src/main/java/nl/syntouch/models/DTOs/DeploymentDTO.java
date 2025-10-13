package nl.syntouch.models.DTOs;

import nl.syntouch.models.DMN;
import nl.syntouch.models.DMNVersion;
import nl.syntouch.models.Deployment;
import nl.syntouch.models.Domain;

import java.time.Instant;

public class DeploymentDTO {
    private Integer id;
    private String deployedBy;
    private Instant deployedTime;
    private String environmentName;
    private Integer dmnId;
    private DMNVersion dmnVersion;
    private DMN dmnName;

    public DeploymentDTO(Deployment deployment) {
        this.id = deployment.getId();
        this.deployedBy = deployment.getDeployedBy();
        this.deployedTime = deployment.getDeployedTime();
        this.environmentName = deployment.getDeployedTo().getName();
        this.dmnId = deployment.getVersion().getDmn().getId();
        this.dmnVersion = deployment.getVersion();
        this.dmnName = deployment.getVersion().getDmn();
    }

    public Integer getId() { return id; }
    public String getDeployedBy() { return deployedBy; }
    public Instant getDeployedTime() { return deployedTime; }
    public String getEnvironmentName() { return environmentName; }
    public Integer getDmnId() { return dmnId; }
    public DMNVersion getDmnVersion() { return dmnVersion; }
    public DMN getDmn() { return dmnName; }
}