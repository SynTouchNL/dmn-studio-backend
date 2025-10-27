package nl.syntouch.models.clientresponses;

import java.util.List;
import java.util.Map;

public class DeploymentResponse {
    private List<Map<String, Object>> links;
    private String id;
    private String name;
    private String source;
    private String deploymentTime;
    private String tenantId;
    private Map<String, Object> deployedProcessDefinitions;
    private Map<String, Object> deployedCaseDefinitions;
    private Map<String, Object> deployedDecisionDefinitions;
    private Map<String, Object> deployedDecisionRequirementsDefinitions;

    public DeploymentResponse() {}

    public List<Map<String, Object>> getLinks() { return links; }
    public void setLinks(List<Map<String, Object>> links) { this.links = links; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getDeploymentTime() { return deploymentTime; }
    public void setDeploymentTime(String deploymentTime) { this.deploymentTime = deploymentTime; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public Map<String, Object> getDeployedProcessDefinitions() { return deployedProcessDefinitions; }
    public void setDeployedProcessDefinitions(Map<String, Object> deployedProcessDefinitions) { this.deployedProcessDefinitions = deployedProcessDefinitions; }

    public Map<String, Object> getDeployedCaseDefinitions() { return deployedCaseDefinitions; }
    public void setDeployedCaseDefinitions(Map<String, Object> deployedCaseDefinitions) { this.deployedCaseDefinitions = deployedCaseDefinitions; }

    public Map<String, Object> getDeployedDecisionDefinitions() { return deployedDecisionDefinitions; }
    public void setDeployedDecisionDefinitions(Map<String, Object> deployedDecisionDefinitions) { this.deployedDecisionDefinitions = deployedDecisionDefinitions; }

    public Map<String, Object> getDeployedDecisionRequirementsDefinitions() { return deployedDecisionRequirementsDefinitions; }
    public void setDeployedDecisionRequirementsDefinitions(Map<String, Object> deployedDecisionRequirementsDefinitions) { this.deployedDecisionRequirementsDefinitions = deployedDecisionRequirementsDefinitions; }
}