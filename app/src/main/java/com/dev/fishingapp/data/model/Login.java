package com.dev.fishingapp.data.model;

/**
 * Created by user on 4/20/2016.
 */
public class Login {
    private boolean response;
    private LoginData data;

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }
}
