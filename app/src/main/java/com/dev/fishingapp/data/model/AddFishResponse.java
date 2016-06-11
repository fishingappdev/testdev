package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by user on 5/28/2016.
 */
public class AddFishResponse implements Serializable {
    private String faultcode;
    private String status;
    private String message;

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    private String profilepic;

    public String getFaultcode() {
        return faultcode;
    }

    public void setFaultcode(String faultcode) {
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
