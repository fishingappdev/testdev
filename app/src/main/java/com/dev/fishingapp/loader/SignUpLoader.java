package com.dev.fishingapp.loader;

/**
 * Created by skumari on 5/1/2016.
 */

import android.provider.SyncStateContract;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.SignUpRequest;
import com.dev.fishingapp.data.model.SignUpResponse;
import com.dev.fishingapp.util.AppConstants;

public class SignUpLoader extends AbstractLoader<SignUpResponse> {
    SignUpRequest signUpRequest;

    public SignUpLoader(SignUpRequest signUpRequest) {
        super(FishingApplication.getContext());
        this.signUpRequest = signUpRequest;
    }

    protected SignUpResponse doLoadInBackground() {
        return getService().signUp("application/x-www-form-urlencoded","application/json", AppConstants.SIGNUP_API_ACTION,signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getFirstname(),signUpRequest.getLastname(), signUpRequest.getCountry());
    }
}
