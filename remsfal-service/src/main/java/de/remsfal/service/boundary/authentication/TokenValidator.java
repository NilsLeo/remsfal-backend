package de.remsfal.service.boundary.authentication;
import de.remsfal.core.dto.ImmutableUserJson;
import de.remsfal.core.model.UserModel;
import io.smallrye.jwt.build.Jwt;
import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

@ApplicationScoped
public class TokenValidator {
    public TokenInfo validate(final String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        DecodedJWT jwt = JWT.decode(token);
        String email = jwt.getClaim("email").asString();
        String userId = jwt.getSubject(); // "sub" is generally the subject in a JWT
        String name = jwt.getClaim("name").asString();

        final UserModel user = ImmutableUserJson.builder()
                .id(userId)
                .email(email)
                .name(name)
                .build();

        return new TokenInfo(user);
    }


}
