package com.dev.fishingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 5/24/2016.
 */
public class ProfileData implements Parcelable{

    private ProfileData(Parcel in) {
        field_first_name=in.readString();
        field_last_name=in.readString();

    }


    public static final Parcelable.Creator<ProfileData> CREATOR
            = new Parcelable.Creator<ProfileData>() {
        public ProfileData createFromParcel(Parcel in) {
            return new ProfileData(in);
        }

        public ProfileData[] newArray(int size) {
            return new ProfileData[size];
        }
    };
    private String field_first_name;

    public String getField_last_name() {
        return field_last_name;
    }

    public void setField_last_name(String field_last_name) {
        this.field_last_name = field_last_name;
    }

    public String getField_first_name() {
        return field_first_name;
    }

    public void setField_first_name(String field_first_name) {
        this.field_first_name = field_first_name;
    }

    private String field_last_name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(field_first_name);
        dest.writeString(field_last_name);
    }
}
