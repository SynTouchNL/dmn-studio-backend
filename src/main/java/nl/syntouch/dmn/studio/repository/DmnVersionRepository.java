package nl.syntouch.dmn.studio.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import nl.syntouch.dmn.studio.model.DMNVersion;
import nl.syntouch.dmn.studio.model.composites.DMNVersionId;

@ApplicationScoped
public class DmnVersionRepository implements PanacheRepositoryBase<DMNVersion, DMNVersionId> {

    public Long getNextVersion(Long dmnId) {
        return find("select coalesce(max(v.version), 0) + 1 from DMNVersion v where v.dmn.id = ?1", dmnId)
                .project(Long.class)
                .firstResult();
    }

}
