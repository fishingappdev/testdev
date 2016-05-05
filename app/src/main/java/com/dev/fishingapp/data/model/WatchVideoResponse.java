package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by Swati on 5/4/2016.
 */
public class WatchVideoResponse implements Serializable {
    private boolean response;
    private WatchVideoResponseData data;

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public WatchVideoResponseData getData() {
        return data;
    }

    public void setData(WatchVideoResponseData data) {
        this.data = data;
    }
}
