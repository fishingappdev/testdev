package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.ForgotPassword;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.ForgotPasswordLoader;
import com.dev.fishingapp.util.AppConstants;

import retrofit.RetrofitError;

/**
 * Created by user on 5/3/2016.
 */
public class ForgotPasswordCallback extends ForgotPasswordLoader.AbstractLoaderCallbacks<ForgotPassword> {

    private AppCompatActivity abstractActivity;
    private String email;

    public ForgotPasswordCallback(AppCompatActivity activity, boolean showProgressDialog,String email) {
        super(activity, showProgressDialog);
        this.abstractActivity=activity;
       this.email=email;

    }

    @Override
    public void onResponse(AbstractLoader<ForgotPassword> loader, ForgotPassword response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.FORGOT_PWD_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<ForgotPassword> onCreateLoader(int id, Bundle args) {
        return new ForgotPasswordLoader(email);

    }
    @Override
    public boolean onError(AbstractLoader<ForgotPassword> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}

