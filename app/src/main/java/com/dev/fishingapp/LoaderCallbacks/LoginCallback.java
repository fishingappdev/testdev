package com.dev.fishingapp.LoaderCallbacks;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.Login;
import com.dev.fishingapp.data.model.LoginRequest;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.LoginLoader;

import retrofit.RetrofitError;

/**
 * Created by user on 4/20/2016.
 */
public class LoginCallback extends LoginLoader.AbstractLoaderCallbacks<Login> {
    private String username;
    private String password;
    private LoginRequest loginRequest;
    public LoginCallback(AppCompatActivity activity, boolean showProgressDialog,String username, String password) {
        super(activity, showProgressDialog);
        loginRequest=new LoginRequest();
        loginRequest.setEmail(username);
        loginRequest.setPassword(password);
    }

    @Override
    public void onResponse(AbstractLoader<Login> loader, Login response) {
        Log.d("on response", ">>>>");
    }

    @Override
    public Loader<Login> onCreateLoader(int id, Bundle args) {
        return new LoginLoader (loginRequest);

    }
    @Override
    public boolean onError(AbstractLoader<Login> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}