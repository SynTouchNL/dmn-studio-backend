package nl.syntouch.rest;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.security.Authenticated;
import nl.syntouch.models.DMNVersion;

@Authenticated
public interface DMNVersionResource extends PanacheEntityResource<DMNVersion, Integer> {
}
