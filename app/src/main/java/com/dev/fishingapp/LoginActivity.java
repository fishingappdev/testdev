package com.dev.fishingapp;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

        loaderManager = getSupportLoaderManager();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
<<<<<<< HEAD

                boolean IsValidate =validateFields();
                if(IsValidate) {
                    /*if(loaderManager.getLoader(R.id.loader_login)== null){
                        loaderManager.initLoader(R.id.loader_login, null, new LoginCallback(this,true,mUsername,mPassword));
                } else {
                    loaderManager.restartLoader(R.id.loader_login, null, new LoginCallback(this,true,mUsername,mPassword));
                }*/
=======
                boolean IsValidate = validateFields();
                if (IsValidate) {
                    if (loaderManager.getLoader(R.id.loader_login) == null) {
                        loaderManager.initLoader(R.id.loader_login, null, new LoginCallback(this, true, mUsername, mPassword));
                    } else {
                        loaderManager.restartLoader(R.id.loader_login, null, new LoginCallback(this, true, mUsername, mPassword));
                    }
>>>>>>> profile pic code & signup layout
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
}
