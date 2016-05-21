package com.dev.fishingapp.loader;

/**
 * Created by skumari on 5/1/2016.
 */

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.GetEditProfileRequest;
import com.dev.fishingapp.data.model.GetEditProfileResponse;
import com.dev.fishingapp.util.AppConstants;

public class GetProfileLoader extends AbstractLoader<GetEditProfileResponse> {
    GetEditProfileRequest getEditProfileRequest;

    public GetProfileLoader(GetEditProfileRequest getEditProfileRequest) {
        super(FishingApplication.getContext());
        this.getEditProfileRequest = getEditProfileRequest;
    }

    protected GetEditProfileResponse doLoadInBackground() {
//        getEditProfileRequest.getUser_id()
        return getService().getProfile("application/x-www-form-urlencoded", "application/json", AppConstants.GET_USER_PROFILE_API_ACTION,getEditProfileRequest.getUser_id());
    }
}
