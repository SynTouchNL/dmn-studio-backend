package nl.syntouch.dmn.studio.resource;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import nl.syntouch.dmn.studio.model.KeyValue;

public interface KeyValueResource extends PanacheEntityResource<KeyValue, Integer> {
}
