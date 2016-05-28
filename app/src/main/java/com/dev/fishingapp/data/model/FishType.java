package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by user on 5/28/2016.
 */
public class FishType implements Serializable{
    private String nid;
    private String title;

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //to display object as a string in spinner
    public String toString() {
        return title;
    }
}
