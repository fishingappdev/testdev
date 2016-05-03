package com.dev.fishingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 5/3/2016.
 */
public class ForgotPassword implements Parcelable{
    private boolean response;
    private boolean data;

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
        response = in.readByte() != 0;
        data = in.readByte() != 0;


    }

    public boolean isReponse() {
        return response;
    }

    public void setReponse(boolean reponse) {
        this.response = reponse;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (response ? 1 : 0));
        dest.writeByte((byte) (data ? 1 : 0));

    }
}
