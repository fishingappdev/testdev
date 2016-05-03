package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.ChangePassword;
import com.dev.fishingapp.data.model.ChangePasswordRequest;

/**
 * Created by user on 5/2/2016.
 */
public class ChangePasswordLoader extends AbstractLoader<ChangePassword> {
    private ChangePasswordRequest changePwdRequest;



    public ChangePasswordLoader(ChangePasswordRequest changePwdRequest) {
        super(FishingApplication.getContext());
        this.changePwdRequest=changePwdRequest;
    }

    @Override
    protected ChangePassword doLoadInBackground() {
        ChangePassword reponse=getService().changePassword("application/json", "application/json", changePwdRequest);
        return reponse;
    }
}
