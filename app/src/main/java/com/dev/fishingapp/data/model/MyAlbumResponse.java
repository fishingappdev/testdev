package com.dev.fishingapp.data.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Swati on 5/17/2016.
 */
public class MyAlbumResponse implements Serializable {
    int faultCode;
    String status;
    ArrayList<MyAlbum> myalbums;

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

    public ArrayList<MyAlbum> getMyalbums() {
        return myalbums;
    }

    public void setMyalbums(ArrayList<MyAlbum> myalbums) {
        this.myalbums = myalbums;
    }
}
