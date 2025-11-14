package nl.syntouch.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import nl.syntouch.models.DMNVersion;
import nl.syntouch.models.composites.DMNVersionId;

@ApplicationScoped
public class DmnVersionRepository implements PanacheRepositoryBase<DMNVersion, DMNVersionId> {

}
