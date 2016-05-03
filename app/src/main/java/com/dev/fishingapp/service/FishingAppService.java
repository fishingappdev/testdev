package com.dev.fishingapp.service;

import com.dev.fishingapp.data.model.ChangePassword;
import com.dev.fishingapp.data.model.ChangePasswordRequest;
import com.dev.fishingapp.data.model.FacebookLoginRequest;
import com.dev.fishingapp.data.model.FacebookResponse;
import com.dev.fishingapp.data.model.ForgotPassword;
import com.dev.fishingapp.data.model.ForgotPasswordRequest;
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

    @POST("/register.json")
    SignUpResponse signUp(@Header("Content-Type") String contentType, @Header("Accept") String accept, @Body SignUpRequest signUpRequest);

    @POST("/change_password.json")
    ChangePassword changePassword(@Header("Content-Type")String contentType,@Header("Accept") String accept,@Body ChangePasswordRequest mChangePasswordRequest);

    @POST("/forgot_password.json")
    ForgotPassword forgotPassword(@Header("Content-Type")String contentType,@Header("Accept") String accept,@Body ForgotPasswordRequest mForgotPasswordRequest);

    @POST("/fb_login_detail.json")
    FacebookResponse facebookLogin(@Header("Content-Type")String contentType,@Header("Accept") String accept,@Body FacebookLoginRequest mFbLogindRequest);

}
