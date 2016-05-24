package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.FishingRecordResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.FishingRecordLoader;
import com.dev.fishingapp.util.AppConstants;

import retrofit.RetrofitError;

/**
 * Created by user on 5/24/2016.
 */
public class FishingRecordCallback extends FishingRecordLoader.AbstractLoaderCallbacks<FishingRecordResponse> {

    private AppCompatActivity abstractActivity;

    public FishingRecordCallback(AppCompatActivity activity, boolean showProgressDialog) {
        super(activity, showProgressDialog);
        this.abstractActivity=activity;


    }

    @Override
    public void onResponse(AbstractLoader<FishingRecordResponse> loader, FishingRecordResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.FISHING_RECORD_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<FishingRecordResponse> onCreateLoader(int id, Bundle args) {
        return new FishingRecordLoader();

    }
    @Override
    public boolean onError(AbstractLoader<FishingRecordResponse> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}
