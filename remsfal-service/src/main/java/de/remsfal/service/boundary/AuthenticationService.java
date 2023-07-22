package de.remsfal.service.boundary;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import de.remsfal.core.dto.UserJson;
import de.remsfal.core.model.CustomerModel;
import de.remsfal.service.control.UserController;
import io.smallrye.jwt.build.Jwt;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
@Path("/authenticate")
@Singleton
public class JwtService {

    @Inject
    UserController controller;
    public String generateJWT(CustomerModel userjson){
        Set<String> roles = new HashSet<>(
                Arrays.asList("User")
        );
        long duration = System.currentTimeMillis() + 3600;
        String email = userjson.getEmail();
        String name = "Nils Leo";
        String id = userjson.getId();
        return Jwt.issuer("remsfal-jwt")
                .subject(id)
                .groups(roles)
                .expiresAt(duration)
                .claim("name", name)  // Adding the email claim
                .claim("email", email)  // Adding the email claim
                .sign();

    }
    @GET
    @Path("")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response authenticate(@Context SecurityContext ctx,
                                 @HeaderParam("Authorization") String authHeader,
                                 String body) {
        String bearerToken = body;
        DecodedJWT jwt = JWT.decode(bearerToken);
        String issuer = String.valueOf(jwt.getClaim("iss"));
        System.out.println("issuer" + issuer);

            try {
                String userId = jwt.getSubject();
                final CustomerModel user = controller.getUserByTokenId(userId);
                System.out.println("email" + user.getEmail());
                String remsfalJwt = generateJWT(user);
                return Response.ok(remsfalJwt).build();
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Invalid user id", e);
            }
    }

}
