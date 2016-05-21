package com.dev.fishingapp.data.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Swati on 5/19/2016.
 */
public class MyAlbumDetailResponse implements Serializable {
    int faultCode;
    String status;
    ArrayList<MyAlbumDetails> myalbums;
    Location location;

    public int getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(int faultCode) {
        this.faultCode = faultCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<MyAlbumDetails> getMyalbums() {
        return myalbums;
    }

    public void setMyalbums(ArrayList<MyAlbumDetails> myalbums) {
        this.myalbums = myalbums;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
