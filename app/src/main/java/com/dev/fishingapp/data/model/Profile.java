package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by Swati on 5/4/2016.
 */
public class Profile implements Serializable {
    String user_id, about_user, fishing_pref, profile_pic;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAbout_user() {
        return about_user;
    }

    public void setAbout_user(String about_user) {
        this.about_user = about_user;
    }

    public String getFishing_pref() {
        return fishing_pref;
    }

    public void setFishing_pref(String fishing_pref) {
        this.fishing_pref = fishing_pref;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }
}
