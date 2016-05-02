package com.dev.fishingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 4/20/2016.
 */
public class Login implements Parcelable{
    private boolean response;



    private LoginData data;
    public static final Parcelable.Creator<Login> CREATOR
            = new Parcelable.Creator<Login>() {
        public Login createFromParcel(Parcel in) {
            return new Login(in);
        }

        public Login[] newArray(int size) {
            return new Login[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    private Login(Parcel in) {
        response = in.readByte() != 0;
        data=in.readParcelable(getClass().getClassLoader());

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (response ? 1 : 0));
        dest.writeParcelable(data,flags);
    }





}
