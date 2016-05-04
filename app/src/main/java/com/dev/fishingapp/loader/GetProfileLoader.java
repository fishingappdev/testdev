package com.dev.fishingapp.loader;

/**
 * Created by skumari on 5/1/2016.
 */

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.GetEditProfileRequest;
import com.dev.fishingapp.data.model.GetEditProfileResponse;

public class GetProfileLoader extends AbstractLoader<GetEditProfileResponse> {
    GetEditProfileRequest getEditProfileRequest;

    public GetProfileLoader(GetEditProfileRequest getEditProfileRequest) {
        super(FishingApplication.getContext());
        this.getEditProfileRequest = getEditProfileRequest;
    }

    protected GetEditProfileResponse doLoadInBackground() {
        return getService().getProfile("application/json","application/json",getEditProfileRequest);
    }
}
