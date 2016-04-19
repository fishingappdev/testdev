package com.dev.fishingapp;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.Button;

/**
 * Created by user on 4/19/2016.
 */
public class LoginActivity extends Activity {
    private GradientDrawable loginBtnShape,fbBtnShape,watchBtnShape;
    private Button mLoginBtn,mFbBtn,mWatchNowBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginBtn=(Button)findViewById(R.id.loginBtn);
        mFbBtn=(Button)findViewById(R.id.fbBtn);
        mWatchNowBtn=(Button)findViewById(R.id.watchBtn);

        loginBtnShape = (GradientDrawable)mLoginBtn.getBackground();
        loginBtnShape.setColor(getResources().getColor(R.color.drawer_bg));

        fbBtnShape = (GradientDrawable)mFbBtn.getBackground();
        fbBtnShape.setColor(getResources().getColor(R.color.fb_btn));

        watchBtnShape = (GradientDrawable)mWatchNowBtn.getBackground();
        watchBtnShape.setColor(getResources().getColor(R.color.watch_now_btn));
    }
}
