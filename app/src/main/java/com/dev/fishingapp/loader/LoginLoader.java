package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.Login;

/**
 * Created by user on 4/20/2016.
 */
public class LoginLoader extends AbstractLoader<Login> {
    private String username;
    private String password;

    public LoginLoader(String username, String password) {
        super(FishingApplication.getContext());
       this.username=username;
        this.password=password;
    }

    @Override
    protected Login doLoadInBackground() {
        Login getLoginData=getService().login(username,password);
        return getLoginData;
    }
}
