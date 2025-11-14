package nl.syntouch.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import nl.syntouch.models.Deployment;

@ApplicationScoped
public class DeploymentRepository implements PanacheRepository<Deployment> {
}
