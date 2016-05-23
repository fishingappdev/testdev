package com.dev.fishingapp.data.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Swati on 5/19/2016.
 */
public class MyAlbumDetails implements Serializable {
    String nid;
    String title;
    String body;
    ArrayList<String> imageurl;
    ArrayList<String> country;
    ArrayList<String> comment;
    Location location;

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ArrayList<String> getImageurl() {
        return imageurl;
    }

    public void setImageurl(ArrayList<String> imageurl) {
        this.imageurl = imageurl;
    }

    public ArrayList<String> getCountry() {
        return country;
    }

    public void setCountry(ArrayList<String> country) {
        this.country = country;
    }

    public ArrayList<String> getComment() {
        return comment;
    }

    public void setComment(ArrayList<String> comment) {
        this.comment = comment;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
