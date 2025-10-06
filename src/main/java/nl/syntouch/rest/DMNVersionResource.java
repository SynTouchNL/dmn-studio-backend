package nl.syntouch.rest;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import nl.syntouch.models.DMNVersion;

public interface DMNVersionResource extends PanacheEntityResource<DMNVersion, Integer> {
}
