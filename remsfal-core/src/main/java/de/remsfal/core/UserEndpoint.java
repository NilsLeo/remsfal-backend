package de.remsfal.core;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import de.remsfal.core.dto.UserJson;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@Path(UserEndpoint.CONTEXT + "/" + UserEndpoint.VERSION + "/" + UserEndpoint.SERVICE)
public interface UserEndpoint {

    final static String CONTEXT = "api";
    final static String VERSION = "v1";
    final static String SERVICE = "users";

    @POST
    @Path("")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Create a new user.")
    @APIResponse(responseCode = "201", description = "User created successfully",
        headers = @Header(name = "Location", description = "URL to retrive all orders"))
    @APIResponse(responseCode = "400", description = "Invalid request message")
    @APIResponse(responseCode = "409", description = "Another user with the same email already exist")
    Response authenticate(@Context SecurityContext ctx,
                        String body);

    @GET
    @Path("roles-allowed-tester")
    @RolesAllowed("TestRole")
    @Produces(MediaType.TEXT_PLAIN)
    String restrictedToTester(@Context SecurityContext ctx);

    @GET
    @Path("permit-all")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    String accessibleToEveryone(@Context SecurityContext ctx);

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Retrieve information of a user.")
    @APIResponse(responseCode = "404", description = "The user does not exist")
    UserJson getUser(
        @Parameter(description = "ID of the user", required = true) @PathParam("userId") String userId);
    @PATCH
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update information of a user.")
    @APIResponse(responseCode = "404", description = "The user does not exist")
    UserJson updateUser(
        @Parameter(description = "ID of the user", required = true) @PathParam("userId") String userId,
        @Parameter(description = "User information", required = true) @Valid UserJson user);

    @DELETE
    @Path("/{userId}")
    @Operation(summary = "Delete an existing user.")
    @APIResponse(responseCode = "204", description = "The user was deleted successfully")
    @APIResponse(responseCode = "404", description = "The user does not exist")
    void deleteUser(
        @Parameter(description = "ID of the user", required = true) @PathParam("userId") String userId);

}
