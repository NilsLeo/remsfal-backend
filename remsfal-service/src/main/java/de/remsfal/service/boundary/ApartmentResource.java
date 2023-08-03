package de.remsfal.service.boundary;

import de.remsfal.core.ApartmentEndpoint;
import de.remsfal.core.BuildingEndpoint;
import de.remsfal.core.ProjectEndpoint;
import de.remsfal.core.PropertyEndpoint;
import de.remsfal.core.dto.ApartmentJson;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path(ProjectEndpoint.CONTEXT + "/" + ProjectEndpoint.VERSION + "/"
        + ProjectEndpoint.SERVICE + "/{projectId}/" + PropertyEndpoint.SERVICE
        + "/{propertyId}/" + BuildingEndpoint.SERVICE
        + "/{buildingId}/" + ApartmentEndpoint.SERVICE)
public class ApartmentResource implements ApartmentEndpoint {
    @Override
    public Response createApartment(ApartmentJson apartment) {
        return null;
    }

    @Override
    public ApartmentJson getApartment(String projectId, String propertyId, String buildingId, String apartmentId) {
        return null;
    }
}
