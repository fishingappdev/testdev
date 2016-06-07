package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.UpdateNewAlbumResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.AddNewAlbumLoader;
import com.dev.fishingapp.loader.UpdateAlbumLoader;
import com.dev.fishingapp.util.AppConstants;

import java.io.File;

import retrofit.RetrofitError;

/**
 * Created by user on 4/20/2016.
 */
public class UpdateAlbumCallback extends AddNewAlbumLoader.AbstractLoaderCallbacks<UpdateNewAlbumResponse> {
    private String nid;
    private File image;
    private AppCompatActivity abstractActivity;

    public UpdateAlbumCallback(AppCompatActivity activity, boolean showProgressDialog, String nid, File image) {
        super(activity, showProgressDialog);
        this.abstractActivity = activity;
        this.nid = nid;
        this.image = image;
    }

    @Override
    public void onResponse(AbstractLoader<UpdateNewAlbumResponse> loader, UpdateNewAlbumResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.UPDATE_ALBUM_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<UpdateNewAlbumResponse> onCreateLoader(int id, Bundle args) {
        return new UpdateAlbumLoader(nid, image);

    }

    @Override
    public boolean onError(AbstractLoader<UpdateNewAlbumResponse> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}