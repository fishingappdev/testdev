package com.dev.fishingapp.LoaderCallbacks;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.SignUpRequest;
import com.dev.fishingapp.data.model.SignUpResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.LoginLoader;
import com.dev.fishingapp.loader.SignUpLoader;

/**
 * Created by user on 4/20/2016.
 */
public class SignUpCallback extends LoginLoader.AbstractLoaderCallbacks<SignUpResponse> {
    private SignUpRequest signUpRequest;
    public SignUpCallback(AppCompatActivity activity, boolean showProgressDialog, SignUpRequest signUpRequest) {
        super(activity, showProgressDialog);
        this.signUpRequest=signUpRequest;
    }

    @Override
    public void onResponse(AbstractLoader<SignUpResponse> loader, SignUpResponse response) {
       Log.d("on response",">>>>");
    }

    @Override
    public Loader<SignUpResponse> onCreateLoader(int id, Bundle args) {
        return new SignUpLoader(signUpRequest);

    }
}