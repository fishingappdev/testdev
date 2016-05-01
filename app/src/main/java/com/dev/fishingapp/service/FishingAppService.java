package com.dev.fishingapp.service;

import com.dev.fishingapp.data.model.Login;
import com.dev.fishingapp.data.model.LoginRequest;

import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by skumari on 4/19/2016.
 */
public interface FishingAppService {
    @POST("/login.json")
    Login login(@Header("Content-Type") String contentType,@Header("Accept") String accept,@Body LoginRequest mLoginRequest);

}
