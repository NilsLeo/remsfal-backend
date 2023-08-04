package de.remsfal.service.boundary;

import de.remsfal.core.ApartmentEndpoint;
import de.remsfal.core.BuildingEndpoint;
import de.remsfal.core.ProjectEndpoint;
import de.remsfal.core.PropertyEndpoint;
import de.remsfal.core.dto.ApartmentJson;
import de.remsfal.core.model.ProjectMemberModel;
import de.remsfal.service.control.AuthController;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path(ProjectEndpoint.CONTEXT + "/" + ProjectEndpoint.VERSION + "/"
        + ProjectEndpoint.SERVICE + "/{projectId}/" + PropertyEndpoint.SERVICE
        + "/{propertyId}/" + BuildingEndpoint.SERVICE
        + "/{buildingId}/" + ApartmentEndpoint.SERVICE)
public class ApartmentResource implements ApartmentEndpoint {
    @Inject
    AuthController authController;


    @Override
    public Response createApartment(String projectId, ApartmentJson apartment) {
        boolean isAuthorized = authController.isOneOfGivenRolesInProject(projectId, new ProjectMemberModel.UserRole[]{ProjectMemberModel.UserRole.PROPRIETOR,ProjectMemberModel.UserRole.MANAGER, ProjectMemberModel.UserRole.LESSOR}, authController.getJwt());
        if(!isAuthorized) { return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to access this resource.").build(); }
        return null;
    }
    @Override
    public Response getApartment(String projectId, String propertyId, String buildingId, String apartmentId) {
        boolean isAuthorized = authController.isOneOfGivenRolesInProject(projectId, new ProjectMemberModel.UserRole[]{
                ProjectMemberModel.UserRole.PROPRIETOR,
                ProjectMemberModel.UserRole.MANAGER,
                ProjectMemberModel.UserRole.LESSOR,
                ProjectMemberModel.UserRole.LESSEE,
                ProjectMemberModel.UserRole.CONSULTANT
        }, authController.getJwt());
        if(!isAuthorized) { return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to access this resource.").build(); }
        return null;
    }
}
