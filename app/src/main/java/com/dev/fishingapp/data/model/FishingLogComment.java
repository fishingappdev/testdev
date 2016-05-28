package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by user on 5/26/2016.
 */
public class FishingLogComment implements Serializable {
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComment_body() {
        return comment_body;
    }

    public void setComment_body(String comment_body) {
        this.comment_body = comment_body;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegistered_name() {
        return registered_name;
    }

    public void setRegistered_name(String registered_name) {
        this.registered_name = registered_name;
    }

    private String subject;
    private String comment_body;
    private String created;
    private String status;
    private String registered_name;


}
