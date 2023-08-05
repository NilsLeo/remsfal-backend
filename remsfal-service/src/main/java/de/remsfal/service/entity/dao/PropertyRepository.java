package de.remsfal.service.entity.dao;

import javax.enterprise.context.ApplicationScoped;

import de.remsfal.core.model.PropertyModel;
import de.remsfal.service.entity.dto.ProjectEntity;
import de.remsfal.service.entity.dto.ProjectMembershipEntity;
import de.remsfal.service.entity.dto.PropertyEntity;

import java.util.List;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@ApplicationScoped
public class PropertyRepository extends AbstractRepository<PropertyEntity> {

    public List<PropertyEntity> findPropertiesByProjectId(String projectId) {
        return getEntityManager()
                .createNamedQuery("PropertyEntity.findByProjectId", PropertyEntity.class)
                .setParameter(PARAM_PROJECT_ID, projectId)
                .getResultList();
    }
    }