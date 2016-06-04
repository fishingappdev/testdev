package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.AddFishResponse;
import com.dev.fishingapp.data.model.AddLogRequest;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 5/31/2016.
 */
public class AddLogLoader  extends AbstractLoader<AddFishResponse> {
    private AddLogRequest addFishLogRequest;
    private String uid;

    public AddLogLoader(AddLogRequest addLogRequest,String uid) {
        super(FishingApplication.getContext());
        this.addFishLogRequest = addLogRequest;
        this.uid=uid;

    }

    @Override
    protected AddFishResponse doLoadInBackground() {
        AddFishResponse response ;
        response=getService().addFishLog("application/x-www-form-urlencoded", "application/json", AppConstants.ADD_FISH_LOG_API_ACTION, uid, addFishLogRequest.getTitle(), addFishLogRequest.getDescription(), addFishLogRequest.getLocation(), addFishLogRequest.getMoon(), addFishLogRequest.getDate(), addFishLogRequest.getTide(), addFishLogRequest.getWeather(), addFishLogRequest.getLongitude(), addFishLogRequest.getLatitude());
        return response;
    }
}
