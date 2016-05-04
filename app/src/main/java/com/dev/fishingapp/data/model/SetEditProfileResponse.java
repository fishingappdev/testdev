package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by Swati on 5/3/2016.
 */
public class SetEditProfileResponse implements Serializable {
    boolean response;
    SetEditProfileResponseData data;

    public SetEditProfileResponseData getData() {
        return data;
    }

    public void setData(SetEditProfileResponseData data) {
        this.data = data;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }
}
