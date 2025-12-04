package nl.syntouch.dmn.studio.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import nl.syntouch.dmn.studio.model.Change;

@ApplicationScoped
public class ChangeRepository implements PanacheRepository<Change> {

}
