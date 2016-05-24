package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by user on 5/24/2016.
 */
public class FishingRecord implements Serializable{
    private String nid;
    private String title;
    private String record_type;
    private String where_caught;
    private String field_date;

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

    public String getRecord_type() {
        return record_type;
    }

    public void setRecord_type(String record_type) {
        this.record_type = record_type;
    }

    public String getWhere_caught() {
        return where_caught;
    }

    public void setWhere_caught(String where_caught) {
        this.where_caught = where_caught;
    }

    public String getField_date() {
        return field_date;
    }

    public void setField_date(String field_date) {
        this.field_date = field_date;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    private String imageurl;

}
