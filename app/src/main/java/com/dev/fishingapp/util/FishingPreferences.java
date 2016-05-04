package com.dev.fishingapp.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.R;

/**
 * Created by Karan on 18/3/15.
 */
public class FishingPreferences {

    private static final String TAG_CURRENT_USER_ID = "current_user_id";
    private static final String IS_SOCIAL_LOGIN = "is_social_login";

    private static SharedPreferences mPreferences;
    private static FishingPreferences mInstance;
    private static SharedPreferences.Editor mEditor;

    /*
    *return instance of FishingPreferences
     */
    @SuppressLint("CommitPrefEdits")
    public static FishingPreferences getInstance() {
        if (mInstance == null || mPreferences == null) {
            mInstance = null;
            Context mContext = FishingApplication.getContext();
            mInstance = new FishingPreferences();
            mPreferences = mContext.getSharedPreferences(mContext.getResources().getString(R.string.preference), Context.MODE_PRIVATE);
            mEditor = mPreferences.edit();
        }
        return mInstance;
    }


    //get current ccount user id
    public String getCurrentUserId() {
        String currentUserId = "";
        if (mPreferences.contains(TAG_CURRENT_USER_ID)) {
            currentUserId = mPreferences.getString(TAG_CURRENT_USER_ID, "");
        }
        return currentUserId;

    }

    // save current account user id
    public void saveCurrentUserId(String currentUserId) {
        mEditor.putString(TAG_CURRENT_USER_ID, currentUserId);
        mEditor.apply();
    }

    public boolean getIsSocailLogin(){
        boolean isSocialLogin=false;
        if(mPreferences.contains(IS_SOCIAL_LOGIN)){
            isSocialLogin=mPreferences.getBoolean(IS_SOCIAL_LOGIN,false);
        }
        return isSocialLogin;
    }
    public void setIsSocialLogin(boolean isSocialLogin){
        mEditor.putBoolean(IS_SOCIAL_LOGIN,isSocialLogin);
        mEditor.apply();
    }

}
