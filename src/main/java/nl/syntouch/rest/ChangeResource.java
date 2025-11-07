package nl.syntouch.rest;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import nl.syntouch.models.Change;

public interface ChangeResource extends PanacheEntityResource<Change, Integer> {
}
