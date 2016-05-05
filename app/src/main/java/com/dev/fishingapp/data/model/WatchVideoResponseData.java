package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by Swati on 5/4/2016.
 */
public class WatchVideoResponseData implements Serializable {
    private String Video;

    public String getVideo() {
        return Video;
    }

    public void setVideo(String video) {
        Video = video;
    }
}
