package nl.syntouch.dmn.studio.resource;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.security.Authenticated;
import nl.syntouch.dmn.studio.model.Deployment;

@Authenticated
public interface DeploymentResource extends PanacheEntityResource<Deployment, Integer> {

}
