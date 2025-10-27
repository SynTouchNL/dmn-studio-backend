package nl.syntouch.rest;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.security.Authenticated;
import nl.syntouch.models.Domain;

@Authenticated
public interface DomainResource extends PanacheEntityResource<Domain, Integer> {

}