package com.dev.fishingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 5/3/2016.
 */
public class FacebookData implements Parcelable{

    public FacebookUser getUser() {
        return User;
    }

    public void setUser(FacebookUser user) {
        User = user;
    }

    private FacebookUser User;
    public static final Parcelable.Creator<FacebookData> CREATOR
            = new Parcelable.Creator<FacebookData>() {
        public FacebookData createFromParcel(Parcel in) {
            return new FacebookData(in);
        }

        public FacebookData[] newArray(int size) {
            return new FacebookData[size];
        }
    };

    private FacebookData(Parcel in){
        User=in.readParcelable(getClass().getClassLoader());

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(User,flags);
    }
}
