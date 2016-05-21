package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by skumari on 5/1/2016.
 */
public class SignUpResponse implements Serializable {
    private int faultcode;
    private String status;
    private String message;


    private SignupData userdata;

    public SignupData getUserdata() {
        return userdata;
    }

    public void setUserdata(SignupData userdata) {
        this.userdata = userdata;
    }

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
