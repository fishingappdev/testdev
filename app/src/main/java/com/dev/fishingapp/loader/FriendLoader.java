package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.FriendsResponse;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 4/20/2016.
 */
public class FriendLoader extends AbstractLoader<FriendsResponse> {

    String searcby, uid;
    boolean find_friends;

    public FriendLoader(String searchby, String uid, boolean find_friends) {
        super(FishingApplication.getContext());
        this.searcby = searchby;
        this.uid = uid;
        this.find_friends = find_friends;
    }

    @Override
    protected FriendsResponse doLoadInBackground() {
        FriendsResponse response;
        if (find_friends)
            response = getService().getAllfriends("application/x-www-form-urlencoded", "application/json", AppConstants.FRIENDS_API_ACTION, searcby, uid);
        else
            response = getService().getMyfriends("application/x-www-form-urlencoded", "application/json", AppConstants.MY_FRIENDS_API_ACTION, searcby, uid);

        return response;
    }
}
