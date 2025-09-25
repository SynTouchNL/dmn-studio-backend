package nl.syntouch.rest;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import nl.syntouch.models.Deployment;

public interface DeploymentResource extends PanacheEntityResource<Deployment, Long> {

}
