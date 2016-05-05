package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by Swati on 5/4/2016.
 */
public class WatchVideoRequest implements Serializable {
    private String empty;

    public WatchVideoRequest()
    {
        empty="";
    }

    public String getEmpty() {
        return empty;
    }

    public void setEmpty(String empty) {
        this.empty = empty;
    }
}
