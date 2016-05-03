package com.dev.fishingapp.loader;

/**
 * Created by skumari on 5/1/2016.
 */

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.SignUpRequest;
import com.dev.fishingapp.data.model.SignUpResponse;

public class SignUpLoader extends AbstractLoader<SignUpResponse> {
    SignUpRequest signUpRequest;

    public SignUpLoader(SignUpRequest signUpRequest) {
        super(FishingApplication.getContext());
        this.signUpRequest = signUpRequest;
    }

    protected SignUpResponse doLoadInBackground() {
        return getService().signUp("application/json","application/json",signUpRequest);
    }
}
