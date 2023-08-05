package de.remsfal.service.entity.dto;

import javax.persistence.*;

import de.remsfal.core.model.ApartmentModel;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@NamedQuery(name = "ApartmentEntity.findByBuildingId",
        query = "SELECT m FROM ApartmentEntity m WHERE m.buildingId = :buildingId")
@Entity
@Table(name = "APARTMENT")
public class ApartmentEntity extends AbstractEntity implements ApartmentModel {

    @Id
    @Column(name = "ID", columnDefinition = "char", nullable = false, length = 36)
    private String id;
    
    @Column(name = "TITLE")
    private String title;
    @Column(name = "BUILDING_ID", columnDefinition = "char", length = 36)
    private String buildingId;
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getBuildingId() {
        // TODO Auto-generated method stub
        return null;
    }

}
