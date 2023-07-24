package de.remsfal.core.model;

import java.util.Date;

public interface RefreshTokenModel {


    String getId();

    String getToken();

    void setToken(String token);
}
