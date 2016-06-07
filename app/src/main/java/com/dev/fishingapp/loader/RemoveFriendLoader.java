package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.AddRemoveFriendResponse;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 4/20/2016.
 */
public class RemoveFriendLoader extends AbstractLoader<AddRemoveFriendResponse> {

    String myuid, frienduid;
    public RemoveFriendLoader(String myuid, String frienduid) {
        super(FishingApplication.getContext());
        this.myuid=myuid;
        this.frienduid=frienduid;
    }

    @Override
    protected AddRemoveFriendResponse doLoadInBackground() {
        AddRemoveFriendResponse response = getService().removeFriend("application/x-www-form-urlencoded", "application/json", AppConstants.REMOVE_FRIEND_API_ACTION, myuid, frienduid);
        return response;
    }
}
