package nl.syntouch.rest;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import nl.syntouch.models.Environment;

public interface EnvironmentResource extends PanacheEntityResource<Environment, Long> {
}
