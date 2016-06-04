package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.AddFishResponse;
import com.dev.fishingapp.data.model.AddLogRequest;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.AddLogLoader;
import com.dev.fishingapp.util.AppConstants;

import retrofit.RetrofitError;

/**
 * Created by user on 5/31/2016.
 */
public class AddLogCallback extends AddLogLoader.AbstractLoaderCallbacks<AddFishResponse> {

    private AppCompatActivity abstractActivity;
    private AddLogRequest mRequest;
    private String uid;

    public AddLogCallback(AppCompatActivity activity, boolean showProgressDialog,AddLogRequest mRequest,String Uid) {
        super(activity, showProgressDialog);
        this.abstractActivity=activity;
        this.mRequest=mRequest;
        this.uid=Uid;

    }

    @Override
    public void onResponse(AbstractLoader<AddFishResponse> loader, AddFishResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.ADD_FISHING_LOG_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<AddFishResponse> onCreateLoader(int id, Bundle args) {
        return new AddLogLoader(mRequest,uid);

    }
    @Override
    public boolean onError(AbstractLoader<AddFishResponse> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}