package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.FacebookLoginRequest;
import com.dev.fishingapp.data.model.FacebookResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.FacebookLoginLoader;
import com.dev.fishingapp.util.AppConstants;

import retrofit.RetrofitError;

/**
 * Created by user on 5/3/2016.
 */
public class FacebookLoginCallback extends FacebookLoginLoader.AbstractLoaderCallbacks<FacebookResponse> {

    private FacebookLoginRequest mRequest;
    private AppCompatActivity abstractActivity;

    public FacebookLoginCallback(AppCompatActivity activity, boolean showProgressDialog,String email,String firstname, String country) {
        super(activity, showProgressDialog);
        this.abstractActivity=activity;
        mRequest=new FacebookLoginRequest();
        mRequest.setEmail(email);
        mRequest.setFirstname(firstname);
        mRequest.setCountry(country);

    }

    @Override
    public void onResponse(AbstractLoader<FacebookResponse> loader, FacebookResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.FACEBOOK_LOGIN_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<FacebookResponse> onCreateLoader(int id, Bundle args) {
        return new FacebookLoginLoader(mRequest);

    }
    @Override
    public boolean onError(AbstractLoader<FacebookResponse> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}


