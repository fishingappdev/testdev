package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.MyAlbumDetailResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.MyAlbumDetailLoader;
import com.dev.fishingapp.util.AppConstants;

import retrofit.RetrofitError;

/**
 * Created by user on 4/20/2016.
 */
public class MyAlbumDetailCallback extends MyAlbumDetailLoader.AbstractLoaderCallbacks<MyAlbumDetailResponse> {
    private String nid;
    private AppCompatActivity abstractActivity;

    public MyAlbumDetailCallback(AppCompatActivity activity, boolean showProgressDialog, String nid) {
        super(activity, showProgressDialog);
        this.abstractActivity=activity;
        this.nid=nid;
    }

    @Override
    public void onResponse(AbstractLoader<MyAlbumDetailResponse> loader, MyAlbumDetailResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.MY_ALBUM_DETAIL_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<MyAlbumDetailResponse> onCreateLoader(int id, Bundle args) {
        return new MyAlbumDetailLoader(nid);

    }
    @Override
    public boolean onError(AbstractLoader<MyAlbumDetailResponse> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}