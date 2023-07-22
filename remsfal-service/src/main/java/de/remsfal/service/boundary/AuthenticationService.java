package de.remsfal.service.boundary;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import de.remsfal.core.AuthenticationEndpoint;
import de.remsfal.service.control.AuthenticationController;
import javax.inject.Inject;
import javax.inject.Singleton;
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
                                 @HeaderParam("Authorization") String authHeader,
                                 String body) {
        System.out.println("body " + body);
        try {
            String remsfalJwt = authController.generateJWT(body);
            return Response.ok(remsfalJwt).build();
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid user id", e);
        }
    }

}