package com.dev.fishingapp.data.model;

import java.io.File;

/**
 * Created by user on 5/28/2016.
 */
public class AddFishRequest {
    public AddFishRequest(String title, String description, String pbval, String typeoffishnid, String date, String where_caught, String length, String weight, String fishimage, String uid, File image) {
        this.title = title;
        this.description = description;
        this.pbval = pbval;
        this.typeoffishnid = typeoffishnid;
        this.date = date;
        this.where_caught = where_caught;
        this.length = length;
        this.weight = weight;
        this.fishimage = fishimage;
        this.uid = uid;
        this.image=image;
    }

    private String title;

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

    public String getPbval() {
        return pbval;
    }

    public void setPbval(String pbval) {
        this.pbval = pbval;
    }

    public String getTypeoffishnid() {
        return typeoffishnid;
    }

    public void setTypeoffishnid(String typeoffishnid) {
        this.typeoffishnid = typeoffishnid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWhere_caught() {
        return where_caught;
    }

    public void setWhere_caught(String where_caught) {
        this.where_caught = where_caught;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFishimage() {
        return fishimage;
    }

    public void setFishimage(String fishimage) {
        this.fishimage = fishimage;
    }

    private String description;
    private String pbval;
    private String typeoffishnid;
    private String date;
    private String where_caught;
    private String length;
    private String weight;
    private String fishimage;
    private String uid;

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    private File image;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
