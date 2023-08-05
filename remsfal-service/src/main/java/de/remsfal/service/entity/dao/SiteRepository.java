package de.remsfal.service.entity.dao;

import javax.enterprise.context.ApplicationScoped;

import de.remsfal.service.entity.dto.PropertyEntity;
import de.remsfal.service.entity.dto.SiteEntity;

import java.util.List;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@ApplicationScoped
public class SiteRepository extends AbstractRepository<SiteEntity> {

    public List<SiteEntity> findSitesByPropertyId(String propertyId) {
        System.out.println("propertyId" + propertyId);

        return getEntityManager()
                .createNamedQuery("SiteEntity.findByPropertyId", SiteEntity.class)
                .setParameter(PARAM_PROPERTY_ID, propertyId)
                .getResultList();
    }

}