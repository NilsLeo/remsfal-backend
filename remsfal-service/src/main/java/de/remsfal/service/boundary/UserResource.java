package de.remsfal.service.boundary;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import de.remsfal.core.UserEndpoint;
import de.remsfal.core.dto.UserJson;
import de.remsfal.core.model.CustomerModel;
import de.remsfal.service.boundary.authentication.RemsfalPrincipal;
import de.remsfal.service.control.UserController;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

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
    JwtService service;

    @Override
    public Response createUser(final UserJson user) {
        // TODO: validate user
        final CustomerModel userModel = controller.createUser(principal);
        final URI location = uri.getAbsolutePathBuilder().path(userModel.getId()).build();
        return Response.created(location).build();
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
    public Response authenticate(final String bearerToken) {
        System.out.println("testauth");
        String token = bearerToken.replace("Bearer ", "");
        DecodedJWT jwt = JWT.decode(token);
        String issuer = String.valueOf(jwt.getClaim("iss"));
        System.out.println("issuer" + issuer);
        if(issuer.equals("accounts.google.com")){

            try {
                String userId = jwt.getSubject();
                final CustomerModel user = controller.getUserByTokenId(userId);
                System.out.println("email" + user.getEmail());
                String remsfalJwt = service.generateJWT(user);
                return Response.ok(remsfalJwt).build();
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Invalid user id", e);
            }
        } else {
            return Response.status(Response.Status.FORBIDDEN).entity("You have already been logged In").build();
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