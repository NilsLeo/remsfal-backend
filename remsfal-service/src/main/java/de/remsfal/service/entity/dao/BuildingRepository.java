package de.remsfal.service.entity.dao;

import javax.enterprise.context.ApplicationScoped;

import de.remsfal.service.entity.dto.BuildingEntity;
import de.remsfal.service.entity.dto.SiteEntity;

import java.util.List;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@ApplicationScoped
public class BuildingRepository extends AbstractRepository<BuildingEntity> {
    public List<BuildingEntity> findBuildingsByPropertyId(String propertyId) {
        System.out.println("propertyId" + propertyId);

        return getEntityManager()
                .createNamedQuery("BuildingEntity.findByPropertyId", BuildingEntity.class)
                .setParameter(PARAM_PROPERTY_ID, propertyId)
                .getResultList();
    }
}