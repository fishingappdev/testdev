package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by Swati on 5/3/2016.
 */
public class SetEditProfileResponse implements Serializable {
    int faultcode;
    String status;
    Profile profiledata;

    public int getFaultcode() {
        return faultcode;
    }

    public void setFaultcode(int faultcode) {
        this.faultcode = faultcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Profile getProfiledata() {
        return profiledata;
    }

    public void setProfiledata(Profile profiledata) {
        this.profiledata = profiledata;
    }
}
