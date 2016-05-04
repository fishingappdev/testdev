package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by Swati on 5/3/2016.
 */
public class GetEditProfileResponseDataUser implements Serializable {
    User User;
    Profile Profile;

    public User getUser() {
        return User;
    }

    public void setUser(User User) {
        this.User = User;
    }

    public Profile getProfile() {
        return Profile;
    }

    public void setProfile(Profile Profile) {
        this.Profile = Profile;
    }
}
