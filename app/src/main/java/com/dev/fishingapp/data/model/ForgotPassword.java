package com.dev.fishingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 5/3/2016.
 */
public class ForgotPassword implements Parcelable{

    private String message;

    public String getFaultcode() {
        return faultcode;
    }

    public void setFaultcode(String faultcode) {
        this.faultcode = faultcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String faultcode;
    private String status;

    public static final Parcelable.Creator<ForgotPassword> CREATOR
            = new Parcelable.Creator<ForgotPassword>() {
        public ForgotPassword createFromParcel(Parcel in) {
            return new ForgotPassword(in);
        }

        public ForgotPassword[] newArray(int size) {
            return new ForgotPassword[size];
        }
    };

    private ForgotPassword(Parcel in){
        message=in.readString();
        faultcode=in.readString();
        status=in.readString();


    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        message=dest.readString();
        faultcode=dest.readString();
        status=dest.readString();



    }
}
