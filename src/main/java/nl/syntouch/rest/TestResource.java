package nl.syntouch.rest;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import nl.syntouch.models.Test;

public interface TestResource extends PanacheEntityResource<Test, Integer> {
}
