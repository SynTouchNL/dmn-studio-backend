package nl.syntouch.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import nl.syntouch.models.Domain;

import java.util.List;

@ApplicationScoped
public class DeploymentService {

    @GET
    public List<Domain> getDomains() {
        return Domain.listAll();
    }
}
