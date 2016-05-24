package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by user on 5/24/2016.
 */
public class SignupProfileData implements Serializable{
    private String field_last_name;

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

    private String field_first_name;

}
