package com.dev.fishingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 5/1/2016.
 */
public class LoginData implements Parcelable{
    private String uid;
    private String name;
    private String pass;
    private String mail;

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    private String profilepic;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

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
        return uid;
    }

    public void setUser_id(String user_id) {
        this.uid = user_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(name);
        dest.writeString(pass);
        dest.writeString(mail);
    }

    private LoginData(Parcel in) {
        uid = in.readString();
        name = in.readString();
        pass = in.readString();
        mail = in.readString();


    }
}
