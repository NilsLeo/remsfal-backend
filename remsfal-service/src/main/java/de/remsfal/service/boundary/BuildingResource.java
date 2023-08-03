package de.remsfal.service.boundary;

import de.remsfal.core.BuildingEndpoint;
import de.remsfal.core.ProjectEndpoint;
import de.remsfal.core.PropertyEndpoint;
import de.remsfal.core.dto.BuildingJson;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path(ProjectEndpoint.CONTEXT + "/" + ProjectEndpoint.VERSION + "/"
        + ProjectEndpoint.SERVICE + "/{projectId}/" + PropertyEndpoint.SERVICE
        + "/{propertyId}/" + BuildingEndpoint.SERVICE)
public class BuildingResource implements BuildingEndpoint {
    @Override
    public Response createBuilding(BuildingJson property) {
        return null;
    }

    @Override
    public BuildingJson getBuilding(String projectId, String propertyId, String buildingId) {
        return null;
    }
}
