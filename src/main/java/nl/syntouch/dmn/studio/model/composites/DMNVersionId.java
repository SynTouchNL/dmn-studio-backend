package nl.syntouch.dmn.studio.model.composites;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DMNVersionId implements Serializable {
    private Integer dmn;
    private Integer version;

    public DMNVersionId() {}

    public DMNVersionId(Integer dmn, Integer version) {
        this.dmn = dmn;
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DMNVersionId)) return false;
        DMNVersionId that = (DMNVersionId) o;
        return Objects.equals(dmn, that.dmn) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dmn, version);
    }
}
