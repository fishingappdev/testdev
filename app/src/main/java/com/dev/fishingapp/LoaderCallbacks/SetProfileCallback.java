package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.dev.fishingapp.AbstractActivity;
import com.dev.fishingapp.data.model.SetEditProfileRequest;
import com.dev.fishingapp.data.model.SetEditProfileResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.SetProfileLoader;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 4/20/2016.
 */
public class SetProfileCallback extends SetProfileLoader.AbstractLoaderCallbacks<SetEditProfileResponse> {
    private SetEditProfileRequest setEditProfileRequest;
    private AbstractActivity activity;

    public SetProfileCallback(AbstractActivity activity, boolean showProgressDialog, SetEditProfileRequest setEditProfileRequest) {
        super(activity, showProgressDialog);
        this.setEditProfileRequest = setEditProfileRequest;
        this.activity = activity;
    }

    @Override
    public void onResponse(AbstractLoader<SetEditProfileResponse> loader, SetEditProfileResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.SET_PROFILE_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
    }

    @Override
    public Loader<SetEditProfileResponse> onCreateLoader(int id, Bundle args) {
        return new SetProfileLoader(setEditProfileRequest);

    }
}