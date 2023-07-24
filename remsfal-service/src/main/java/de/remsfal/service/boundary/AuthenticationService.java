package de.remsfal.service.boundary;

import de.remsfal.core.AuthenticationEndpoint;
import de.remsfal.core.dto.ImmutableUserJson;
import de.remsfal.core.model.CustomerModel;
import de.remsfal.core.model.RefreshTokenModel;
import de.remsfal.core.model.UserModel;
import de.remsfal.service.control.AuthenticationController;
import de.remsfal.service.control.UserController;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Singleton
public class AuthenticationService implements AuthenticationEndpoint {

    @Inject
    AuthenticationController authController;
    @Override
    public Response authenticate(@Context SecurityContext ctx,
                                 String body) {
        try {

            String accessToken = authController.generateAccessToken(body);
            String refreshToken = authController.generateRefreshToken(accessToken);
            // authController
            // Create a JSON object to hold the access token and refresh token
            JsonObject tokensJson = Json.createObjectBuilder()
                    .add("accessToken", accessToken)
                    .add("refreshToken", refreshToken)
                    .build();

            // Return the JSON object in the response
            return Response.ok(tokensJson.toString(), MediaType.APPLICATION_JSON).build();
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid user id", e);
        }
    }


    @Override
    public Response refresh(@Context SecurityContext ctx, String body) {
        try {

        if(authController.checkIfRefreshTokenExists(body)){
            validate, make sure its not too old
            jwt.verify(body);
        }
        JsonObject json = Json.createObjectBuilder()
                .add("message", "This is a dummy JSON")
                .add("code", 200)
                .build();

        return Response.ok(json).build();
        } catch (Exception e) {
            System.err.println("Authorization error: " + e.getMessage());

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Authorization error: " + e.getMessage())
                    .build();
        }

    }


//    public Response authenticate(SecurityContext ctx, String body) {
//        try {
//            String email = authController.getTokenEmail(body);
//            String tokenUserId = authController.getTokenSubject(body);
//
//            // if user exists, log him in
//            if(controller.checkIfUserExists(tokenUserId)){
//                String accessToken = authController.generateAccessToken(body);
//                String refreshToken = authController.generateRefreshToken(accessToken);
//
//                JsonObject tokensJson = Json.createObjectBuilder()
//                        .add("accessToken", accessToken)
//                        .add("refreshToken", refreshToken)
//                        .build();
//
//                return Response.ok(tokensJson.toString(), MediaType.APPLICATION_JSON).build();
//            }
//            // else create user
//            else {
//                final UserModel userRequest =
//                        ImmutableUserJson.builder().name("Nils Leo").email(email).tokenId(tokenUserId).build();
//
//                CustomerModel user = controller.createUser(userRequest);
//
//                return Response.status(Response.Status.CREATED)
//                        .entity(user)
//                        .build();
//            }
//        } catch (Exception e) {
//            System.err.println("Authorization error: " + e.getMessage());
//
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity("Authorization error: " + e.getMessage())
//                    .build();
//        }
//    }
    @Override
    public String getRefreshToken(final String refreshToken) {
        try {
            final RefreshTokenModel refreshTokenModel = authController.getRefreshToken(refreshToken);
            return "accessToken";
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid refreshToken", e);
        }
    }

}
