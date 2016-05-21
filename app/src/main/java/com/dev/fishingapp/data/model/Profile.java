package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by Swati on 5/4/2016.
 */
public class Profile implements Serializable {
    String uid;
    String field_first_name;
    String field_last_name;
    String field_about_me;
    String field_fishing_preferences;
    String field_country;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getField_first_name() {
        return field_first_name;
    }

    public void setField_first_name(String field_first_name) {
        this.field_first_name = field_first_name;
    }

    public String getField_last_name() {
        return field_last_name;
    }

    public void setField_last_name(String field_last_name) {
        this.field_last_name = field_last_name;
    }

    public String getField_about_me() {
        return field_about_me;
    }

    public void setField_about_me(String field_about_me) {
        this.field_about_me = field_about_me;
    }

    public String getField_fishing_preferences() {
        return field_fishing_preferences;
    }

    public void setField_fishing_preferences(String field_fishing_preferences) {
        this.field_fishing_preferences = field_fishing_preferences;
    }

    public String getField_country() {
        return field_country;
    }

    public void setField_country(String field_country) {
        this.field_country = field_country;
    }
}
