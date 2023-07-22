package de.remsfal.service.boundary;

import java.net.URI;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import de.remsfal.core.UserEndpoint;
import de.remsfal.core.dto.UserJson;
import de.remsfal.core.model.CustomerModel;
import de.remsfal.service.boundary.authentication.RemsfalPrincipal;
import de.remsfal.service.control.UserController;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@Path(UserEndpoint.CONTEXT + "/" + UserEndpoint.VERSION + "/" + UserEndpoint.SERVICE)
public class UserResource implements UserEndpoint {

    @Context
    UriInfo uri;

    @Inject
    RemsfalPrincipal principal;
    
    @Inject
    UserController controller;

    @Inject
    JsonWebToken jwt;
    @Override
    public Response createUser(final UserJson user) {
        // TODO: validate user
        final CustomerModel userModel = controller.createUser(principal);
        final URI location = uri.getAbsolutePathBuilder().path(userModel.getId()).build();
        return Response.created(location).build();
    }

    @Override
    public String restrictedToTester(@Context SecurityContext ctx) {
        System.out.println("testjwt " + jwt.getGroups());

        return "testerresource";
    }

    @Override
    public String accessibleToEveryone(SecurityContext ctx) {
        return "everyonesreource";
    }

    @Override
    public UserJson getUser(final String userId) {
        try {
            final CustomerModel user = controller.getUser(userId);
            return UserJson.valueOf(user);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid user id", e);
        }
    }



    @Override
    public UserJson updateUser(final String userId, final UserJson user) {
        if (user.getId() != null) {
            throw new BadRequestException("User ID should not be set in payload");
        } else {
            //user.setId(userId);
        }
        final CustomerModel updatedUser = controller.updateUser(user);
        return UserJson.valueOf(updatedUser);
    }

    @Override
    public void deleteUser(final String userId) {
        if (!controller.deleteUser(userId)) {
            throw new NotFoundException();
        }
    }

}