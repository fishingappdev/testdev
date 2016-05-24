package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.MyFishResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.MyFishLoader;
import com.dev.fishingapp.util.AppConstants;

import retrofit.RetrofitError;

/**
 * Created by user on 5/23/2016.
 */
public class MyFishCallback extends MyFishLoader.AbstractLoaderCallbacks<MyFishResponse> {

    private AppCompatActivity abstractActivity;
    private String uid;

    public MyFishCallback(AppCompatActivity activity, boolean showProgressDialog,String uid) {
        super(activity, showProgressDialog);
        this.abstractActivity=activity;
        this.uid=uid;

    }

    @Override
    public void onResponse(AbstractLoader<MyFishResponse> loader, MyFishResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.MYFISH_LIST_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<MyFishResponse> onCreateLoader(int id, Bundle args) {
        return new MyFishLoader(uid);

    }
    @Override
    public boolean onError(AbstractLoader<MyFishResponse> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}
