package de.remsfal.service.boundary;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("api" + "/" + "v1" + "/" + "test")

public class JwtResource {
    @Inject
    JwtService service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getJwt(){
        String jwt = service.generateJWT();
        return Response.ok(jwt).build();
    }
}
