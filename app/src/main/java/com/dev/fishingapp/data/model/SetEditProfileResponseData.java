package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by Swati on 5/3/2016.
 */
public class SetEditProfileResponseData implements Serializable{
    String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
