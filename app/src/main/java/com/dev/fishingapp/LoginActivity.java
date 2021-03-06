package com.dev.fishingapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.fishingapp.LoaderCallbacks.LoginCallback;
import com.dev.fishingapp.LoaderCallbacks.SignUpCallback;
import com.dev.fishingapp.LoaderCallbacks.UserProfileCallback;
import com.dev.fishingapp.LoaderCallbacks.WatchVideoCallback;
import com.dev.fishingapp.data.model.Login;
import com.dev.fishingapp.data.model.SignUpRequest;
import com.dev.fishingapp.data.model.SignUpResponse;
import com.dev.fishingapp.data.model.WatchVideoRequest;
import com.dev.fishingapp.data.model.WatchVideoResponse;
import com.dev.fishingapp.util.AlertMessageDialog;
import com.dev.fishingapp.util.AppConstants;
import com.dev.fishingapp.util.FishingPreferences;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by user on 4/19/2016.
 */
public class LoginActivity extends AbstractActivity implements OnClickListener {
    private GradientDrawable loginBtnShape, fbBtnShape, watchBtnShape;
    private Button mLoginBtn, mFbBtn, mWatchNowBtn;
    private TextView mForgotPasswordView, msSignup;
    private EditText mUserNameEdt, mPasswordEdt;
    private LoaderManager loaderManager;
    private String mUsername;
    private String mPassword;
    CallbackManager callbackManager;
    private TextView mHeader;
    private ImageView center_logo;
    private LoginBroadcastReceiver receiver;
    private FbLoginBroadcastReceiver loginReceiver;
    private WatchVideoBroadcastReceiver watchVideoBroadcastReceiver;
    private UserProfileBroadcastReceiver profileReceiver;
    private String mFacebookEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        mHeader = (TextView) tb.findViewById(R.id.title);
        center_logo = (ImageView) tb.findViewById(R.id.center_logo);
        setToolBarImage();
        mLoginBtn = (Button) findViewById(R.id.loginBtn);
        mFbBtn = (Button) findViewById(R.id.fbBtn);
        mWatchNowBtn = (Button) findViewById(R.id.watchBtn);
        mForgotPasswordView = (TextView) findViewById(R.id.forgotPwd);
        mUserNameEdt = (EditText) findViewById(R.id.username);
        mPasswordEdt = (EditText) findViewById(R.id.password);
        msSignup = (TextView) findViewById(R.id.signup);

        loginBtnShape = (GradientDrawable) mLoginBtn.getBackground();
        loginBtnShape.setColor(getResources().getColor(R.color.drawer_bg));

        fbBtnShape = (GradientDrawable) mFbBtn.getBackground();
        fbBtnShape.setColor(getResources().getColor(R.color.fb_btn));

        watchBtnShape = (GradientDrawable) mWatchNowBtn.getBackground();
        watchBtnShape.setColor(getResources().getColor(R.color.watch_now_btn));

        mLoginBtn.setOnClickListener(this);
        mForgotPasswordView.setOnClickListener(this);
        msSignup.setOnClickListener(this);
        mFbBtn.setOnClickListener(this);
        //mWatchNowBtn.setOnClickListener(this);

        printKeyHash(this);
        loaderManager = getSupportLoaderManager();

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().getLoginBehavior();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                try {
                                    Log.v("LoginActivity", response.toString());
                                    String str_id = object.getString("id");
                                    mFacebookEmail = object.getString("email");
                                    String str_firstname = object.getString("first_name");
                                    String lastname=object.getString("last_name");
                                    String username = object.getString("name");

                                   /* String tempdata = object.getJSONObject("location").getString("name");
                                    String tempdataArray[] = tempdata.split("\\s+");
                                    String str_city = tempdataArray[0];
                                    String str_country = tempdataArray[1];
                                    Log.v("LoginActivity", str_country);
                */

                                    SignUpRequest signUpRequest = new SignUpRequest(username, mFacebookEmail, mFacebookEmail, str_firstname, lastname, "India");
                                    loaderManager.initLoader(R.id.loader_signup, null, new SignUpCallback(LoginActivity.this, true, signUpRequest));
/*
                                    if (loaderManager.getLoader(R.id.loader_facebook_login) == null) {
                                        loaderManager.initLoader(R.id.loader_facebook_login, null, new FacebookLoginCallback(LoginActivity.this, true, str_email, str_firstname, "India"));
                                    } else {
                                        loaderManager.restartLoader(R.id.loader_facebook_login, null, new FacebookLoginCallback(LoginActivity.this, true, str_email, str_firstname, "India"));
                                    }*/


                                } catch (JSONException e) {
                                    if (e.getMessage().equals("No value for email")) {
                                        AlertMessageDialog dialog = new AlertMessageDialog(LoginActivity.this, getString(R.string.error_txt), getString(R.string.facebook_no_email));
                                    }
                                    e.printStackTrace();
                                }

                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,first_name,last_name,location");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Log.d("cancel", "cancel" + "");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        if (exception instanceof FacebookAuthorizationException) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                Toast.makeText(LoginActivity.this, "Some Error Occured", Toast.LENGTH_LONG).show();
                                LoginManager.getInstance().logOut();
                            }
                        }
                        Log.d("exception", exception + "");
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                boolean IsValidate = validateFields();
                if (IsValidate) {
                    if (loaderManager.getLoader(R.id.loader_login) == null) {
                        loaderManager.initLoader(R.id.loader_login, null, new LoginCallback(this, true, mUsername, mPassword));
                    } else {
                        loaderManager.restartLoader(R.id.loader_login, null, new LoginCallback(this, true, mUsername, mPassword));
                    }

                }
                break;
            case R.id.forgotPwd:
                Intent forgotpwdIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(forgotpwdIntent);
                break;
            case R.id.signup:
                Intent signupIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signupIntent);
                finish();
                break;
            case R.id.fbBtn:
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_location"));
                break;
            case R.id.watchBtn:
                loaderManager.initLoader(R.id.loader_watchnow, null, new WatchVideoCallback(this, true, new WatchVideoRequest()));
                break;
        }
    }

    private boolean validateFields() {
        mUsername = mUserNameEdt.getText().toString().trim();
        mPassword = mPasswordEdt.getText().toString().trim();
        if (mUsername.isEmpty()) {
            mUserNameEdt.setError(getResources().getString(R.string.empty_username));
            return false;
        } else if (mPassword.isEmpty()) {
            mPasswordEdt.setError(getResources().getString(R.string.empty_password));
            return false;
        }
        return true;
    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Log.d("onActivityResult", "After fb login");
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }

    public void setToolBarImage() {
        mHeader.setVisibility(View.GONE);
        center_logo.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter(AppConstants.LOGIN_CALLBACK_BROADCAST);
        IntentFilter loginfilter = new IntentFilter(AppConstants.SIGNUP_CALLBACK_BROADCAST);
        IntentFilter watchVideoFilter = new IntentFilter(AppConstants.WATCH_VIDEO_CALLBACK_BROADCAST);
        IntentFilter userprofileFilter = new IntentFilter(AppConstants.USER_PROFILE_CALLBACK_BROADCAST);
        receiver = new LoginBroadcastReceiver();
        loginReceiver = new FbLoginBroadcastReceiver();
        watchVideoBroadcastReceiver = new WatchVideoBroadcastReceiver();
        profileReceiver=new UserProfileBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);
        localBroadcastManager.registerReceiver(loginReceiver, loginfilter);
        localBroadcastManager.registerReceiver(watchVideoBroadcastReceiver, watchVideoFilter);
        localBroadcastManager.registerReceiver(profileReceiver,userprofileFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.unregisterReceiver(receiver);
        localBroadcastManager.unregisterReceiver(loginReceiver);
        localBroadcastManager.unregisterReceiver(watchVideoBroadcastReceiver);
        localBroadcastManager.unregisterReceiver(profileReceiver);

    }

    class LoginBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.LOGIN_CALLBACK_BROADCAST)) {
                if (intent.getParcelableExtra("data") != null) {
                    Login loginData = intent.getParcelableExtra("data");
                    if (loginData.getStatus().equalsIgnoreCase(getResources().getString(R.string.success_txt))) {
                        String user_id = loginData.getData().getUser_id();
                        String username=loginData.getProfiledata().getField_first_name()+" "+loginData.getProfiledata().getField_last_name();
                        String imageUrl=loginData.getData().getProfilepic();
                        FishingPreferences.getInstance().saveCurrentUserId(user_id);
                        FishingPreferences.getInstance().saveCurrentUsername(username);
                        if(imageUrl!=null && !imageUrl.isEmpty())
                        FishingPreferences.getInstance().setProfileImageUrl(imageUrl);
                        Log.d("user id", user_id + "");
                        Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                        finish();
                    } else {
                        AlertMessageDialog dialog = new AlertMessageDialog(LoginActivity.this, "Error", loginData.getMessage());
                        dialog.setAcceptButtonText("OK");
                        dialog.show();
                    }
                }

            }
        }
    }


    class UserProfileBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.USER_PROFILE_CALLBACK_BROADCAST)) {
                if (intent.getParcelableExtra("data") != null) {
                    Login loginData = intent.getParcelableExtra("data");
                    if (loginData.getStatus().equalsIgnoreCase(getResources().getString(R.string.success_txt))) {
                        String user_id = loginData.getData().getUser_id();
                        String username=loginData.getProfiledata().getField_first_name()+" "+loginData.getProfiledata().getField_last_name();
                        String imageUrl=loginData.getData().getProfilepic();
                        FishingPreferences.getInstance().saveCurrentUserId(user_id);
                        FishingPreferences.getInstance().saveCurrentUsername(username);
                        if(imageUrl!=null && !imageUrl.isEmpty())
                        FishingPreferences.getInstance().setProfileImageUrl(imageUrl);
                        FishingPreferences.getInstance().setIsSocialLogin(true);

                        Log.d("user id", user_id + "");
                        Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                        finish();
                    } else {
                        AlertMessageDialog dialog = new AlertMessageDialog(LoginActivity.this, "Error", loginData.getMessage());
                        dialog.setAcceptButtonText("OK");
                        dialog.show();
                    }
                }

            }
        }
    }

    class WatchVideoBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.WATCH_VIDEO_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                    WatchVideoResponse watchVideoResponse = (WatchVideoResponse) intent.getSerializableExtra("data");
                    if (watchVideoResponse.isResponse()&&!TextUtils.isEmpty(watchVideoResponse.getData().getVideo())) {
                        Intent videoIntent = new Intent(Intent.ACTION_VIEW);
                        videoIntent.setDataAndType(Uri.parse(watchVideoResponse.getData().getVideo()), "video/*");
                        startActivity(Intent.createChooser(videoIntent, "Complete action using"));
                    } else {
                        AlertMessageDialog dialog = new AlertMessageDialog(LoginActivity.this, "Error", getString(R.string.some_error_occured));
                        dialog.setAcceptButtonText("OK");
                        dialog.show();
                    }
                }

            }
        }
    }

    class FbLoginBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.SIGNUP_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {

                        SignUpResponse signUpResponse = (SignUpResponse) intent.getSerializableExtra("data");
                        if (signUpResponse.getStatus().equals("success")) {
                            String user_id = signUpResponse.getUserdata().getUser_id();
                            String username=signUpResponse.getProfiledata().getField_first_name()+" "+signUpResponse.getProfiledata().getField_last_name();
                            FishingPreferences.getInstance().saveCurrentUserId(user_id);
                            FishingPreferences.getInstance().saveCurrentUserId(username);
                            FishingPreferences.getInstance().setIsSocialLogin(true);

                            Log.d("user id", user_id + "");
                            Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(homeIntent);
                            finish();
                        } else if(signUpResponse.getMessage().equalsIgnoreCase("Email id is already exist.")) {
                            loaderManager.initLoader(R.id.loader_facebook_login, null, new UserProfileCallback(LoginActivity.this, true, mFacebookEmail));

                        }else{
                            AlertMessageDialog dialog = new AlertMessageDialog(LoginActivity.this, getString(R.string.error_txt), getString(R.string.username_email_id_exists));
                            dialog.setAcceptButtonText(getString(R.string.ok_txt));
                            dialog.show();
                        }





               }

            }
        }
    }

}
