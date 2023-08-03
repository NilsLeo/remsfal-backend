package de.remsfal.service.boundary;

import de.remsfal.core.ProjectEndpoint;
import de.remsfal.core.PropertyEndpoint;
import de.remsfal.core.dto.PropertyJson;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path(ProjectEndpoint.CONTEXT + "/" + ProjectEndpoint.VERSION + "/"
        + ProjectEndpoint.SERVICE + "/{projectId}/" + PropertyEndpoint.SERVICE)
public class PropertyResource implements PropertyEndpoint {
    @Override
    public Response createProperty(PropertyJson property) {
        return null;
    }

    @Override
    public PropertyJson getProperty(String projectId, String propertyId) {
        return null;
    }
}
