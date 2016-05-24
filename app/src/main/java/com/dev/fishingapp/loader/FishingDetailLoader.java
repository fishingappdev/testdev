package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.FishDetailResponse;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 5/23/2016.
 */
public class FishingDetailLoader extends AbstractLoader<FishDetailResponse> {
    private String nid;
    public FishingDetailLoader(String nid) {
        super(FishingApplication.getContext());
        this.nid=nid;
    }

    @Override
    protected FishDetailResponse doLoadInBackground() {
        FishDetailResponse reponse=getService().getFishDetail("application/x-www-form-urlencoded", "application/json", AppConstants.FISH_DETAIL_API_ACTION,nid,"all");
        return reponse;
    }
}