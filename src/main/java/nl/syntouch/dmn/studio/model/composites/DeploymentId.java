package nl.syntouch.dmn.studio.model.composites;

import java.io.Serializable;
import java.util.Objects;

public class DeploymentId implements Serializable {
    public Integer id;
    public DMNVersionId version;

    public DeploymentId() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeploymentId)) return false;
        DeploymentId that = (DeploymentId) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }
}
