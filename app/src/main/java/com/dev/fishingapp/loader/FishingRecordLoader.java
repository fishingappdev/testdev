package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.FishingRecordResponse;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 5/24/2016.
 */
public class FishingRecordLoader extends AbstractLoader<FishingRecordResponse> {
    public FishingRecordLoader() {
        super(FishingApplication.getContext());
    }

    @Override
    protected FishingRecordResponse doLoadInBackground() {
        FishingRecordResponse reponse=getService().getFishisngRecord("application/x-www-form-urlencoded", "application/json", AppConstants.FISHING_RECORD_API_ACTION,"all");
        return reponse;
    }
}
