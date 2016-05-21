package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by Swati on 5/19/2016.
 */
public class Location implements Serializable {
    String name;
    String street;
    String additional;
    String country_name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
