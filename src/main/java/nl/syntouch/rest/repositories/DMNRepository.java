package nl.syntouch.rest.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import nl.syntouch.models.DMN;

public class DMNRepository implements PanacheRepository<DMN> {
    public DMN findByName(String name) {
        return find("name", name).firstResult();
    }

    public DMN findByVersion(Long version) {
        return find("version", version).firstResult();
    }
}
