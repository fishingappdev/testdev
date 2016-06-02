package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by Swati on 6/2/2016.
 */

public class FriendData implements Serializable {
    String uid;
    String name;
    String picture;
    String last_name;
    String about_me;
    String fishing_preferences;
    String field_country;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAbout_me() {
        return about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    public String getFishing_preferences() {
        return fishing_preferences;
    }

    public void setFishing_preferences(String fishing_preferences) {
        this.fishing_preferences = fishing_preferences;
    }

    public String getField_country() {
        return field_country;
    }

    public void setField_country(String field_country) {
        this.field_country = field_country;
    }
}
