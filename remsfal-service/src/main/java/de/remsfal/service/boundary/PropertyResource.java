package de.remsfal.service.boundary;

import de.remsfal.core.ProjectEndpoint;
import de.remsfal.core.PropertyEndpoint;
import de.remsfal.core.dto.ProjectJson;
import de.remsfal.core.dto.ProjectListJson;
import de.remsfal.core.dto.PropertyJson;
import de.remsfal.core.model.ProjectMemberModel;
import de.remsfal.core.model.ProjectModel;
import de.remsfal.core.model.PropertyModel;
import de.remsfal.service.boundary.authentication.RemsfalPrincipal;
import de.remsfal.service.control.AuthController;
import de.remsfal.service.control.PropertyController;
import de.remsfal.service.entity.dto.PropertyEntity;
import org.hibernate.mapping.Property;

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
        + ProjectEndpoint.SERVICE + "/{projectId}/" + PropertyEndpoint.SERVICE)
public class PropertyResource implements PropertyEndpoint {
    @Inject
    AuthController authController;
    @Context
    UriInfo uri;

    @Inject
    PropertyController propertyController;
    @Inject
    RemsfalPrincipal principal;
    @Override
    public Response createProperty(String projectId, PropertyJson property) {
        boolean isAuthorized = authController.isOneOfGivenRolesInProject(projectId, new ProjectMemberModel.UserRole[]{
                ProjectMemberModel.UserRole.PROPRIETOR,
                ProjectMemberModel.UserRole.MANAGER,
                ProjectMemberModel.UserRole.LESSOR
        }, authController.getJwt());
        if(!isAuthorized) { return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to access this resource.").build(); }
        final PropertyModel model = propertyController.createProperty(projectId, principal, property.getTitle());
        final URI location = uri.getAbsolutePathBuilder().path(model.getId()).build();
        return Response.created(location)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @Override
    public Response getProperties(String projectId) {
        boolean isAuthorized = authController.isOneOfGivenRolesInProject(projectId, new ProjectMemberModel.UserRole[]{
                ProjectMemberModel.UserRole.PROPRIETOR,
                ProjectMemberModel.UserRole.MANAGER
        }, authController.getJwt());
        if(!isAuthorized) { return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to access this resource.").build(); }
        List<PropertyEntity> properties = propertyController.getProperties(projectId);
        if(properties.isEmpty()) {
            throw new NotFoundException("No projects for this user fond");
        }
        return Response.ok(properties).build();
    }


}
