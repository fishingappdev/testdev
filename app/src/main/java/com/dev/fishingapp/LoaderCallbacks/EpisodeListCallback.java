package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.EpisodeListResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.EpisodeListLoader;
import com.dev.fishingapp.util.AppConstants;

import retrofit.RetrofitError;

/**
 * Created by user on 5/21/2016.
 */
public class EpisodeListCallback  extends EpisodeListLoader.AbstractLoaderCallbacks<EpisodeListResponse> {

    private AppCompatActivity abstractActivity;

    public EpisodeListCallback(AppCompatActivity activity, boolean showProgressDialog) {
        super(activity, showProgressDialog);
        this.abstractActivity=activity;

    }

    @Override
    public void onResponse(AbstractLoader<EpisodeListResponse> loader, EpisodeListResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.FORGOT_PWD_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<EpisodeListResponse> onCreateLoader(int id, Bundle args) {
        return new EpisodeListLoader();

    }
    @Override
    public boolean onError(AbstractLoader<EpisodeListResponse> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}

