package nl.syntouch.rest;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.security.Authenticated;
import nl.syntouch.models.Deployment;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@Authenticated
public interface DeploymentResource extends PanacheEntityResource<Deployment, Integer> {

}
