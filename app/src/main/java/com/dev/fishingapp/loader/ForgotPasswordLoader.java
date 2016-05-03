package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.ForgotPassword;
import com.dev.fishingapp.data.model.ForgotPasswordRequest;

/**
 * Created by user on 5/3/2016.
 */
public class ForgotPasswordLoader extends AbstractLoader<ForgotPassword> {
    private ForgotPasswordRequest changePwdRequest;



    public ForgotPasswordLoader(ForgotPasswordRequest changePwdRequest) {
        super(FishingApplication.getContext());
        this.changePwdRequest=changePwdRequest;
    }

    @Override
    protected ForgotPassword doLoadInBackground() {
        ForgotPassword reponse=getService().forgotPassword("application/json", "application/json", changePwdRequest);
        return reponse;
    }
}
