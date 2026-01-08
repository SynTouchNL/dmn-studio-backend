package nl.syntouch.dmn.studio.resource;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import jakarta.annotation.security.RolesAllowed;
import nl.syntouch.dmn.studio.model.KeyValue;

import static nl.syntouch.dmn.studio.DmnStudioConstants.*;

@RolesAllowed({ROLE_DEVELOPER, ROLE_APPROVER, ROLE_DEPLOYER})
public interface KeyValueResource extends PanacheEntityResource<KeyValue, Integer> {
}
