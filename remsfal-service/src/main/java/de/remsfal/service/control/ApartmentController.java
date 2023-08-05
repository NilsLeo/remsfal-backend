package de.remsfal.service.control;

import de.remsfal.core.model.ApartmentModel;
import de.remsfal.core.model.BuildingModel;
import de.remsfal.service.entity.dao.ApartmentRepository;
import de.remsfal.service.entity.dao.BuildingRepository;
import de.remsfal.service.entity.dto.ApartmentEntity;
import de.remsfal.service.entity.dto.BuildingEntity;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
@RequestScoped
public class ApartmentController {

    @Inject
    Logger logger;

    @Inject
    ApartmentRepository apartmentRepository;

    @Transactional
    public ApartmentModel createApartment(String buildingId, String title){
        logger.infov("Creating a apartment (title={0}, buildingId={1})", title, buildingId);

        ApartmentEntity entity = new ApartmentEntity();
        entity.generateId();
        entity.setTitle(title);
        entity.setBuildingId(buildingId);
        apartmentRepository.persistAndFlush(entity);
        return entity;
    }

    public List<ApartmentEntity> getApartments(String buildingId) {
        List<ApartmentEntity> apartmentEntities = apartmentRepository.findApartmentsByBuildingId(buildingId);
        return apartmentEntities;
    }
}
