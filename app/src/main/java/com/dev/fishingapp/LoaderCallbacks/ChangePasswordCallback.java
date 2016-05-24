package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.ChangePassword;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.ChangePasswordLoader;
import com.dev.fishingapp.util.AppConstants;

import retrofit.RetrofitError;

/**
 * Created by user on 5/2/2016.
 */
public class ChangePasswordCallback extends ChangePasswordLoader.AbstractLoaderCallbacks<ChangePassword> {
    private String oldPassword;
    private String newPassword;
    private String uid;
    private AppCompatActivity abstractActivity;

    public ChangePasswordCallback(AppCompatActivity activity, boolean showProgressDialog,String oldPassword, String newPassword,String userid) {
        super(activity, showProgressDialog);
        this.abstractActivity=activity;
        this.oldPassword=oldPassword;
        this.newPassword=newPassword;
        this.uid=userid;

    }

    @Override
    public void onResponse(AbstractLoader<ChangePassword> loader, ChangePassword response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.CHANGE_PWD_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<ChangePassword> onCreateLoader(int id, Bundle args) {
        return new ChangePasswordLoader(uid,oldPassword,newPassword);

    }
    @Override
    public boolean onError(AbstractLoader<ChangePassword> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}
