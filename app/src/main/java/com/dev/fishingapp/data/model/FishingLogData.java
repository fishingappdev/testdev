package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by user on 5/25/2016.
 */
public class FishingLogData implements Serializable {
    private String nid;
    private String title;
    private String description;
    private String location;
    private String date;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
