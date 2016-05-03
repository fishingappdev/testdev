package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by skumari on 5/1/2016.
 */
public class SignUpResponse implements Serializable {
    boolean success;
    Data data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
