package nl.syntouch.rest;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.security.Authenticated;
import nl.syntouch.models.Environment;

@Authenticated
public interface EnvironmentResource extends PanacheEntityResource<Environment, Integer> {
}
