package nl.syntouch.rest;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import nl.syntouch.models.KeyValue;

public interface KeyValueResource extends PanacheEntityResource<KeyValue, Integer> {
}
