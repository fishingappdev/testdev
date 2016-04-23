package com.dev.fishingapp.data.model;

/**
 * Created by user on 4/23/2016.
 */
public class FishingLogResponse {
    private String fishTitle;
    private String location;
    private String description;

    public FishingLogResponse(String fishTitle, String location, String description, String date){
        this.date=date;
        this.fishTitle=fishTitle;
        this.description=description;
        this.location=location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getFishTitle() {
        return fishTitle;
    }

    public void setFishTitle(String fishTitle) {
        this.fishTitle = fishTitle;
    }

    private String date;
}
