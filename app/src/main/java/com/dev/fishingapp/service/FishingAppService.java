package com.dev.fishingapp.service;

import com.dev.fishingapp.data.model.AddNewAlbumResponse;
import com.dev.fishingapp.data.model.ChangePassword;
import com.dev.fishingapp.data.model.EpisodeListResponse;
import com.dev.fishingapp.data.model.FacebookLoginRequest;
import com.dev.fishingapp.data.model.FacebookResponse;
import com.dev.fishingapp.data.model.FishDetailResponse;
import com.dev.fishingapp.data.model.FishingRecordResponse;
import com.dev.fishingapp.data.model.ForgotPassword;
import com.dev.fishingapp.data.model.GetEditProfileResponse;
import com.dev.fishingapp.data.model.Login;
import com.dev.fishingapp.data.model.MyAlbumDetailResponse;
import com.dev.fishingapp.data.model.MyAlbumResponse;
import com.dev.fishingapp.data.model.MyFishResponse;
import com.dev.fishingapp.data.model.SetEditProfileResponse;
import com.dev.fishingapp.data.model.SignUpResponse;
import com.dev.fishingapp.data.model.WatchVideoRequest;
import com.dev.fishingapp.data.model.WatchVideoResponse;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by skumari on 4/19/2016.
 */
public interface FishingAppService {
    @FormUrlEncoded
    @POST("/userlogin")
    Login login(@Header("Content-Type") String contentType, @Header("Accept") String accept, @Field("apiaction") String apiaction, @Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("/usersignup")
    SignUpResponse signUp(@Header("Content-Type") String contentType, @Header("Accept") String accept, @Field("apiaction") String apiaction, @Field("username") String username, @Field("email") String email, @Field("firstname") String firstname, @Field("lastname") String lastname, @Field("country") String country);

<<<<<<< HEAD
    @FormUrlEncoded
    @POST("/changepassword")
    ChangePassword changePassword(@Header("Content-Type")String contentType,@Header("Accept") String accept,@Field("apiaction")String apiaction, @Field("uid") String uid,@Field("oldpassword") String oldPassword, @Field("newpassword") String newPassword);
=======
    @POST("/change_password.json")
    ChangePassword changePassword(@Header("Content-Type") String contentType, @Header("Accept") String accept, @Body ChangePasswordRequest mChangePasswordRequest);
>>>>>>> AddAlbum:Image Integrated

    @FormUrlEncoded
    @POST("/forgotpassword")
    ForgotPassword forgotPassword(@Header("Content-Type") String contentType, @Header("Accept") String accept, @Field("apiaction") String apiaction, @Field("email") String email);

    @POST("/fb_login_detail.json")
    FacebookResponse facebookLogin(@Header("Content-Type") String contentType, @Header("Accept") String accept, @Body FacebookLoginRequest mFbLogindRequest);

    @FormUrlEncoded
    @POST("/usereprofiledata")
    GetEditProfileResponse getProfile(@Header("Content-Type") String contentType, @Header("Accept") String accept, @Field("apiaction") String apiaction, @Field("uid") String uid);

    @FormUrlEncoded
    @POST("/usereditprofile")
    SetEditProfileResponse setProfile(@Header("Content-Type") String contentType, @Header("Accept") String accept, @Field("apiaction") String apiaction, @Field("uid") String uid, @Field("aboutme") String aboutme, @Field("fishingpre") String fishingpre);

    @POST("/watch_video.json")
    WatchVideoResponse watchVideo(@Header("Content-Type") String contentType, @Header("Accept") String accept, @Body WatchVideoRequest watchVideoRequest);

    @FormUrlEncoded
    @POST("/myalbums")
    MyAlbumResponse getMyAlbum(@Header("Content-Type") String contentType, @Header("Accept") String accept, @Field("apiaction") String apiaction, @Field("uid") String uid);


    @FormUrlEncoded
    @POST("/myalbumdetails")
    MyAlbumDetailResponse getMyAlbumDetails(@Header("Content-Type") String contentType, @Header("Accept") String accept, @Field("apiaction") String apiaction, @Field("nid") String nid);

    @FormUrlEncoded
    @POST("/getepisode")
    EpisodeListResponse getEpisodeList(@Header("Content-Type") String contentType,@Header("Accept") String accept,@Field("apiaction") String apiaction ,@Field("count") String count);

    @FormUrlEncoded
    @POST("/addnewalbum")
    AddNewAlbumResponse addNewAlbum(@Header("Content-Type") String contentType, @Header("Accept") String accept, @Field("apiaction") String apiaction, @Field("uid") String uid, @Field("title") String title, @Field("description") String description, @Field("locname") String locname, @Field("street") String street, @Field("additional") String additional, @Field("country_name") String country_name, @Field("country") String country, @Field("albumimage") String albumimage);

    @Multipart
    @POST("/addnewalbum")
    AddNewAlbumResponse addNewAlbumWithFile(@Header("Content-Type") String contentType, @Header("Accept") String accept, @Part("apiaction") String apiaction, @Part("uid") String uid, @Part("title") String title, @Part("description") String description, @Part("locname") String locname, @Part("street") String street, @Part("additional") String additional, @Part("country_name") String country_name, @Part("country") String country, @Part("albumimage") String albumimage, @Part("image") TypedFile image);

    @FormUrlEncoded
    @POST("/myfishlist")
    MyFishResponse getMyFishList(@Header("Content-Type") String contentType,@Header("Accept") String accept,@Field("apiaction") String apiaction,@Field("uid") String uid ,@Field("count") String count);

    @FormUrlEncoded
    @POST("/myfishdetail")
    FishDetailResponse getFishDetail(@Header("Content-Type") String contentType,@Header("Accept") String accept,@Field("apiaction") String apiaction,@Field("nid") String nid ,@Field("count") String count);


    @FormUrlEncoded
    @POST("/fishingrecords")
    FishingRecordResponse getFishisngRecord(@Header("Content-Type") String contentType,@Header("Accept") String accept,@Field("apiaction") String apiaction,@Field("count") String count);

}
