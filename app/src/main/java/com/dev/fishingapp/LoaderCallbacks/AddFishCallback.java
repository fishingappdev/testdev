package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.AddFishRequest;
import com.dev.fishingapp.data.model.AddFishResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.AddFishLoader;
import com.dev.fishingapp.util.AppConstants;

import retrofit.RetrofitError;

/**
 * Created by user on 5/28/2016.
 */
public class AddFishCallback extends AddFishLoader.AbstractLoaderCallbacks<AddFishResponse> {

    private AppCompatActivity abstractActivity;
    private AddFishRequest mRequest;

    public AddFishCallback(AppCompatActivity activity, boolean showProgressDialog,AddFishRequest mRequest) {
        super(activity, showProgressDialog);
        this.abstractActivity=activity;
        this.mRequest=mRequest;

    }

    @Override
    public void onResponse(AbstractLoader<AddFishResponse> loader, AddFishResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.ADD_FISH_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<AddFishResponse> onCreateLoader(int id, Bundle args) {
        return new AddFishLoader(mRequest);

    }
    @Override
    public boolean onError(AbstractLoader<AddFishResponse> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}