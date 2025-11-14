package nl.syntouch.dmn.studio.resource;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.security.Authenticated;
import nl.syntouch.dmn.studio.model.Environment;

@Authenticated
public interface EnvironmentResource extends PanacheEntityResource<Environment, Integer> {
}
