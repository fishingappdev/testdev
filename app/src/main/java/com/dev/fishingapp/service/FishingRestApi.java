package com.dev.fishingapp.service;

import android.content.Context;

import com.dev.fishingapp.BuildConfig;
import com.dev.fishingapp.R;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by skumari on 4/19/2016.
 */
public class FishingRestApi {
    Context mContext;
    private static RestAdapter mRestAdapter;
    private FishingAppService mFishingAppService;
    private static boolean fbRequest;

    public FishingRestApi(final Context context) {
        mContext = context;
        String baseUrl = mContext.getString(R.string.api_fishingapp);
        if (mRestAdapter == null) {
            OkHttpClient okHttpClient = new OkHttpClient();



            mRestAdapter = new RestAdapter.Builder().setEndpoint(baseUrl)
                    .setConverter(new GsonConverter(new Gson()))
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestInterceptor.RequestFacade requestFacade) {
                            requestFacade.addHeader("Accept","application/json");
                            requestFacade.addHeader("Content-Type","application/json");
//                            String access_token = VirginPrefs.getInstance().getAccessToken();
//                            if (access_token != null && !isFbRequest())
//                                requestFacade.addQueryParam("access_token", VirginPrefs.getInstance().getAccessToken());
                        }
                    })
                    .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                    .setClient(new OkClient(okHttpClient))
                    .build();
        }
    }

    public static boolean isFbRequest() {
        return fbRequest;
    }

    public FishingAppService getService() {
        if (mFishingAppService == null) {
            mFishingAppService = mRestAdapter.create(FishingAppService.class);
        }
        return mFishingAppService;
    }

}
