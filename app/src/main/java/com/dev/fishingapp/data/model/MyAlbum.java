package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by skumari on 4/23/2016.
 */
public class MyAlbum implements Serializable{
    String nid;
    String title;
    String description;
    String imageurl;
    String country;

    public MyAlbum() {

    }

    public MyAlbum(String nid, String title, String description, String imageUrl, String country) {
        this.title = title;
        this.description = description;
        this.imageurl = imageUrl;
        this.country = country;
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

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNid() {
        return nid;
    }
}
