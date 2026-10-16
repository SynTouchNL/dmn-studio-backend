package nl.syntouch.dmn.studio.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import nl.syntouch.dmn.studio.model.Environment;

import java.util.List;

@ApplicationScoped
public class EnvironmentRepository implements PanacheRepository<Environment> {

    public List<Environment> listVisibleWithDeployments() {
        return find("""
                select distinct e from Environment e
                left join fetch e.deployments d
                left join fetch d.version v
                left join fetch v.dmn
                where e.internal = false
                order by e.id""").list();
    }

    public Environment findVisible(Long id) {
        return find("id = ?1 and internal = false", id).firstResult();
    }
}
