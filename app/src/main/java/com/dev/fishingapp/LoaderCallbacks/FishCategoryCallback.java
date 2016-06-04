package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.MyFishResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.FishListCategoryLoader;
import com.dev.fishingapp.loader.MyFishLoader;
import com.dev.fishingapp.util.AppConstants;

import retrofit.RetrofitError;

/**
 * Created by user on 6/4/2016.
 */
public class FishCategoryCallback  extends MyFishLoader.AbstractLoaderCallbacks<MyFishResponse> {

    private AppCompatActivity abstractActivity;
    private String uid;
    private String catId;

    public FishCategoryCallback(AppCompatActivity activity, boolean showProgressDialog,String uid,String catId) {
        super(activity, showProgressDialog);
        this.abstractActivity=activity;
        this.uid=uid;
        this.catId=catId;

    }

    @Override
    public void onResponse(AbstractLoader<MyFishResponse> loader, MyFishResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.FISH_CATEGORY_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<MyFishResponse> onCreateLoader(int id, Bundle args) {
        return new FishListCategoryLoader(uid,catId);

    }
    @Override
    public boolean onError(AbstractLoader<MyFishResponse> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}

