package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.FriendDetailResponse;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 4/20/2016.
 */
public class FriendDetailLoader extends AbstractLoader<FriendDetailResponse> {
    private String uid;

    public FriendDetailLoader(String uid) {
        super(FishingApplication.getContext());
        this.uid = uid;
    }

    @Override
    protected FriendDetailResponse doLoadInBackground() {
        FriendDetailResponse response = getService().getFriendDetail("application/x-www-form-urlencoded", "application/json", AppConstants.FRIEND_DETAILS_API_ACTION, uid);
        return response;
    }
}
