package nl.syntouch.dmn.studio.resource;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import jakarta.annotation.security.RolesAllowed;
import nl.syntouch.dmn.studio.model.Test;

import static nl.syntouch.dmn.studio.DmnStudioConstants.*;

@RolesAllowed({ROLE_DEVELOPER, ROLE_DEPLOYER})
public interface TestResource extends PanacheEntityResource<Test, Integer> {
}
