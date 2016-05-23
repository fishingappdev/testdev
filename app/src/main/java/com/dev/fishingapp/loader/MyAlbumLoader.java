package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.MyAlbumResponse;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 4/20/2016.
 */
public class MyAlbumLoader extends AbstractLoader<MyAlbumResponse> {
    private String userid;

    public MyAlbumLoader(String userid) {
        super(FishingApplication.getContext());
        this.userid = userid;
    }

    @Override
    protected MyAlbumResponse doLoadInBackground() {
        MyAlbumResponse response = getService().getMyAlbum("application/x-www-form-urlencoded", "application/json", AppConstants.MYALBUM_API_ACTION, userid);
        return response;
    }
}
