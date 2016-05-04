package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by Swati on 5/3/2016.
 */
public class GetEditProfileResponse implements Serializable {
    boolean response;
    GetEditProfileResponseData data;

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public GetEditProfileResponseData getData() {
        return data;
    }

    public void setData(GetEditProfileResponseData data) {
        this.data = data;
    }
}
