package nl.syntouch.rest.custom;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.*;
import nl.syntouch.models.Test;
import java.util.List;

@Path("/test")
//@Authenticated
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class TestResourceCustom {

    @GET
    @Path("/{dmnId}/{version}/tests")
    public List<Test> getTestsByDMNandVersion(@PathParam("dmnId") Integer dmnId, @PathParam("version") Integer version) {
        EntityManager em = Test.getEntityManager();
        TypedQuery<Test> query = em.createQuery("SELECT t FROM Test t WHERE t.dmnVersion.dmn.id = :dmnId AND t.dmnVersion.version = :version", Test.class);
        query.setParameter("dmnId", dmnId);
        query.setParameter("version", version);
        List<Test> results = query.getResultList();
        return results.isEmpty() ? null : results;
    }
}
