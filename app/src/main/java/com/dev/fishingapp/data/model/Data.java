package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by skumari on 5/1/2016.
 */
public class Data implements Serializable{
    String user_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}

