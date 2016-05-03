package com.dev.fishingapp.service;

import com.dev.fishingapp.data.model.Login;
import com.dev.fishingapp.data.model.LoginRequest;
import com.dev.fishingapp.data.model.SignUpRequest;
import com.dev.fishingapp.data.model.SignUpResponse;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by skumari on 4/19/2016.
 */
public interface FishingAppService {
    @POST("/login.json")
    Login login(@Header("Content-Type") String contentType,@Header("Accept") String accept,@Body LoginRequest mLoginRequest);

    @GET("/loginCheck")
    Login login(@Query("username") String username, @Query("password") String password);

    @POST("/register.json")
    SignUpResponse signUp(@Header("Content-Type") String contentType, @Header("Accept") String accept, @Body SignUpRequest signUpRequest);
}
