package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.FriendsResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.FriendLoader;
import com.dev.fishingapp.util.AppConstants;

import retrofit.RetrofitError;

/**
 * Created by user on 4/20/2016.
 */
public class FriendsCallback extends FriendLoader.AbstractLoaderCallbacks<FriendsResponse> {
    private String searchby;
    private String uid;
    private boolean find_friends;
    private AppCompatActivity abstractActivity;

    public FriendsCallback(AppCompatActivity activity, boolean showProgressDialog, String searchby, String uid, boolean find_friends) {
        super(activity, showProgressDialog);
        this.abstractActivity = activity;
        this.searchby = searchby;
        this.uid = uid;
        this.find_friends = find_friends;
    }

    @Override
    public void onResponse(AbstractLoader<FriendsResponse> loader, FriendsResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.FRIEND_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<FriendsResponse> onCreateLoader(int id, Bundle args) {
        return new FriendLoader(searchby, uid, find_friends);

    }

    @Override
    public boolean onError(AbstractLoader<FriendsResponse> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}