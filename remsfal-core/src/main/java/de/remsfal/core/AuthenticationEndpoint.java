package de.remsfal.core;

import de.remsfal.core.model.CustomerModel;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;


@Path(AuthenticationEndpoint.CONTEXT + "/" +AuthenticationEndpoint.VERSION + "/" + AuthenticationEndpoint.SERVICE)

public interface AuthenticationEndpoint {

    final static String CONTEXT = "api";
    final static String VERSION = "v1";
    final static String SERVICE = "authenticate";
    @POST
    @Path("")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    Response authenticate(@Context SecurityContext ctx,
                          String body);
    @POST
    @Path("/token")
    Response refresh(@Context SecurityContext ctx,
                     String body);

    String getRefreshToken(String refreshToken);

}
