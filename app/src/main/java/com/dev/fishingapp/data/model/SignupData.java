package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by user on 5/1/2016.
 */
public class SignupData implements Serializable{
    private String uid;
    private String name;
    private String pass;
    private String mail;

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


    public String getUser_id() {
        return uid;
    }

    public void setUser_id(String user_id) {
        this.uid = user_id;
    }

}
