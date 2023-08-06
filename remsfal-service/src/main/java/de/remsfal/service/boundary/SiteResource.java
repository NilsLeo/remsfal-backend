package de.remsfal.service.boundary;

import de.remsfal.core.ProjectEndpoint;
import de.remsfal.core.PropertyEndpoint;
import de.remsfal.core.SiteEndpoint;
import de.remsfal.core.dto.PropertyJson;
import de.remsfal.core.dto.SiteJson;
import de.remsfal.core.model.ProjectMemberModel;
import de.remsfal.core.model.PropertyModel;
import de.remsfal.core.model.SiteModel;
import de.remsfal.service.control.AuthController;
import de.remsfal.service.control.SiteController;
import de.remsfal.service.entity.dto.PropertyEntity;
import de.remsfal.service.entity.dto.SiteEntity;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path(ProjectEndpoint.CONTEXT + "/" + ProjectEndpoint.VERSION + "/"
        + ProjectEndpoint.SERVICE + "/{projectId}/" + PropertyEndpoint.SERVICE
        + "/{propertyId}/" + SiteEndpoint.SERVICE)
public class SiteResource implements SiteEndpoint {
    @Inject
    AuthController authController;
    @Inject
    SiteController siteController;
    @Context
    UriInfo uri;
    @Override
    public Response createSite(String projectId, String propertyId, SiteJson site) {
        boolean isAuthorized = authController.isOneOfGivenRolesInProject(projectId, new ProjectMemberModel.UserRole[]{
                ProjectMemberModel.UserRole.MANAGER,
                ProjectMemberModel.UserRole.PROPRIETOR,

        }, authController.getJwt());
        if (!isAuthorized) {
            return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to access this resource.").build();
        }
        final SiteModel model = siteController.createSite(propertyId, site.getTitle());
        final URI location = uri.getAbsolutePathBuilder().path(model.getId()).build();
        return Response.created(location)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    @Override
    public Response getSites(String projectId, String propertyId) {
        System.out.println("projectId" + projectId + "propertyId " + propertyId);
        boolean isAuthorized = authController.isOneOfGivenRolesInProject(projectId, new ProjectMemberModel.UserRole[]{
                ProjectMemberModel.UserRole.MANAGER,
                ProjectMemberModel.UserRole.PROPRIETOR,
                ProjectMemberModel.UserRole.CONSULTANT,
                ProjectMemberModel.UserRole.CARETAKER


        }, authController.getJwt());
        if (!isAuthorized) {
            return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to access this resource.").build();
        }
        List<SiteEntity> sites = siteController.getSites(propertyId);
        if(sites.isEmpty()) {
            throw new NotFoundException("No projects for this user fond");
        }
        return Response.ok(sites).build();
    }
}
