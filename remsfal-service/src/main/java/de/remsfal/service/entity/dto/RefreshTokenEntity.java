package de.remsfal.service.entity.dto;

import de.remsfal.core.model.RefreshTokenModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "REFRESH_TOKEN")
public class RefreshTokenEntity  extends AbstractEntity implements RefreshTokenModel {
    @Id
    @Column(name = "ID", columnDefinition = "char", nullable = false, length = 36)
    private String id;

    @Column(name = "TOKEN")
    private String token;

    @Column(name = "USER_ID", columnDefinition = "char")
    private String userId;

    @Column(name = "EXPIRY_DATE")
    private Date expiryDate;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public Date getExpiryDate() {
        return expiryDate;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    // missing setter for userId
    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
