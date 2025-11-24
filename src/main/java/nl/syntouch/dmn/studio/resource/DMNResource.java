package nl.syntouch.dmn.studio.resource;

import io.quarkus.security.Authenticated;
import nl.syntouch.dmn.studio.model.DMN;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;

@Authenticated
public interface DMNResource extends PanacheEntityResource<DMN, Integer> {
}