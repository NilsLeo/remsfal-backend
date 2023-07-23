package de.remsfal.core.model;

import java.util.Date;

public interface RefreshTokenModel {


    String getId();

    String getUserId();

    String getToken();

    Date getExpiryDate();

    void setToken(String token);

    // missing setter for userId
    void setUserId(String userId);
}
