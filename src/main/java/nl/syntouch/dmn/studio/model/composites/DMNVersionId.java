package nl.syntouch.dmn.studio.model.composites;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@RequiredArgsConstructor
@Embeddable
public class DMNVersionId implements Serializable {
    private Long dmn;
    private Long version;

    public DMNVersionId(Long dmn, Long version) {
        this.dmn = dmn;
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DMNVersionId that)) return false;
        return Objects.equals(dmn, that.dmn) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dmn, version);
    }
}
