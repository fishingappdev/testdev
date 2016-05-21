package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.MyAlbumDetailResponse;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 4/20/2016.
 */
public class MyAlbumDetailLoader extends AbstractLoader<MyAlbumDetailResponse> {
    private String nid;

    public MyAlbumDetailLoader(String nid) {
        super(FishingApplication.getContext());
        this.nid = nid;
    }

    @Override
    protected MyAlbumDetailResponse doLoadInBackground() {
        MyAlbumDetailResponse response = getService().getMyAlbumDetails("application/x-www-form-urlencoded", "application/json", AppConstants.MYALBUM_DETAILS_API_ACTION, "18928");
        return response;
    }
}
