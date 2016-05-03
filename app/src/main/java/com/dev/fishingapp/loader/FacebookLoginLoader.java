package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.FacebookLoginRequest;
import com.dev.fishingapp.data.model.FacebookResponse;

/**
 * Created by user on 5/3/2016.
 */
public class FacebookLoginLoader extends AbstractLoader<FacebookResponse> {
    private FacebookLoginRequest FbLoginrequest;



    public FacebookLoginLoader(FacebookLoginRequest FbLoginrequest) {
        super(FishingApplication.getContext());
        this.FbLoginrequest=FbLoginrequest;
    }

    @Override
    protected FacebookResponse doLoadInBackground() {
        FacebookResponse getFbLoginData=getService().facebookLogin("application/json", "application/json", FbLoginrequest);
        return getFbLoginData;
    }
}

