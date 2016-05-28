package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.FishTypeResponse;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 5/28/2016.
 */
public class FishTypeLoader extends AbstractLoader<FishTypeResponse> {
    public FishTypeLoader() {
        super(FishingApplication.getContext());
    }

    @Override
    protected FishTypeResponse doLoadInBackground() {
        FishTypeResponse reponse=getService().getFishType("application/x-www-form-urlencoded", "application/json", AppConstants.FISH_TYPE_API_ACTION);
        return reponse;
    }
}
