package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.AddFriendResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.AddFriendLoader;
import com.dev.fishingapp.util.AppConstants;

import retrofit.RetrofitError;

/**
 * Created by user on 4/20/2016.
 */
public class AddFriendCallback extends AddFriendLoader.AbstractLoaderCallbacks<AddFriendResponse> {
    private String myuid, frienduid;
    private AppCompatActivity abstractActivity;

    public AddFriendCallback(AppCompatActivity activity, boolean showProgressDialog, String myuid, String frienduid) {
        super(activity, showProgressDialog);
        this.abstractActivity = activity;
        this.myuid = myuid;
        this.frienduid = frienduid;
    }

    @Override
    public void onResponse(AbstractLoader<AddFriendResponse> loader, AddFriendResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.ADD_FRIEND_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<AddFriendResponse> onCreateLoader(int id, Bundle args) {
        return new AddFriendLoader(myuid, frienduid);

    }

    @Override
    public boolean onError(AbstractLoader<AddFriendResponse> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}