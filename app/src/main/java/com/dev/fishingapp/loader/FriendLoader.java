package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.FriendsResponse;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 4/20/2016.
 */
public class FriendLoader extends AbstractLoader<FriendsResponse> {

    String searcby;
    public FriendLoader(String searchby) {
        super(FishingApplication.getContext());
        this.searcby=searchby;
    }

    @Override
    protected FriendsResponse doLoadInBackground() {
        FriendsResponse response = getService().getAllfriends("application/x-www-form-urlencoded", "application/json", AppConstants.FRIENDS_API_ACTION, searcby);
        return response;
    }
}
