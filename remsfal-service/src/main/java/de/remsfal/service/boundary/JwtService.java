package de.remsfal.service.boundary;

import de.remsfal.core.dto.UserJson;
import de.remsfal.core.model.CustomerModel;
import io.smallrye.jwt.build.Jwt;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class JwtService {

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
}
