package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.Login;
import com.dev.fishingapp.data.model.LoginRequest;

/**
 * Created by user on 4/20/2016.
 */
public class LoginLoader extends AbstractLoader<Login> {
    private LoginRequest loginrequest;



    public LoginLoader(LoginRequest loginRequest) {
        super(FishingApplication.getContext());
         this.loginrequest=loginRequest;
    }

    @Override
    protected Login doLoadInBackground() {
        Login getLoginData=getService().login("application/json","application/json",loginrequest);
        return getLoginData;
    }
}
