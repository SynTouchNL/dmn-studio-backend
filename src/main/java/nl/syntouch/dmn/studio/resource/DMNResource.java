package nl.syntouch.dmn.studio.resource;

import jakarta.annotation.security.RolesAllowed;
import nl.syntouch.dmn.studio.model.DMN;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;

import static nl.syntouch.dmn.studio.DmnStudioConstants.*;

@RolesAllowed({ROLE_DEVELOPER, ROLE_APPROVER, ROLE_DEPLOYER})
public interface DMNResource extends PanacheEntityResource<DMN, Integer> {
}