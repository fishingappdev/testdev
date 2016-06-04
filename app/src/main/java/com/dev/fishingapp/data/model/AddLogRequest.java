package com.dev.fishingapp.data.model;

/**
 * Created by user on 5/31/2016.
 */
public class AddLogRequest {
    private String title;
    private String description;
    private String location;
    private String moon;
    private String date;
    private String tide;
    private String weather;
    private String latitude;


    public AddLogRequest(String title, String description, String location, String moon, String date, String tide, String weather, String latitude, String longitude) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.moon = moon;
        this.date = date;
        this.tide = tide;
        this.weather = weather;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTide() {
        return tide;
    }

    public void setTide(String tide) {
        this.tide = tide;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMoon() {
        return moon;
    }

    public void setMoon(String moon) {
        this.moon = moon;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String longitude;

}
