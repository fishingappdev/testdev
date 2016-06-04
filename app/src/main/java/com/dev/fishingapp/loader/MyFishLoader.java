package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.MyFishResponse;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 5/23/2016.
 */
public class MyFishLoader extends AbstractLoader<MyFishResponse> {
private String uid;
    public MyFishLoader(String uid) {
        super(FishingApplication.getContext());
        this.uid=uid;
    }

    @Override
    protected MyFishResponse doLoadInBackground() {
        MyFishResponse reponse=getService().getMyFishList("application/x-www-form-urlencoded", "application/json", AppConstants.MYFISH_LIST_API_ACTION,uid,"all");
        return reponse;
    }
}
