package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.FishDetailResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.FishingDetailLoader;
import com.dev.fishingapp.util.AppConstants;

import retrofit.RetrofitError;

/**
 * Created by user on 5/23/2016.
 */
public class FishDetailCallback extends FishingDetailLoader.AbstractLoaderCallbacks<FishDetailResponse> {

    private AppCompatActivity abstractActivity;
    private String nid;

    public FishDetailCallback(AppCompatActivity activity, boolean showProgressDialog,String nid) {
        super(activity, showProgressDialog);
        this.abstractActivity=activity;
        this.nid=nid;

    }

    @Override
    public void onResponse(AbstractLoader<FishDetailResponse> loader, FishDetailResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.FISH_DETAIL_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<FishDetailResponse> onCreateLoader(int id, Bundle args) {
        return new FishingDetailLoader(nid);

    }
    @Override
    public boolean onError(AbstractLoader<FishDetailResponse> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}

