package nl.syntouch.dmn.studio.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import nl.syntouch.dmn.studio.model.DMNVersion;
import nl.syntouch.dmn.studio.model.composites.DMNVersionId;

@ApplicationScoped
public class DmnVersionRepository implements PanacheRepositoryBase<DMNVersion, DMNVersionId> {

}
