package com.dev.fishingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 5/1/2016.
 */
public class LoginData implements Parcelable{
    private String user_id;

    public static final Parcelable.Creator<LoginData> CREATOR
            = new Parcelable.Creator<LoginData>() {
        public LoginData createFromParcel(Parcel in) {
            return new LoginData(in);
        }

        public LoginData[] newArray(int size) {
            return new LoginData[size];
        }
    };

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(user_id);
    }

    private LoginData(Parcel in) {
        user_id = in.readString();


    }
}
