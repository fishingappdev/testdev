package com.dev.fishingapp.data.model;

/**
 * Created by user on 5/1/2016.
 */
public class LoginRequest {
    public String getApiaction() {
        return apiaction;
    }

    public void setApiaction(String apiaction) {
        this.apiaction = apiaction;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String apiaction;
    private String username;
    private String password;




}
