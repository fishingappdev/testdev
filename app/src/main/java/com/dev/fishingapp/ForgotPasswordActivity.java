package com.dev.fishingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dev.fishingapp.LoaderCallbacks.ForgotPasswordCallback;
import com.dev.fishingapp.data.model.ForgotPassword;
import com.dev.fishingapp.util.AlertMessageDialog;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 4/20/2016.
 */
public class ForgotPasswordActivity extends AbstractActivity {
    private EditText mForgotPwdEdt;
    private Button mSubmitBtn;
    private GradientDrawable mSubmitBtnShape;
    private String mEmail;
    private LoaderManager loaderManager;
    private ForgotPasswordBroadcastReceiver receiver;
    private AlertMessageDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mForgotPwdEdt=(EditText)findViewById(R.id.forgotpwdEdt);
        mSubmitBtn=(Button)findViewById(R.id.submitBtn);
        mSubmitBtnShape = (GradientDrawable) mSubmitBtn.getBackground();
        mSubmitBtnShape.setColor(getResources().getColor(R.color.drawer_bg));
        loaderManager=getSupportLoaderManager();


        mSubmitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               mEmail=mForgotPwdEdt.getText().toString();
                if(mEmail.isEmpty()){
                    mForgotPwdEdt.setError(getString(R.string.empty_email_id));
                }else{
                    if(loaderManager.getLoader(R.id.loader_forgot_password)== null){
                        loaderManager.initLoader(R.id.loader_forgot_password, null, new ForgotPasswordCallback(ForgotPasswordActivity.this,true,mEmail));
                    } else {
                        loaderManager.restartLoader(R.id.loader_forgot_password, null, new ForgotPasswordCallback(ForgotPasswordActivity.this,true,mEmail));
                    }
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter(AppConstants.FORGOT_PWD_CALLBACK_BROADCAST);
        receiver = new ForgotPasswordBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.unregisterReceiver(receiver);
    }


    class ForgotPasswordBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.FORGOT_PWD_CALLBACK_BROADCAST)) {
                if (intent.getParcelableExtra("data") != null) {
                    ForgotPassword data = intent.getParcelableExtra("data");
                    if(data.isReponse()){
                        dialog= new AlertMessageDialog(ForgotPasswordActivity.this,"Success","Please Check your Email");
                        dialog.setAcceptButtonText(getResources().getString(R.string.ok_txt));
                        dialog.show();
                        finish();
                    }else{
                       dialog= new AlertMessageDialog(ForgotPasswordActivity.this,"Error","Email not found");
                        dialog.setAcceptButtonText(getResources().getString(R.string.ok_txt));
                        dialog.show();
                    }

                }

            }
        }
    }
}
