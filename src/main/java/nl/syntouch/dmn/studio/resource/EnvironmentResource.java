package nl.syntouch.dmn.studio.resource;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import jakarta.annotation.security.RolesAllowed;
import nl.syntouch.dmn.studio.model.Environment;

import static nl.syntouch.dmn.studio.DmnStudioConstants.ROLE_ADMIN;
import static nl.syntouch.dmn.studio.DmnStudioConstants.ROLE_DEPLOYER;

@RolesAllowed({ROLE_ADMIN, ROLE_DEPLOYER})
public interface EnvironmentResource extends PanacheEntityResource<Environment, Integer> {
}
