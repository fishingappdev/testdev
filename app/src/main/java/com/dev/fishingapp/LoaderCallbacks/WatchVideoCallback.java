package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.dev.fishingapp.AbstractActivity;
import com.dev.fishingapp.data.model.WatchVideoRequest;
import com.dev.fishingapp.data.model.WatchVideoResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.WatchVideoLoader;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 4/20/2016.
 */
public class WatchVideoCallback extends WatchVideoLoader.AbstractLoaderCallbacks<WatchVideoResponse> {
    private WatchVideoRequest watchVideoRequest;
    private AbstractActivity activity;

    public WatchVideoCallback(AbstractActivity activity, boolean showProgressDialog, WatchVideoRequest watchVideoRequest) {
        super(activity, showProgressDialog);
        this.watchVideoRequest = watchVideoRequest;
        this.activity = activity;
    }

    @Override
    public void onResponse(AbstractLoader<WatchVideoResponse> loader, WatchVideoResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.WATCH_VIDEO_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
    }

    @Override
    public Loader<WatchVideoResponse> onCreateLoader(int id, Bundle args) {
        return new WatchVideoLoader(watchVideoRequest);

    }
}