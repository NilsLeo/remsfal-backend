package de.remsfal.service.entity.dao;

import javax.enterprise.context.ApplicationScoped;

import de.remsfal.service.entity.dto.ApartmentEntity;
import de.remsfal.service.entity.dto.BuildingEntity;

import java.util.List;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@ApplicationScoped
public class ApartmentRepository extends AbstractRepository<ApartmentEntity> {
    public List<ApartmentEntity> findApartmentsByBuildingId(String buildingId) {
        return getEntityManager()
                .createNamedQuery("ApartmentEntity.findByBuildingId", ApartmentEntity.class)
                .setParameter(PARAM_BUILDING_ID, buildingId)
                .getResultList();
    }
}