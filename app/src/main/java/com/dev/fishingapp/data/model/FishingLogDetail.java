package com.dev.fishingapp.data.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by user on 5/26/2016.
 */
public class FishingLogDetail implements Serializable {
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

    public String getMoon() {
        return moon;
    }

    public void setMoon(String moon) {
        this.moon = moon;
    }

    public String getTide() {
        return tide;
    }

    public void setTide(String tide) {
        this.tide = tide;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<FishingLogComment> getComment() {
        return comment;
    }

    public void setComment(ArrayList<FishingLogComment> comment) {
        this.comment = comment;
    }

    private String nid;
    private String title;
    private String description;
    private String location;
    private String moon;
    private String tide;
    private String weather;
    private String longitude;
    private String latitude;
    private String date;
private ArrayList<FishingLogComment> comment;





}
