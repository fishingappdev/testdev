package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.ForgotPassword;
import com.dev.fishingapp.data.model.ForgotPasswordRequest;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 5/3/2016.
 */
public class ForgotPasswordLoader extends AbstractLoader<ForgotPassword> {
    private ForgotPasswordRequest changePwdRequest;
    private String email;



    public ForgotPasswordLoader(String email) {
        super(FishingApplication.getContext());
        this.email=email;
    }

    @Override
    protected ForgotPassword doLoadInBackground() {
        ForgotPassword reponse=getService().forgotPassword("application/x-www-form-urlencoded", "application/json", AppConstants.FORGOT_PASSWORD_API_ACTION,email);
        return reponse;
    }
}
