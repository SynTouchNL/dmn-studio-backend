package nl.syntouch.dmn.studio.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import nl.syntouch.dmn.studio.model.Test;

@ApplicationScoped
public class UnittestRepository implements PanacheRepository<Test>  {

    public Test findTest(Long dmnId, Long version, Long testId) {
        return find("dmn.id = ?1 and version = ?2 and id = ?3", dmnId, version, testId).firstResult();
    }

}
