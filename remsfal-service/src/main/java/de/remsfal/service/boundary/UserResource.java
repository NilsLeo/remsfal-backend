package de.remsfal.service.boundary;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import de.remsfal.core.UserEndpoint;
import de.remsfal.core.dto.ImmutableUserJson;
import de.remsfal.core.dto.UserJson;
import de.remsfal.core.model.CustomerModel;
import de.remsfal.core.model.UserModel;
import de.remsfal.service.boundary.authentication.RemsfalPrincipal;
import de.remsfal.service.control.AuthenticationController;
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
    AuthenticationController authController;
    @Inject
    JsonWebToken jwt;

    @Override
    public Response authenticate(SecurityContext ctx, String body) {
        try {
            String email = authController.getTokenEmail(body);
            String tokenUserId = authController.getTokenSubject(body);

            // if user exists, log him in
            if(controller.checkIfUserExists(tokenUserId)){
                String accessToken = authController.generateAccessToken(body);
                String refreshToken = authController.generateRefreshToken(accessToken);

                JsonObject tokensJson = Json.createObjectBuilder()
                        .add("accessToken", accessToken)
                        .add("refreshToken", refreshToken)
                        .build();

                return Response.ok(tokensJson.toString(), MediaType.APPLICATION_JSON).build();
            }
            // else create user
            else {
                final UserModel userRequest =
                        ImmutableUserJson.builder().name("Nils Leo").email(email).tokenId(tokenUserId).build();

                CustomerModel user = controller.createUser(userRequest);

                return Response.status(Response.Status.CREATED)
                        .entity(user)
                        .build();
            }
        } catch (Exception e) {
            System.err.println("Authorization error: " + e.getMessage());

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Authorization error: " + e.getMessage())
                    .build();
        }
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