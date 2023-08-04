package de.remsfal.service.boundary;

import de.remsfal.core.BuildingEndpoint;
import de.remsfal.core.GarageEndpoint;
import de.remsfal.core.ProjectEndpoint;
import de.remsfal.core.PropertyEndpoint;
import de.remsfal.core.dto.GarageJson;
import de.remsfal.service.control.AuthController;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path(ProjectEndpoint.CONTEXT + "/" + ProjectEndpoint.VERSION + "/"
        + ProjectEndpoint.SERVICE + "/{projectId}/" + PropertyEndpoint.SERVICE
        + "/{propertyId}/" + BuildingEndpoint.SERVICE
        + "/{buildingId}/" + GarageEndpoint.SERVICE)
public class GarageResource implements GarageEndpoint {
    @Inject
    AuthController authController;
    @Override
    public Response createGarage(GarageJson garage) {
        return null;
    }

    @Override
    public Response getGarage(String projectId, String propertyId, String buildingId, String garageId) {
        return null;
    }
}
