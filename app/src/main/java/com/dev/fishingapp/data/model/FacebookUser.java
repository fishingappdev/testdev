package com.dev.fishingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 5/3/2016.
 */
public class FacebookUser implements Parcelable{
    public static final Parcelable.Creator<FacebookUser> CREATOR
            = new Parcelable.Creator<FacebookUser>() {
        public FacebookUser createFromParcel(Parcel in) {
            return new FacebookUser(in);
        }

        public FacebookUser[] newArray(int size) {
            return new FacebookUser[size];
        }
    };

    private FacebookUser(Parcel in){
        user_id = in.readString();
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    private String user_id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);


    }
}
