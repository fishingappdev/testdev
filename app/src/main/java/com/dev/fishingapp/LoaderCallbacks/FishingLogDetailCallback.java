package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.FishingLogDetail;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.FishingDetailLoader;
import com.dev.fishingapp.loader.FishingLogDetailLoader;
import com.dev.fishingapp.util.AppConstants;

import retrofit.RetrofitError;

/**
 * Created by user on 5/26/2016.
 */
public class FishingLogDetailCallback extends FishingDetailLoader.AbstractLoaderCallbacks<FishingLogDetail> {

    private AppCompatActivity abstractActivity;
    private String nid;

    public FishingLogDetailCallback(AppCompatActivity activity, boolean showProgressDialog,String nid) {
        super(activity, showProgressDialog);
        this.abstractActivity=activity;
        this.nid=nid;

    }

    @Override
    public void onResponse(AbstractLoader<FishingLogDetail> loader, FishingLogDetail response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.FISHING_LOG_DETAIL_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<FishingLogDetail> onCreateLoader(int id, Bundle args) {
        return new FishingLogDetailLoader(nid);

    }
    @Override
    public boolean onError(AbstractLoader<FishingLogDetail> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}


