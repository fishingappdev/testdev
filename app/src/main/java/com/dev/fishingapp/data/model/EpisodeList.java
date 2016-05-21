package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by user on 5/21/2016.
 */
public class EpisodeList implements Serializable {
    private String title;
    private String description;
    private String episode_name;

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

    public String getEpisode_name() {
        return episode_name;
    }

    public void setEpisode_name(String episode_name) {
        this.episode_name = episode_name;
    }

    public String getFull_video_image() {
        return full_video_image;
    }

    public void setFull_video_image(String full_video_image) {
        this.full_video_image = full_video_image;
    }

    public String getFull_video_url() {
        return full_video_url;
    }

    public void setFull_video_url(String full_video_url) {
        this.full_video_url = full_video_url;
    }

    private String full_video_image;
    private String full_video_url;

}
