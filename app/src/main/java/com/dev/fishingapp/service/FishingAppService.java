package com.dev.fishingapp.service;

import com.dev.fishingapp.data.model.Login;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by skumari on 4/19/2016.
 */
public interface FishingAppService {
    @GET("/loginCheck")
    Login login(@Query("username") String username, @Query("password") String password);
}
