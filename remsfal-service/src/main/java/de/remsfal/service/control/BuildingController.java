package de.remsfal.service.control;

import de.remsfal.core.model.BuildingModel;
import de.remsfal.core.model.SiteModel;
import de.remsfal.service.entity.dao.BuildingRepository;
import de.remsfal.service.entity.dao.SiteRepository;
import de.remsfal.service.entity.dto.BuildingEntity;
import de.remsfal.service.entity.dto.SiteEntity;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
@RequestScoped

public class BuildingController {

    @Inject
    Logger logger;

    @Inject
    BuildingRepository buildingRepository;

    @Transactional
    public BuildingModel createBuilding(String propertyId, String title){
        logger.infov("Creating a site (title={0}, propertyId={1})", title, propertyId);

        BuildingEntity entity = new BuildingEntity();
        entity.generateId();
        entity.setTitle(title);
        entity.setPropertyId(propertyId);
        buildingRepository.persistAndFlush(entity);
        return entity;
    }

    public List<BuildingEntity> getBuildings(String propertyId) {
        List<BuildingEntity> buildingEntities = buildingRepository.findBuildingsByPropertyId(propertyId);
        return buildingEntities;
    }
}
