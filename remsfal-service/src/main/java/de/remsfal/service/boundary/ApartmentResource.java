package de.remsfal.service.boundary;

import de.remsfal.core.ApartmentEndpoint;
import de.remsfal.core.BuildingEndpoint;
import de.remsfal.core.ProjectEndpoint;
import de.remsfal.core.PropertyEndpoint;
import de.remsfal.core.dto.ApartmentJson;
import de.remsfal.core.dto.SiteJson;
import de.remsfal.core.model.ApartmentModel;
import de.remsfal.core.model.BuildingModel;
import de.remsfal.core.model.ProjectMemberModel;
import de.remsfal.core.model.SiteModel;
import de.remsfal.service.control.ApartmentController;
import de.remsfal.service.control.AuthController;
import de.remsfal.service.control.BuildingController;
import de.remsfal.service.entity.dto.ApartmentEntity;
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
        + "/{propertyId}/" + BuildingEndpoint.SERVICE
        + "/{buildingId}/" + ApartmentEndpoint.SERVICE)
public class ApartmentResource implements ApartmentEndpoint {
    @Inject
    AuthController authController;

    @Inject
    ApartmentController apartmentController;
    @Context
    UriInfo uri;
    @Override
    public Response createApartment(String projectId, String buildingId, ApartmentJson apartment) {
        boolean isAuthorized = authController.isOneOfGivenRolesInProject(projectId, new ProjectMemberModel.UserRole[]{
                ProjectMemberModel.UserRole.PROPRIETOR,
                ProjectMemberModel.UserRole.MANAGER,
                ProjectMemberModel.UserRole.LESSOR
        }, authController.getJwt());
        if (!isAuthorized) {
            return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to access this resource.").build();
        }
        final ApartmentModel model = apartmentController.createApartment(buildingId, apartment.getTitle());
        final URI location = uri.getAbsolutePathBuilder().path(model.getId()).build();
        return Response.created(location)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    @Override
    public Response getApartments(String projectId, String buildingId) {
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
        List<ApartmentEntity> apartments = apartmentController.getApartments(buildingId);
        if(apartments.isEmpty()) {
            throw new NotFoundException("No projects for this user fond");
        }
        return Response.ok(apartments).build();
    }
}
