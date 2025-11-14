package nl.syntouch.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import nl.syntouch.models.DMN;

@ApplicationScoped
public class DmnRepository implements PanacheRepository<DMN> {

}
