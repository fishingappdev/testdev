package com.dev.fishingapp.service;

import com.dev.fishingapp.data.model.ChangePassword;
import com.dev.fishingapp.data.model.ChangePasswordRequest;
import com.dev.fishingapp.data.model.FacebookLoginRequest;
import com.dev.fishingapp.data.model.FacebookResponse;
import com.dev.fishingapp.data.model.ForgotPassword;
import com.dev.fishingapp.data.model.GetEditProfileRequest;
import com.dev.fishingapp.data.model.GetEditProfileResponse;
import com.dev.fishingapp.data.model.Login;
import com.dev.fishingapp.data.model.SetEditProfileRequest;
import com.dev.fishingapp.data.model.SetEditProfileResponse;
import com.dev.fishingapp.data.model.SignUpRequest;
import com.dev.fishingapp.data.model.SignUpResponse;
import com.dev.fishingapp.data.model.WatchVideoRequest;
import com.dev.fishingapp.data.model.WatchVideoResponse;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by skumari on 4/19/2016.
 */
public interface FishingAppService {
    @FormUrlEncoded
    @POST("/userlogin")
    Login login(@Header("Content-Type") String contentType,@Header("Accept") String accept,@Field("apiaction") String apiaction ,@Field("username") String username,@Field("password") String password);

    @POST("/register.json")
    SignUpResponse signUp(@Header("Content-Type") String contentType, @Header("Accept") String accept, @Body SignUpRequest signUpRequest);

    @POST("/change_password.json")
    ChangePassword changePassword(@Header("Content-Type")String contentType,@Header("Accept") String accept,@Body ChangePasswordRequest mChangePasswordRequest);

    @FormUrlEncoded
    @POST("/forgotpassword")
    ForgotPassword forgotPassword(@Header("Content-Type")String contentType,@Header("Accept") String accept,@Field("apiaction") String apiaction, @Field("email") String email);

    @POST("/fb_login_detail.json")
    FacebookResponse facebookLogin(@Header("Content-Type")String contentType,@Header("Accept") String accept,@Body FacebookLoginRequest mFbLogindRequest);

    @POST("/get_user_detail_with_profile_detail.json")
    GetEditProfileResponse getProfile(@Header("Content-Type") String contentType, @Header("Accept") String accept, @Body GetEditProfileRequest getEditProfileRequest);

    @POST("/set_profile_detail.json")
    SetEditProfileResponse setProfile(@Header("Content-Type") String contentType, @Header("Accept") String accept, @Body SetEditProfileRequest setEditProfileRequest);

    @POST("/watch_video.json")
    WatchVideoResponse watchVideo(@Header("Content-Type") String contentType, @Header("Accept") String accept, @Body WatchVideoRequest watchVideoRequest);

}
