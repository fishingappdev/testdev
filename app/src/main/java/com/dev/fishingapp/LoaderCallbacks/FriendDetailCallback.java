package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.FriendDetailResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.FriendDetailLoader;
import com.dev.fishingapp.util.AppConstants;

import retrofit.RetrofitError;

/**
 * Created by user on 4/20/2016.
 */
public class FriendDetailCallback extends FriendDetailLoader.AbstractLoaderCallbacks<FriendDetailResponse> {
    private String uid;
    private AppCompatActivity abstractActivity;

    public FriendDetailCallback(AppCompatActivity activity, boolean showProgressDialog, String uid) {
        super(activity, showProgressDialog);
        this.abstractActivity=activity;
        this.uid=uid;
    }

    @Override
    public void onResponse(AbstractLoader<FriendDetailResponse> loader, FriendDetailResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.FRIEND_DETAIL_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<FriendDetailResponse> onCreateLoader(int id, Bundle args) {
        return new FriendDetailLoader(uid);

    }
    @Override
    public boolean onError(AbstractLoader<FriendDetailResponse> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}