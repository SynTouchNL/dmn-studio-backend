package nl.syntouch.dmn.studio.resource;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import nl.syntouch.dmn.studio.model.Test;

public interface TestResource extends PanacheEntityResource<Test, Integer> {
}
