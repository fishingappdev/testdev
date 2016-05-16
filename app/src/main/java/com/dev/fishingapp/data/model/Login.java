package com.dev.fishingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 4/20/2016.
 */
public class Login implements Parcelable{
    private int faultcode;
    private String status;

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

    public int getFaultcode() {
        return faultcode;
    }

    public void setFaultcode(int faultcode) {
        this.faultcode = faultcode;
    }

    private String message;


    private LoginData userdata;
    public static final Parcelable.Creator<Login> CREATOR
            = new Parcelable.Creator<Login>() {
        public Login createFromParcel(Parcel in) {
            return new Login(in);
        }

        public Login[] newArray(int size) {
            return new Login[size];
        }
    };


   public LoginData getData() {
        return userdata;
    }

    public void setData(LoginData data) {
        this.userdata = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private Login(Parcel in) {
        faultcode = in.readInt();
        userdata=in.readParcelable(getClass().getClassLoader());
        status=in.readString();
        message=in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
       dest.writeInt(faultcode);
        dest.writeParcelable(userdata, flags);
        dest.writeString(status);
        dest.writeString(message);
    }





}
