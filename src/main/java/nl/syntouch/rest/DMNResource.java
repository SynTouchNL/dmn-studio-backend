package nl.syntouch.rest;

import io.quarkus.security.Authenticated;
import nl.syntouch.models.DMN;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@Authenticated
public interface DMNResource extends PanacheEntityResource<DMN, Integer> {
}