package de.remsfal.service.boundary;

import de.remsfal.core.ProjectEndpoint;
import de.remsfal.core.PropertyEndpoint;
import de.remsfal.core.dto.PropertyJson;
import de.remsfal.service.control.AuthController;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path(ProjectEndpoint.CONTEXT + "/" + ProjectEndpoint.VERSION + "/"
        + ProjectEndpoint.SERVICE + "/{projectId}/" + PropertyEndpoint.SERVICE)
public class PropertyResource implements PropertyEndpoint {
    @Inject
    AuthController authController;
    @Override
    public Response createProperty(PropertyJson property) {
        return null;
    }

    @Override
    public Response getProperty(String projectId, String propertyId) {
        return null;
    }
}
