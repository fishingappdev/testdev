package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by user on 5/23/2016.
 */
public class FishDetail implements Serializable{
    private String nid;
    private String title;
    private String description;
    private String where_caught;

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

    public String getWhere_caught() {
        return where_caught;
    }

    public void setWhere_caught(String where_caught) {
        this.where_caught = where_caught;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFish_length() {
        return fish_length;
    }

    public void setFish_length(String fish_length) {
        this.fish_length = fish_length;
    }

    public String getField_date_value() {
        return field_date_value;
    }

    public void setField_date_value(String field_date_value) {
        this.field_date_value = field_date_value;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    private String weight;
    private String fish_length;
    private String field_date_value;
    private String imageurl;


}
