package com.dev.fishingapp.LoaderCallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dev.fishingapp.data.model.AddFishResponse;
import com.dev.fishingapp.loader.AbstractLoader;
import com.dev.fishingapp.loader.AddCommentLoader;
import com.dev.fishingapp.util.AppConstants;

import retrofit.RetrofitError;

/**
 * Created by user on 6/1/2016.
 */
public class AddCommentCallback extends AddCommentLoader.AbstractLoaderCallbacks<AddFishResponse> {
    private AppCompatActivity abstractActivity;
    private String uid;
    private String subject;
    private String commentBody;
    private String nid;

    public AddCommentCallback(AppCompatActivity activity, boolean showProgressDialog,String Uid, String subject, String commentBody, String nid) {
        super(activity, showProgressDialog);
        this.abstractActivity=activity;
        this.uid=Uid;
        this.subject=subject;
        this.commentBody=commentBody;
        this.nid=nid;

    }

    @Override
    public void onResponse(AbstractLoader<AddFishResponse> loader, AddFishResponse response) {
        Log.d("on response", ">>>>");
        Intent intent = new Intent(AppConstants.ADD_COMMENT_CALLBACK_BROADCAST);
        intent.putExtra("data", response);
        LocalBroadcastManager.getInstance(abstractActivity).sendBroadcast(intent);
    }

    @Override
    public Loader<AddFishResponse> onCreateLoader(int id, Bundle args) {
        return new AddCommentLoader(uid,subject,commentBody,nid);

    }
    @Override
    public boolean onError(AbstractLoader<AddFishResponse> loader, RetrofitError error) {
        return super.onError(loader, error);
    }
}