package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.Login;
import com.dev.fishingapp.data.model.LoginRequest;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.LoginLoader;
import com.dev.fishingapp.util.AppConstants;

import retrofit.RetrofitError;

/**
 * Created by user on 4/20/2016.
 */
public class LoginCallback extends LoginLoader.AbstractLoaderCallbacks<Login> {
    private String username;
    private String password;
    private LoginRequest loginRequest;
    private AppCompatActivity abstractActivity;

    public LoginCallback(AppCompatActivity activity, boolean showProgressDialog,String username, String password) {
        super(activity, showProgressDialog);
        this.abstractActivity=activity;
        this.username=username;
        this.password=password;
        /*loginRequest=new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);*/
    }

    @Override
    public void onResponse(AbstractLoader<Login> loader, Login response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.LOGIN_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<Login> onCreateLoader(int id, Bundle args) {
        return new LoginLoader (username,password);

    }
    @Override
    public boolean onError(AbstractLoader<Login> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}