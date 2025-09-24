package nl.syntouch.rest;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import nl.syntouch.models.DMN;

public interface DMNResource extends PanacheEntityResource<DMN, Long> {

}