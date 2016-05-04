package com.dev.fishingapp.loader;

/**
 * Created by skumari on 5/1/2016.
 */

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.SetEditProfileRequest;
import com.dev.fishingapp.data.model.SetEditProfileResponse;

public class SetProfileLoader extends AbstractLoader<SetEditProfileResponse> {
    SetEditProfileRequest setEditProfileRequest;

    public SetProfileLoader(SetEditProfileRequest setEditProfileRequest) {
        super(FishingApplication.getContext());
        this.setEditProfileRequest = setEditProfileRequest;
    }

    protected SetEditProfileResponse doLoadInBackground() {
        return getService().setProfile("application/json","application/json",setEditProfileRequest);
    }
}
