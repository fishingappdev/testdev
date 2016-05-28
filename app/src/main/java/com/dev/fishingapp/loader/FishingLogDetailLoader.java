package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.FishingLogDetail;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 5/26/2016.
 */
public class FishingLogDetailLoader extends AbstractLoader<FishingLogDetail> {
    private String nid;
    public FishingLogDetailLoader(String nid) {
        super(FishingApplication.getContext());
        this.nid=nid;
    }

    @Override
    protected FishingLogDetail doLoadInBackground() {
        FishingLogDetail reponse=getService().getFishisngLogDetail("application/x-www-form-urlencoded", "application/json", AppConstants.FISHING_LOG_DETAIL_API_ACTION,nid);
        return reponse;
    }
}