package com.dev.fishingapp.data.model;

import java.io.Serializable;

/**
 * Created by skumari on 5/1/2016.
 */
public class SignUpRequest implements Serializable {
    final String username, email, confemail, password, firstname, lastname, country;

    public SignUpRequest(String username, String email, String confemail, String password, String firstname, String lastname, String country) {
        this.username = username;
        this.email = email;
        this.confemail = confemail;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.country = country;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getConfemail() {
        return confemail;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getCountry() {
        return country;
    }
}
