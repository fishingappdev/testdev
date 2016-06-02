package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by Swati on 5/19/2016.
 */
public class FriendDetailResponse implements Serializable {
    int faultcode;
    String status;
    FriendData data;

    public FriendData getData() {
        return data;
    }

    public void setData(FriendData data) {
        this.data = data;
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
