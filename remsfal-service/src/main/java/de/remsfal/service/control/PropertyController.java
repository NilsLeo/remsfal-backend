package de.remsfal.service.control;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import de.remsfal.core.model.*;
import de.remsfal.service.entity.dao.*;
import de.remsfal.service.entity.dto.PropertyEntity;
import de.remsfal.service.entity.dto.UserEntity;
import org.jboss.logging.Logger;

import java.util.List;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@RequestScoped
public class PropertyController {
    
    @Inject
    Logger logger;
    
    @Inject
    PropertyRepository propertyRepository;

    @Inject
    SiteRepository siteRepository;
    
    @Inject
    BuildingRepository buildingRepository;
    @Inject
    UserRepository userRepository;

    @Inject
    ApartmentRepository apartmentRepository;
    
    @Inject
    GarageRepository garageRepository;

    @Transactional
    public PropertyModel createProperty(String projectId, final UserModel user, String title) {
        logger.infov("Creating a property (title={0}, email={1}, projectId={2})", title, user.getEmail(), projectId);
        UserEntity userEntity = userRepository.findById(user.getId());

        PropertyEntity entity = new PropertyEntity();
        entity.generateId();
        entity.setTitle(title);
        entity.setProjectId(projectId);
        propertyRepository.persistAndFlush(entity);
        return entity;
    }
    public List<PropertyEntity> getProperties(String projectId) {
        List<PropertyEntity> propertyEntities = propertyRepository.findPropertiesByProjectId(projectId);
        return propertyEntities;
    }

    @Transactional
    public SiteModel createSite(final UserModel user, final SiteModel site) {
        return null;
    }

    public SiteModel getSite(final UserModel user, final String siteId) {
        return null;
    }


    @Transactional
    public BuildingModel createBuilding(final UserModel user, final BuildingModel building) {
        return null;
    }

    public BuildingModel getBuilding(final UserModel user, final String buildingId) {
        return null;
    }


    @Transactional
    public ApartmentModel createApartment(final UserModel user, final ApartmentModel apartment) {
        return null;
    }

    public ApartmentModel getApartment(final UserModel user, final String apartmentId) {
        return null;
    }


    @Transactional
    public GarageModel createGarage(final UserModel user, final GarageModel garage) {
        return null;
    }

    public GarageModel getGarage(final UserModel user, final String garageId) {
        return null;
    }

}
