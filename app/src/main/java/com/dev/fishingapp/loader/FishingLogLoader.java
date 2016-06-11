package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.FishingLogResponse;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 5/25/2016.
 */
public class FishingLogLoader extends AbstractLoader<FishingLogResponse> {
    String uid;
    public FishingLogLoader(String uid) {
        super(FishingApplication.getContext());
        this.uid=uid;
    }

    @Override
    protected FishingLogResponse doLoadInBackground() {
        FishingLogResponse reponse=getService().getFishisngLog("application/x-www-form-urlencoded", "application/json", AppConstants.FISHING_LOG_API_ACTION,"all",uid);
        return reponse;
    }
}