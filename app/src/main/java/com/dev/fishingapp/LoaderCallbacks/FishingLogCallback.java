package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.FishingLogResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.FishingLogLoader;
import com.dev.fishingapp.util.AppConstants;

import retrofit.RetrofitError;

/**
 * Created by user on 5/25/2016.
 */
public class FishingLogCallback  extends FishingLogLoader.AbstractLoaderCallbacks<FishingLogResponse> {

    private AppCompatActivity abstractActivity;
    private String uid;

    public FishingLogCallback(AppCompatActivity activity, boolean showProgressDialog, String uid) {
        super(activity, showProgressDialog);
        this.abstractActivity=activity;
        this.uid=uid;

    }

    @Override
    public void onResponse(AbstractLoader<FishingLogResponse> loader, FishingLogResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.FISHING_LOG_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<FishingLogResponse> onCreateLoader(int id, Bundle args) {
        return new FishingLogLoader(uid);

    }
    @Override
    public boolean onError(AbstractLoader<FishingLogResponse> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}


