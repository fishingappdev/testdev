package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by Swati on 5/3/2016.
 */
public class GetEditProfileResponseData implements Serializable {
    GetEditProfileResponseDataUser User;

    public GetEditProfileResponseDataUser getUser() {
        return User;
    }

    public void setUser(GetEditProfileResponseDataUser User) {
        this.User = User;
    }
}
