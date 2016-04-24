package com.dev.fishingapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);

                                } catch (JSONException e) {
                                    if (e.getMessage().equals("No value for email")) {
                                    }
                                    e.printStackTrace();
                                }

                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,first_name,last_name");
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
                                Toast.makeText(LoginActivity.this,"Some Error Occured",Toast.LENGTH_LONG).show();
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
                boolean IsValidate =validateFields();
                if(IsValidate) {
                    /*if(loaderManager.getLoader(R.id.loader_login)== null){
                        loaderManager.initLoader(R.id.loader_login, null, new LoginCallback(this,true,mUsername,mPassword));
                } else {
                    loaderManager.restartLoader(R.id.loader_login, null, new LoginCallback(this,true,mUsername,mPassword));
                }*/
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
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
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
                break;
        }
    }

    private boolean validateFields() {
        mUsername = mUserNameEdt.getText().toString();
        mPassword = mPasswordEdt.getText().toString();
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
        }
        catch (NoSuchAlgorithmException e) {
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
}
