package nl.syntouch.rest;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import nl.syntouch.models.Domain;

public interface DomainResource extends PanacheEntityResource<Domain, Long> {

}