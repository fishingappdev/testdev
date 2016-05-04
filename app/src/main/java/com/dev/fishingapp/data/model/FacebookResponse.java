package com.dev.fishingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 5/3/2016.
 */
public class FacebookResponse implements Parcelable{


    public static final Parcelable.Creator<FacebookResponse> CREATOR
            = new Parcelable.Creator<FacebookResponse>() {
        public FacebookResponse createFromParcel(Parcel in) {
            return new FacebookResponse(in);
        }

        public FacebookResponse[] newArray(int size) {
            return new FacebookResponse[size];
        }
    };
    private boolean response;

    public FacebookData getData() {
        return data;
    }

    public void setData(FacebookData data) {
        this.data = data;
    }

    private FacebookData data;

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    private FacebookResponse(Parcel in){
        response = in.readByte() != 0;
        data=in.readParcelable(getClass().getClassLoader());

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (response ? 1 : 0));
        dest.writeParcelable(data, flags);

    }
}
