package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.AddNewAlbumRequest;
import com.dev.fishingapp.data.model.AddNewAlbumResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.AddNewAlbumLoader;
import com.dev.fishingapp.util.AppConstants;

import retrofit.RetrofitError;

/**
 * Created by user on 4/20/2016.
 */
public class AddNewAlbumCallback extends AddNewAlbumLoader.AbstractLoaderCallbacks<AddNewAlbumResponse> {
    private AddNewAlbumRequest addNewAlbumRequest;
    private AppCompatActivity abstractActivity;

    public AddNewAlbumCallback(AppCompatActivity activity, boolean showProgressDialog, AddNewAlbumRequest addNewAlbumRequest) {
        super(activity, showProgressDialog);
        this.abstractActivity=activity;
        this.addNewAlbumRequest=addNewAlbumRequest;
    }

    @Override
    public void onResponse(AbstractLoader<AddNewAlbumResponse> loader, AddNewAlbumResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.ADD_ALBUM_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<AddNewAlbumResponse> onCreateLoader(int id, Bundle args) {
        return new AddNewAlbumLoader(addNewAlbumRequest);

    }
    @Override
    public boolean onError(AbstractLoader<AddNewAlbumResponse> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}