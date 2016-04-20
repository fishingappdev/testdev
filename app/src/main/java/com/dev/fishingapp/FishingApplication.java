package com.dev.fishingapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by user on 4/20/2016.
 */
public class FishingApplication extends Application {
    private static Context mContext = null;

    static public Context getContext() {
        return mContext;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        FishingApplication fishingApplication = this;
    }


}
