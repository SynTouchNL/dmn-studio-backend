package nl.syntouch.models.forms;

import org.jboss.resteasy.reactive.PartFilename;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

import java.io.InputStream;

public class DeploymentForm {

    @RestForm("tenant-id")
    public String tenantId;

    @RestForm("deployment-source")
    public String deploymentSource;

    @RestForm("deploy-changed-only")
    public Boolean deployChangedOnly;

    @RestForm("enable-duplicate-filtering")
    public Boolean enableDuplicateFiltering;

    @RestForm("deployment-name")
    public String deploymentName;

    @RestForm("deployment-activation-time")
    public String deploymentActivationTime;

    @RestForm("data")
    @PartType("application/xml")
    @PartFilename("deployment.dmn")
    public InputStream data;
}
