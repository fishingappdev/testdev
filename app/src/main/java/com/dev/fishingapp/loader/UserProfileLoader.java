package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.Login;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 5/3/2016.
 */
public class UserProfileLoader extends AbstractLoader<Login> {
String email;


    public UserProfileLoader(String email) {
        super(FishingApplication.getContext());
        this.email=email;
    }

    @Override
    protected Login doLoadInBackground() {
        Login userData=getService().getUserProfile("application/x-www-form-urlencoded", "application/json", AppConstants.USER_PROFILE_API_ACTION, email);
        return userData;
    }
}

