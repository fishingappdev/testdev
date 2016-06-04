package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.MyFishResponse;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 6/4/2016.
 */
public class FishListCategoryLoader extends AbstractLoader<MyFishResponse> {
    private String catid;
    private String uid;

    public FishListCategoryLoader(String uid,String catId) {
        super(FishingApplication.getContext());
        this.uid=uid;
        this.catid=catId;
    }

    @Override
    protected MyFishResponse doLoadInBackground() {
        MyFishResponse reponse=getService().getFishListCategory("application/x-www-form-urlencoded", "application/json", AppConstants.FISH_CATEGORY_API_ACTION,uid,catid,"all");
        return reponse;
    }
}