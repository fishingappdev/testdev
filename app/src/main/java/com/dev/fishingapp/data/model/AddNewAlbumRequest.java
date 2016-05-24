package com.dev.fishingapp.data.model;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Swati on 5/23/2016.
 */

public class AddNewAlbumRequest implements Serializable {
    String uid;
    //    15063
    String title;
    //        =Testalbum1
    String description;
    //    =testdes
    String locname;
    //    =delhi
    String street;
    //    =nagar
    String additional;
    //    =testadd
    String country_name;
    //    =New Zealand
    String country;
    //    =Australia,New+Zealand
    String albumimage;
//    =no
    File image;

    public AddNewAlbumRequest( String uid, String title, String description, String locname, String street, String additional, String country_name, String country, String albumimage, File image) {
        this.uid = uid;
        this.title = title;
        this.description = description;
        this.locname = locname;
        this.street = street;
        this.additional = additional;
        this.country_name = country_name;
        this.country = country;
        this.albumimage = albumimage;
        this.image=image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocname() {
        return locname;
    }

    public void setLocname(String locname) {
        this.locname = locname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAlbumimage() {
        return albumimage;
    }

    public void setAlbumimage(String albumimage) {
        this.albumimage = albumimage;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }
}
