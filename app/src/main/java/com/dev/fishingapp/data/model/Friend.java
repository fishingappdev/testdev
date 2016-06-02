package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by skumari on 4/23/2016.
 */
public class Friend implements Serializable{
    String uid;
    String name;
    String picture;

    public Friend() {
    }

    public Friend(String uid, String name, String picture) {
        this.uid = uid;
        this.name = name;
        this.picture=picture;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
