package de.remsfal.service.boundary;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

@Path("api" + "/" + "v1" + "/" + "auth/login")
public class AuthenticationResource {
    @Inject
    JwtService service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getJwt(){
        String jwt = service.generateJWT();
        return Response.ok(jwt).build();
    }
}
