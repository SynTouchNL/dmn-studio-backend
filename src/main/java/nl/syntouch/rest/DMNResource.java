package nl.syntouch.rest;

import nl.syntouch.models.DMN;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;

public interface DMNResource extends PanacheEntityResource<DMN, Integer> {
}