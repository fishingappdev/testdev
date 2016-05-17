package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.MyAlbumResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.MyAlbumLoader;
import com.dev.fishingapp.util.AppConstants;

import retrofit.RetrofitError;

/**
 * Created by user on 4/20/2016.
 */
public class MyAlbumCallback extends MyAlbumLoader.AbstractLoaderCallbacks<MyAlbumResponse> {
    private String userid;
    private AppCompatActivity abstractActivity;

    public MyAlbumCallback(AppCompatActivity activity, boolean showProgressDialog, String userid) {
        super(activity, showProgressDialog);
        this.abstractActivity=activity;
        this.userid=userid;
    }

    @Override
    public void onResponse(AbstractLoader<MyAlbumResponse> loader, MyAlbumResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.MY_ALBUM_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<MyAlbumResponse> onCreateLoader(int id, Bundle args) {
        return new MyAlbumLoader(userid);

    }
    @Override
    public boolean onError(AbstractLoader<MyAlbumResponse> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}