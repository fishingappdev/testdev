package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by Swati on 5/19/2016.
 */
public class AddRemoveFriendResponse implements Serializable {
    int faultcode;
    String status;
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
}
