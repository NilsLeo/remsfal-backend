package de.remsfal.service.boundary.authentication;

import de.remsfal.core.dto.ImmutableUserJson;
import de.remsfal.core.model.CustomerModel;
import de.remsfal.core.model.UserModel;
import io.smallrye.jwt.build.Jwt;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.util.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

@ApplicationScoped
public class TokenValidator {
    public TokenInfo validate(final String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        DecodedJWT jwt = JWT.decode(token);

        String issuer = jwt.getIssuer();

        // Check if the token is expired
        Date expiresAt = jwt.getExpiresAt();
        if (expiresAt.before(new Date())) {
            return null; // Token is expired
        }

        if(issuer.equals("remsfal-jwt") ){

            try {
                String email = jwt.getClaim("email").asString();
                String userId = jwt.getSubject(); // "sub" is generally the subject in a JWT
                String name = jwt.getClaim("name").asString();
                final UserModel user = ImmutableUserJson.builder()
                        .id(userId)
                        .tokenId(userId)
                        .email(email)
                        .name(name)
                        .build();

                return new TokenInfo(user);
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Invalid user id", e);
            }

        }
        else return null;

    }

}
