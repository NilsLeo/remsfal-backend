package de.remsfal.service.boundary;
import de.remsfal.service.boundary.authentication.RemsfalPrincipal;
import de.remsfal.service.boundary.authentication.RemsfalSecurityContext;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/secured")
@RequestScoped
public class TokenSecuredResource {
    @Inject
    RemsfalPrincipal principal;

    @Inject
    JsonWebToken jwt;
    @Inject
    @Claim(standard = Claims.birthdate)
    String birthdate;

    @GET
    @Path("permit-all")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@Context SecurityContext ctx) {
        System.out.println("everyonesresource");
        for (String claimName : jwt.getClaimNames()) {
            Object claimValue = jwt.getClaim(claimName);
            System.out.println(claimName + ": " + claimValue);
        }
        return "everyonesresource";
    }

    @GET
    @Path("roles-allowed")
    @RolesAllowed({ "User", "Admin" })
    @Produces(MediaType.TEXT_PLAIN)
    public String helloRolesAllowed(@Context SecurityContext ctx) {
        return "user,adminresource";
    }

    @GET
    @Path("roles-allowed-admin")
    @RolesAllowed("Admin")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloRolesAllowedAdmin(@Context SecurityContext ctx) {
        return "adminresource";
    }
    @GET
    @Path("roles-allowed-tester")
    @RolesAllowed("Tester")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloRolesAllowedTester(@Context SecurityContext ctx) {
        return "testerresource";
    }

    @GET
    @Path("deny-all")
    @DenyAll
    @Produces(MediaType.TEXT_PLAIN)
    public String helloShouldDeny(@Context SecurityContext ctx) {
        throw new InternalServerErrorException("This method must not be invoked");
    }

    private String getResponseString(SecurityContext ctx) {
        String name;
        if (ctx.getUserPrincipal() == null) {
            name = "anonymous";
        } else if (!ctx.getUserPrincipal().getName().equals(jwt.getName())) {
            System.out.println("principalName " + ctx.getUserPrincipal().getName() + " jwtName " + jwt.getName() + " jwt " + jwt + " remsfalprincipalname " + principal.getName());
            throw new InternalServerErrorException("Principal and JsonWebToken names do not match");
        } else {
            name = ctx.getUserPrincipal().getName();
        }
        return String.format("hello + %s,"
                + " isHttps: %s,"
                + " authScheme: %s,"
                + " hasJWT: %s",
                name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJwt());
    }

    private boolean hasJwt() {
        return jwt.getClaimNames() != null;
    }
}
