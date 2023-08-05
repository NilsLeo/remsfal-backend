package de.remsfal.service.boundary;

import de.remsfal.core.BuildingEndpoint;
import de.remsfal.core.ProjectEndpoint;
import de.remsfal.core.PropertyEndpoint;
import de.remsfal.core.dto.BuildingJson;
import de.remsfal.core.dto.SiteJson;
import de.remsfal.core.model.BuildingModel;
import de.remsfal.core.model.ProjectMemberModel;
import de.remsfal.core.model.SiteModel;
import de.remsfal.service.control.AuthController;
import de.remsfal.service.control.BuildingController;
import de.remsfal.service.entity.dto.BuildingEntity;
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
        + "/{propertyId}/" + BuildingEndpoint.SERVICE)
public class BuildingResource implements BuildingEndpoint {
    @Inject
    AuthController authController;
    @Context
    UriInfo uri;

    @Inject
    BuildingController buildingController;
    @Override
    public Response createBuilding(String projectId, String propertyId, BuildingJson building) {
        boolean isAuthorized = authController.isOneOfGivenRolesInProject(projectId, new ProjectMemberModel.UserRole[]{
                ProjectMemberModel.UserRole.PROPRIETOR,
                ProjectMemberModel.UserRole.MANAGER,
                ProjectMemberModel.UserRole.LESSOR,
        }, authController.getJwt());
        if(!isAuthorized) { return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to access this resource.").build(); }
        final BuildingModel model = buildingController.createBuilding(propertyId, building.getTitle());
        final URI location = uri.getAbsolutePathBuilder().path(model.getId()).build();
        return Response.created(location)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @Override
    public Response getBuildings(String projectId, String propertyId) {
        System.out.println("propertyIdB" + propertyId);

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
        List<BuildingEntity> sites = buildingController.getBuildings(propertyId);
        if(sites.isEmpty()) {
            throw new NotFoundException("No projects for this user fond");
        }
        return Response.ok(sites).build();
    }
}
