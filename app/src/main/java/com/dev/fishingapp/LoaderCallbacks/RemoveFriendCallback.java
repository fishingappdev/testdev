package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.AddRemoveFriendResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.AddFriendLoader;
import com.dev.fishingapp.loader.RemoveFriendLoader;
import com.dev.fishingapp.util.AppConstants;

import retrofit.RetrofitError;

/**
 * Created by user on 4/20/2016.
 */
public class RemoveFriendCallback extends AddFriendLoader.AbstractLoaderCallbacks<AddRemoveFriendResponse> {
    private String myuid, frienduid;
    private AppCompatActivity abstractActivity;

    public RemoveFriendCallback(AppCompatActivity activity, boolean showProgressDialog, String myuid, String frienduid) {
        super(activity, showProgressDialog);
        this.abstractActivity = activity;
        this.myuid = myuid;
        this.frienduid = frienduid;
    }

    @Override
    public void onResponse(AbstractLoader<AddRemoveFriendResponse> loader, AddRemoveFriendResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.REMOVE_FRIEND_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<AddRemoveFriendResponse> onCreateLoader(int id, Bundle args) {
        return new RemoveFriendLoader(myuid, frienduid);

    }

    @Override
    public boolean onError(AbstractLoader<AddRemoveFriendResponse> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}