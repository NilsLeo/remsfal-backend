package de.remsfal.service.entity.dto;

import javax.persistence.*;

import de.remsfal.core.model.PropertyModel;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@NamedQuery(name = "PropertyEntity.findByProjectId",
        query = "SELECT m FROM PropertyEntity m WHERE m.projectId = :projectId")
@Entity
@Table(name = "PROPERTY")
public class PropertyEntity extends AbstractEntity implements PropertyModel {

    @Id
    @Column(name = "ID", columnDefinition = "char", nullable = false, length = 36)
    private String id;
    @Column(name = "PROJECT_ID", columnDefinition = "char", length = 36)
    private String projectId;

    @Column(name = "TITLE")
    private String title;


    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getProjectId() {
        return projectId;
    }
    public void setProjectId(String projectId){this.projectId=projectId;}

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}
