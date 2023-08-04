package de.remsfal.service.boundary;

import de.remsfal.core.ApartmentEndpoint;
import de.remsfal.core.BuildingEndpoint;
import de.remsfal.core.ProjectEndpoint;
import de.remsfal.core.PropertyEndpoint;
import de.remsfal.core.dto.ApartmentJson;
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
    public Response createApartment(ApartmentJson apartment) {
        return null;
    }

    @Override
    public Response getApartment(String projectId, String propertyId, String buildingId, String apartmentId) {
        return null;
    }
}
