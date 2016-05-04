package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by Swati on 5/3/2016.
 */
public class GetEditProfileRequest implements Serializable {
    String user_id;

    public GetEditProfileRequest(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
