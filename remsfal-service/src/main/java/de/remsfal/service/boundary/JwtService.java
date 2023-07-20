package de.remsfal.service.boundary;

import io.smallrye.jwt.build.Jwt;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class JwtService {

    public String generateJWT(){
        Set<String> roles = new HashSet<>(
                Arrays.asList("User")
        );
        long duration = System.currentTimeMillis() + 3600;

        return Jwt.issuer("remsfal-jwt")
                .subject("113871335663645591213")
                .groups(roles)
                .expiresAt(duration)
                .claim("name", "Nils Leo")  // Adding the email claim
                .claim("email", "nilstleo01@gmail.com")  // Adding the email claim
                .sign();

    }
}
