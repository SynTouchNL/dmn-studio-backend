package nl.syntouch.dmn.studio.model.composites;

import java.io.Serializable;
import java.util.Objects;

public class DeploymentId implements Serializable {
    private Long id;
    private DMNVersionId version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeploymentId that)) return false;
        return Objects.equals(id, that.id) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }
}
