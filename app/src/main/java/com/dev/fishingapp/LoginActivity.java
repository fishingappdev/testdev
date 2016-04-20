package com.dev.fishingapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by user on 4/19/2016.
 */
public class LoginActivity extends Activity implements OnClickListener{
    private GradientDrawable loginBtnShape,fbBtnShape,watchBtnShape;
    private Button mLoginBtn,mFbBtn,mWatchNowBtn;
    private TextView mForgotPasswordView;
    private EditText mUserNameEdt,mPasswordEdt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginBtn=(Button)findViewById(R.id.loginBtn);
        mFbBtn=(Button)findViewById(R.id.fbBtn);
        mWatchNowBtn=(Button)findViewById(R.id.watchBtn);
        mForgotPasswordView=(TextView)findViewById(R.id.forgotPwd);

        loginBtnShape = (GradientDrawable)mLoginBtn.getBackground();
        loginBtnShape.setColor(getResources().getColor(R.color.drawer_bg));

        fbBtnShape = (GradientDrawable)mFbBtn.getBackground();
        fbBtnShape.setColor(getResources().getColor(R.color.fb_btn));

        watchBtnShape = (GradientDrawable)mWatchNowBtn.getBackground();
        watchBtnShape.setColor(getResources().getColor(R.color.watch_now_btn));

        mLoginBtn.setOnClickListener(this);
        mForgotPasswordView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.loginBtn:
                boolean IsValidate =validateFields();
                if(IsValidate) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.forgotPwd:
                Intent forgotpwdIntent=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(forgotpwdIntent);
        }
    }

    private boolean validateFields(){
        String userName=mUserNameEdt.getText().toString();
        String password=mPasswordEdt.getText().toString();
        if(userName.isEmpty()){
            mUserNameEdt.setError(getResources().getString(R.string.empty_username));
            return false;
        }else if(password.isEmpty()){
            mPasswordEdt.setError(getResources().getString(R.string.empty_password));
            return  false;
        }
        return true;
    }
}
