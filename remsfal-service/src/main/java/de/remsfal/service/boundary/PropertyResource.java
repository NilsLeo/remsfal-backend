package de.remsfal.service.boundary;

import de.remsfal.core.ProjectEndpoint;
import de.remsfal.core.PropertyEndpoint;
import de.remsfal.core.dto.PropertyJson;
import de.remsfal.core.model.ProjectMemberModel;
import de.remsfal.service.control.AuthController;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path(ProjectEndpoint.CONTEXT + "/" + ProjectEndpoint.VERSION + "/"
        + ProjectEndpoint.SERVICE + "/{projectId}/" + PropertyEndpoint.SERVICE)
public class PropertyResource implements PropertyEndpoint {
    @Inject
    AuthController authController;


    @Override
    public Response createProperty(String projectId, PropertyJson property) {
        boolean isAuthorized = authController.isOneOfGivenRolesInProject(projectId, new ProjectMemberModel.UserRole[]{
                ProjectMemberModel.UserRole.PROPRIETOR,
                ProjectMemberModel.UserRole.MANAGER,
                ProjectMemberModel.UserRole.LESSOR
        }, authController.getJwt());
        if(!isAuthorized) { return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to access this resource.").build(); }
        return null;
    }

    @Override
    public Response getProperty(String projectId, String propertyId) {
        boolean isAuthorized = authController.isOneOfGivenRolesInProject(projectId, new ProjectMemberModel.UserRole[]{
                ProjectMemberModel.UserRole.PROPRIETOR,
                ProjectMemberModel.UserRole.MANAGER
        }, authController.getJwt());
        if(!isAuthorized) { return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to access this resource.").build(); }
        return null;
    }
}
