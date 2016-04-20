package com.dev.fishingapp.LoaderCallbacks;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.Login;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.LoginLoader;

/**
 * Created by user on 4/20/2016.
 */
public class LoginCallback extends LoginLoader.AbstractLoaderCallbacks<Login> {
    private String username;
    private String password;
    public LoginCallback(AppCompatActivity activity, boolean showProgressDialog,String username, String password) {
        super(activity, showProgressDialog);
        this.username=username;
        this.password=password;
    }

    @Override
    public void onResponse(AbstractLoader<Login> loader, Login response) {
       Log.d("on response",">>>>");
    }

    @Override
    public Loader<Login> onCreateLoader(int id, Bundle args) {
        return new LoginLoader(username, password);

    }
}