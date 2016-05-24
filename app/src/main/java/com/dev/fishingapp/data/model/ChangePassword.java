package com.dev.fishingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 5/2/2016.
 */
public class ChangePassword implements Parcelable {
    private String faultcode;

    public String getFaultcode() {
        return faultcode;
    }

    public void setFaultcode(String faultcode) {
        this.faultcode = faultcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String status;
    private String message;



    public static final Parcelable.Creator<ChangePassword> CREATOR
            = new Parcelable.Creator<ChangePassword>() {
        public ChangePassword createFromParcel(Parcel in) {
            return new ChangePassword(in);
        }

        public ChangePassword[] newArray(int size) {
            return new ChangePassword[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(faultcode);
        dest.writeString(message);
        dest.writeString(status);

    }

    private ChangePassword(Parcel in){
        faultcode=in.readString();
        message=in.readString();
        status=in.readString();

    }
}
