package com.dev.fishingapp.service;

import com.dev.fishingapp.data.model.ChangePassword;
import com.dev.fishingapp.data.model.ChangePasswordRequest;
import com.dev.fishingapp.data.model.FacebookLoginRequest;
import com.dev.fishingapp.data.model.FacebookResponse;
import com.dev.fishingapp.data.model.ForgotPassword;
import com.dev.fishingapp.data.model.ForgotPasswordRequest;
import com.dev.fishingapp.data.model.GetEditProfileRequest;
import com.dev.fishingapp.data.model.GetEditProfileResponse;
import com.dev.fishingapp.data.model.Login;
import com.dev.fishingapp.data.model.LoginRequest;
import com.dev.fishingapp.data.model.SetEditProfileRequest;
import com.dev.fishingapp.data.model.SetEditProfileResponse;
import com.dev.fishingapp.data.model.SignUpRequest;
import com.dev.fishingapp.data.model.SignUpResponse;

import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

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

    @POST("/get_user_detail_with_profile_detail.json")
    GetEditProfileResponse getProfile(@Header("Content-Type") String contentType, @Header("Accept") String accept, @Body GetEditProfileRequest getEditProfileRequest);

    @POST("/set_profile_detail.json")
    SetEditProfileResponse setProfile(@Header("Content-Type") String contentType, @Header("Accept") String accept, @Body SetEditProfileRequest setEditProfileRequest);

}
