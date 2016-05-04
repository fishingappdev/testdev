package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.dev.fishingapp.AbstractActivity;
import com.dev.fishingapp.data.model.SignUpRequest;
import com.dev.fishingapp.data.model.SignUpResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.SignUpLoader;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 4/20/2016.
 */
public class SignUpCallback extends SignUpLoader.AbstractLoaderCallbacks<SignUpResponse> {
    private SignUpRequest signUpRequest;
    private AbstractActivity activity;

    public SignUpCallback(AbstractActivity activity, boolean showProgressDialog, SignUpRequest signUpRequest) {
        super(activity, showProgressDialog);
        this.signUpRequest = signUpRequest;
        this.activity = activity;
    }

    @Override
    public void onResponse(AbstractLoader<SignUpResponse> loader, SignUpResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.SIGNUP_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
    }

    @Override
    public Loader<SignUpResponse> onCreateLoader(int id, Bundle args) {
        return new SignUpLoader(signUpRequest);

    }
}