package com.dev.fishingapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by user on 4/20/2016.
 */
public class ForgotPasswordActivity extends Activity {
    private EditText mForgotPwdEdt;
    private Button mSubmitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mForgotPwdEdt=(EditText)findViewById(R.id.forgotpwdEdt);
        mSubmitBtn=(Button)findViewById(R.id.submitBtn);

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }
}
