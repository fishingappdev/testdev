package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.AddFishResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.UpdateProfielPicLoader;
import com.dev.fishingapp.util.AppConstants;

import java.io.File;

import retrofit.RetrofitError;

/**
 * Created by user on 6/1/2016.
 */
public class UpdateProfilePicCallback extends UpdateProfielPicLoader.AbstractLoaderCallbacks<AddFishResponse> {

    private AppCompatActivity abstractActivity;
    File image;

    public UpdateProfilePicCallback(AppCompatActivity activity, boolean showProgressDialog,File image) {
        super(activity, showProgressDialog);
        this.abstractActivity=activity;
        this.image=image;

    }

    @Override
    public void onResponse(AbstractLoader<AddFishResponse> loader, AddFishResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.UPDATE_PROFILE_PIC_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<AddFishResponse> onCreateLoader(int id, Bundle args) {
        return new UpdateProfielPicLoader(image);

    }
    @Override
    public boolean onError(AbstractLoader<AddFishResponse> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}
