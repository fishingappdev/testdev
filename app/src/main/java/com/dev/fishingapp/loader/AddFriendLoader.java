package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.AddFriendResponse;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 4/20/2016.
 */
public class AddFriendLoader extends AbstractLoader<AddFriendResponse> {

    String myuid, frienduid;
    public AddFriendLoader(String myuid, String frienduid) {
        super(FishingApplication.getContext());
        this.myuid=myuid;
        this.frienduid=frienduid;
    }

    @Override
    protected AddFriendResponse doLoadInBackground() {
        AddFriendResponse response = getService().addFriend("application/x-www-form-urlencoded", "application/json", AppConstants.ADD_FRIEND_API_ACTION, myuid, frienduid);
        return response;
    }
}
