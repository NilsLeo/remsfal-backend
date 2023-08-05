package de.remsfal.service.control;

import de.remsfal.core.model.ApartmentModel;
import de.remsfal.core.model.GarageModel;
import de.remsfal.service.entity.dao.ApartmentRepository;
import de.remsfal.service.entity.dao.GarageRepository;
import de.remsfal.service.entity.dto.ApartmentEntity;
import de.remsfal.service.entity.dto.GarageEntity;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
public class GarageController {


    @Inject
    Logger logger;

    @Inject
    GarageRepository garageRepository;

    @Transactional
    public GarageModel createGarage(String buildingId, String title){
        logger.infov("Creating a garage (title={0}, buildingId={1})", title, buildingId);

        GarageEntity entity = new GarageEntity();
        entity.generateId();
        entity.setTitle(title);
        entity.setBuildingId(buildingId);
       garageRepository.persistAndFlush(entity);
        return entity;
    }

    public List<GarageEntity> getGarages(String buildingId) {
        List<GarageEntity> garageEntities = garageRepository.findGaragesByBuildingId(buildingId);
        return garageEntities;
    }
}
