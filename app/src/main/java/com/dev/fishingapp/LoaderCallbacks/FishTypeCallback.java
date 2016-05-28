package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.FishTypeResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.FishTypeLoader;
import com.dev.fishingapp.util.AppConstants;

import retrofit.RetrofitError;

/**
 * Created by user on 5/28/2016.
 */
public class FishTypeCallback extends FishTypeLoader.AbstractLoaderCallbacks<FishTypeResponse> {

    private AppCompatActivity abstractActivity;

    public FishTypeCallback(AppCompatActivity activity, boolean showProgressDialog) {
        super(activity, showProgressDialog);
        this.abstractActivity=activity;

    }

    @Override
    public void onResponse(AbstractLoader<FishTypeResponse> loader, FishTypeResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.FISH_TYPE_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<FishTypeResponse> onCreateLoader(int id, Bundle args) {
        return new FishTypeLoader();

    }
    @Override
    public boolean onError(AbstractLoader<FishTypeResponse> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}


