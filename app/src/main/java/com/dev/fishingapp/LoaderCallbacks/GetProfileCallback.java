package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.dev.fishingapp.AbstractActivity;
import com.dev.fishingapp.data.model.GetEditProfileRequest;
import com.dev.fishingapp.data.model.GetEditProfileResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.GetProfileLoader;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 4/20/2016.
 */
public class GetProfileCallback extends GetProfileLoader.AbstractLoaderCallbacks<GetEditProfileResponse> {
    private GetEditProfileRequest getEditProfileRequest;
    private AbstractActivity activity;

    public GetProfileCallback(AbstractActivity activity, boolean showProgressDialog, GetEditProfileRequest getEditProfileRequest) {
        super(activity, showProgressDialog);
        this.getEditProfileRequest = getEditProfileRequest;
        this.activity = activity;
    }

    @Override
    public void onResponse(AbstractLoader<GetEditProfileResponse> loader, GetEditProfileResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.GET_PROFILE_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
    }

    @Override
    public Loader<GetEditProfileResponse> onCreateLoader(int id, Bundle args) {
        return new GetProfileLoader(getEditProfileRequest);

    }
}