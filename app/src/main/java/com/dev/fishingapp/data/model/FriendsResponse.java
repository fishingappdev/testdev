package com.dev.fishingapp.data.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Swati on 6/2/2016.
 */

public class FriendsResponse implements Serializable {

    int faultcode;
    String status;
    ArrayList<Friend> data;

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

    public ArrayList<Friend> getData() {
        return data;
    }

    public void setData(ArrayList<Friend> data) {
        this.data = data;
    }
}
