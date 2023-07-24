package de.remsfal.service.control;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import de.remsfal.core.model.*;
import de.remsfal.service.entity.dao.RefreshTokenRepository;
import de.remsfal.service.entity.dao.UserRepository;
import de.remsfal.service.entity.dto.ProjectEntity;
import de.remsfal.service.entity.dto.RefreshTokenEntity;
import de.remsfal.service.entity.dto.UserEntity;
import io.smallrye.jwt.build.Jwt;
import io.quarkus.smallrye.jwt.runtime.auth.JwtAuthMechanism;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import java.time.Instant;
import java.util.*;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.security.interfaces.RSAPublicKey;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
@ApplicationScoped
public class AuthenticationController {

    @Inject
    JwtAuthMechanism jwtAuthMechanism;
    @Inject
    RefreshTokenRepository refreshTokenRepository;
    @Inject
    UserController userController;
    @Inject
    Logger logger;
    public String generateAccessToken(String token){

        DecodedJWT jwt = JWT.decode(token);
        String userId = jwt.getSubject();
        final CustomerModel user = userController.getUserByTokenId(userId);

        Set<String> roles = new HashSet<>(
                Arrays.asList("User")
        );
        long duration = System.currentTimeMillis() + 900;
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

    public String getTokenSubject(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }
    public boolean verifyRefreshToken(String refreshToken) {

    }

    public String getTokenEmail(String token) {
        DecodedJWT jwt = JWT.decode(token);
        Claim emailClaim = jwt.getClaim("email");
        String email = null;

        // Check if the claim is not null and then get its string value
        if(emailClaim != null){
            email = emailClaim.asString();
        }
        return email;
    }

    public String generateRefreshToken(String token){
        DecodedJWT jwt = JWT.decode(token);
        String userId = jwt.getSubject();
        System.out.println("reached2");

        final CustomerModel user = userController.getUserByTokenId(userId);

        String id = user.getTokenId();
        Instant now = Instant.now();
        Instant expiryInstant = now.plusMillis(900_000); // token expires in 15 mins
        String refreshToken = Jwt.issuer("remsfal-jwt")
                .subject(id)
                .issuedAt(now)
                .expiresAt(expiryInstant)
                .sign();

        createRefreshToken(refreshToken);
        return refreshToken;
    }
    public boolean checkIfRefreshTokenExists(final String refreshToken) {
        try {
            logger.infov("Checking if refreshToken exists ( refreshToken = {0})",  refreshToken);
            final RefreshTokenModel refreshTokenModel = refreshTokenRepository.refreshTokenByToken(refreshToken);
            return refreshTokenModel != null;
        } catch (Exception e) {
            logger.errorv("An error occurred while checking if refreshToken (refreshToken = {0}): {1}", refreshToken, e.getMessage());
            return false;
        }
    }
    public RefreshTokenModel getRefreshToken(final String refreshToken) {
        logger.infov("Retrieving a refreshToken (id = {0})", refreshToken);
        try {
            return refreshTokenRepository.refreshTokenByToken(refreshToken);
        } catch (final NoResultException e) {
            throw new NotFoundException("Project not exist or user has no membership", e);
        }
    }

    @Transactional
    public RefreshTokenModel createRefreshToken(final String refreshToken) {

        DecodedJWT jwt = JWT.decode(refreshToken);
        Instant expiryInstant = jwt.getExpiresAt().toInstant();

        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.generateId();
        System.out.println("refreshToken " + refreshToken);
        entity.setToken(refreshToken);
        refreshTokenRepository.persistAndFlush(entity);
        return entity;
    }


}