package de.remsfal.service.entity.dao;

import javax.enterprise.context.ApplicationScoped;

import de.remsfal.service.entity.dto.ApartmentEntity;
import de.remsfal.service.entity.dto.GarageEntity;

import java.util.List;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@ApplicationScoped
public class GarageRepository extends AbstractRepository<GarageEntity> {
    public List<GarageEntity> findGaragesByBuildingId(String buildingId) {
        return getEntityManager()
                .createNamedQuery("GarageEntity.findByBuildingId", GarageEntity.class)
                .setParameter(PARAM_BUILDING_ID, buildingId)
                .getResultList();
    }
}