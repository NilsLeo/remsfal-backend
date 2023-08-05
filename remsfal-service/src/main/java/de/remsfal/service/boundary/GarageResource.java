package de.remsfal.service.boundary;

import de.remsfal.core.BuildingEndpoint;
import de.remsfal.core.GarageEndpoint;
import de.remsfal.core.ProjectEndpoint;
import de.remsfal.core.PropertyEndpoint;
import de.remsfal.core.dto.ApartmentJson;
import de.remsfal.core.dto.GarageJson;
import de.remsfal.core.model.ApartmentModel;
import de.remsfal.core.model.GarageModel;
import de.remsfal.core.model.ProjectMemberModel;
import de.remsfal.service.control.AuthController;
import de.remsfal.service.control.GarageController;
import de.remsfal.service.entity.dto.ApartmentEntity;
import de.remsfal.service.entity.dto.GarageEntity;

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
        + "/{propertyId}/" + BuildingEndpoint.SERVICE
        + "/{buildingId}/" + GarageEndpoint.SERVICE)
public class GarageResource implements GarageEndpoint {
    @Inject
    AuthController authController;
    @Inject
    GarageController garageController;
    @Context
    UriInfo uri;

    @Override
    public Response createGarage(String projectId, String propertyId, String buildingId, GarageJson garage) {
        boolean isAuthorized = authController.isOneOfGivenRolesInProject(projectId, new ProjectMemberModel.UserRole[]{
                ProjectMemberModel.UserRole.PROPRIETOR,
                ProjectMemberModel.UserRole.MANAGER,
                ProjectMemberModel.UserRole.LESSOR
        }, authController.getJwt());
        if (!isAuthorized) {
            return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to access this resource.").build();
        }
        final GarageModel model = garageController.createGarage(buildingId, garage.getTitle());
        final URI location = uri.getAbsolutePathBuilder().path(model.getId()).build();
        return Response.created(location)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @Override
    public Response getGarages(String projectId, String buildingId) {
        System.out.println("projectId" + projectId + "propertyId " + buildingId);
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
        List<GarageEntity> apartments = garageController.getGarages(buildingId);
        if(apartments.isEmpty()) {
            throw new NotFoundException("No projects for this user fond");
        }
        return Response.ok(apartments).build();
    }
}
