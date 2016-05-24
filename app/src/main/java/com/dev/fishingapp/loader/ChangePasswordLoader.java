package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.ChangePassword;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 5/2/2016.
 */
public class ChangePasswordLoader extends AbstractLoader<ChangePassword> {
  String uid,oldPassword,newPassword;



    public ChangePasswordLoader(String uid,String oldPassword,String newPassword) {
        super(FishingApplication.getContext());
        this.oldPassword=oldPassword;
        this.uid=uid;
        this.newPassword=newPassword;
    }

    @Override
    protected ChangePassword doLoadInBackground() {
        ChangePassword reponse=getService().changePassword("application/x-www-form-urlencoded", "application/json", AppConstants.CHANGE_PASSWORD_API_ACTION,uid,oldPassword,newPassword);
        return reponse;
    }
}
