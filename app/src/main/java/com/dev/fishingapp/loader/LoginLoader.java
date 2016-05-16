package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.Login;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 4/20/2016.
 */
public class LoginLoader extends AbstractLoader<Login> {
    private String username;
    private String Password;



    public LoginLoader(String username,String Password ) {
        super(FishingApplication.getContext());
         this.username=username;
         this.Password=Password;
    }

    @Override
    protected Login doLoadInBackground() {
        Login getLoginData=getService().login("application/x-www-form-urlencoded","application/json", AppConstants.LOGIN_API_ACTION,username,Password);
        return getLoginData;
    }
}
