package de.remsfal.service.boundary;

import de.remsfal.core.ProjectEndpoint;
import de.remsfal.core.PropertyEndpoint;
import de.remsfal.core.SiteEndpoint;
import de.remsfal.core.dto.SiteJson;
import de.remsfal.core.model.ProjectMemberModel;
import de.remsfal.service.control.AuthController;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path(ProjectEndpoint.CONTEXT + "/" + ProjectEndpoint.VERSION + "/"
        + ProjectEndpoint.SERVICE + "/{projectId}/" + PropertyEndpoint.SERVICE
        + "/{propertyId}/" + SiteEndpoint.SERVICE)
public class SiteResource implements SiteEndpoint {
    @Inject
    AuthController authController;

    @Override
    public Response createSite(String projectId, String propertyId, SiteJson site) {
        boolean isAuthorized = authController.isOneOfGivenRolesInProject(projectId, new ProjectMemberModel.UserRole[]{
                ProjectMemberModel.UserRole.PROPRIETOR,
                ProjectMemberModel.UserRole.MANAGER,
                ProjectMemberModel.UserRole.LESSOR
        }, authController.getJwt());
        if (!isAuthorized) {
            return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to access this resource.").build();
        }
        return null;
    }

    @Override
    public Response getSite(String projectId, String propertyId, String siteId) {
        boolean isAuthorized = authController.isOneOfGivenRolesInProject(projectId, new ProjectMemberModel.UserRole[]{
                ProjectMemberModel.UserRole.PROPRIETOR,
                ProjectMemberModel.UserRole.MANAGER,
                ProjectMemberModel.UserRole.LESSOR,
                ProjectMemberModel.UserRole.LESSEE,
                ProjectMemberModel.UserRole.CONSULTANT
        }, authController.getJwt());
        if (!isAuthorized) {
            return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to access this resource.").build();
        }
        {
            return null;
        }
    }
}
