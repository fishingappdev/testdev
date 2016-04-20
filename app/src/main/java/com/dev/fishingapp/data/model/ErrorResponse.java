package com.dev.fishingapp.data.model;

public class ErrorResponse {

    private int httpCode;
    private int errorCode;
    private String message;
    private int cappingLimit;

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCappingLimit() {
        return cappingLimit;
    }

    public void setCappingLimit(int cappingLimit) {
        this.cappingLimit = cappingLimit;
    }
}

