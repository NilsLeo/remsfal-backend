package de.remsfal.service.boundary;

import de.remsfal.core.BuildingEndpoint;
import de.remsfal.core.ProjectEndpoint;
import de.remsfal.core.PropertyEndpoint;
import de.remsfal.core.dto.BuildingJson;
import de.remsfal.service.control.AuthController;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path(ProjectEndpoint.CONTEXT + "/" + ProjectEndpoint.VERSION + "/"
        + ProjectEndpoint.SERVICE + "/{projectId}/" + PropertyEndpoint.SERVICE
        + "/{propertyId}/" + BuildingEndpoint.SERVICE)
public class BuildingResource implements BuildingEndpoint {
    @Inject
    AuthController authController;
    @Override
    public Response createBuilding(BuildingJson property) {
        return null;
    }

    @Override
    public Response getBuilding(String projectId, String propertyId, String buildingId) {
        return null;
    }
}
