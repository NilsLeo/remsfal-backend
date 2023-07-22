package de.remsfal.service.control;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import de.remsfal.core.model.CustomerModel;
import io.smallrye.jwt.build.Jwt;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
@ApplicationScoped
public class AuthenticationController {

    @Inject
    UserController userController;

    public String generateJWT(String token){
        DecodedJWT jwt = JWT.decode(token);
        String userId = jwt.getSubject();
        final CustomerModel user = userController.getUserByTokenId(userId);

        Set<String> roles = new HashSet<>(
                Arrays.asList("User")
        );
        long duration = System.currentTimeMillis() + 3600;
        String email = user.getEmail();
        String name = null;
        if(user.getName() == null){
            name = "";
        }
        else {
            name= user.getName();
        }
        String id = user.getTokenId();
        return Jwt.issuer("remsfal-jwt")
                .subject(id)
                .groups(roles)
                .expiresAt(duration)
                .claim("name", name)  // Adding the name claim
                .claim("email", email)  // Adding the email claim
                .sign();

    }
}