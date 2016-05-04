package com.dev.fishingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 5/2/2016.
 */
public class ChangePassword implements Parcelable {
    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    private boolean response;


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
        dest.writeByte((byte) (response ? 1 : 0));

    }

    private ChangePassword(Parcel in){
        response = in.readByte() != 0;

    }
}
