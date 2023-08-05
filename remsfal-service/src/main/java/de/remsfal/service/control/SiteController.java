package de.remsfal.service.control;

import de.remsfal.core.model.PropertyModel;
import de.remsfal.core.model.SiteModel;
import de.remsfal.core.model.UserModel;
import de.remsfal.service.entity.dao.SiteRepository;
import de.remsfal.service.entity.dto.PropertyEntity;
import de.remsfal.service.entity.dto.SiteEntity;
import de.remsfal.service.entity.dto.UserEntity;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
@RequestScoped
public class SiteController {
    @Inject
    Logger logger;

    @Inject
    SiteRepository siteRepository;

    @Transactional
    public SiteModel createSite(String propertyId, String title){
        logger.infov("Creating a site (title={0}, propertyId={1})", title, propertyId);

        SiteEntity entity = new SiteEntity();
        entity.generateId();
        entity.setTitle(title);
        entity.setPropertyId(propertyId);
        siteRepository.persistAndFlush(entity);
        return entity;
    }

    public List<SiteEntity> getSites(String propertyId) {
        List<SiteEntity> siteEntities = siteRepository.findSitesByPropertyId(propertyId);
        return siteEntities;
    }
}
