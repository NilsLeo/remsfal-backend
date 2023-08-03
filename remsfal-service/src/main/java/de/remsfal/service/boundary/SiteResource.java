package de.remsfal.service.boundary;

import de.remsfal.core.ProjectEndpoint;
import de.remsfal.core.PropertyEndpoint;
import de.remsfal.core.SiteEndpoint;
import de.remsfal.core.dto.SiteJson;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path(ProjectEndpoint.CONTEXT + "/" + ProjectEndpoint.VERSION + "/"
        + ProjectEndpoint.SERVICE + "/{projectId}/" + PropertyEndpoint.SERVICE
        + "/{propertyId}/" + SiteEndpoint.SERVICE)
public class SiteResource implements SiteEndpoint {
    @Override
    public Response createSite(SiteJson site) {
        return null;
    }

    @Override
    public SiteJson getSite(String projectId, String propertyId, String siteId) {
        return null;
    }
}
