package com.dev.fishingapp.data.model;

/**
 * Created by skumari on 4/23/2016.
 */
public class Friend {
    String title;
    String imageUrl;

    public Friend() {
    }

    public Friend(String title, String imageUrl) {
        this.title = title;
        this.imageUrl=imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
