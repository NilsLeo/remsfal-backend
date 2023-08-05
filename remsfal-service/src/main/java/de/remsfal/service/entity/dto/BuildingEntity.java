package de.remsfal.service.entity.dto;

import javax.persistence.*;

import de.remsfal.core.model.BuildingModel;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@NamedQuery(name = "BuildingEntity.findByPropertyId",
        query = "SELECT m FROM BuildingEntity m WHERE m.propertyId = :propertyId")
@Entity
@Table(name = "BUILDING")
public class BuildingEntity extends AbstractEntity implements BuildingModel {

    @Id
    @Column(name = "ID", columnDefinition = "char", nullable = false, length = 36)
    private String id;
    @Column(name = "PROPERTY_ID", columnDefinition = "char", length = 36)
    private String propertyId;

    @Column(name = "TITLE")
    private String title;
    
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getPropertyId() {
        // TODO Auto-generated method stub
        return null;
    }

}
