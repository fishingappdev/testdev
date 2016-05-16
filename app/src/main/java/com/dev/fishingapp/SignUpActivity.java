package com.dev.fishingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.fishingapp.LoaderCallbacks.SignUpCallback;
import com.dev.fishingapp.data.model.SignUpRequest;
import com.dev.fishingapp.data.model.SignUpResponse;
import com.dev.fishingapp.util.AlertMessageDialog;
import com.dev.fishingapp.util.AppConstants;
import com.dev.fishingapp.util.FishingPreferences;

/**
 * Created by user on 4/19/2016.
 */
public class SignUpActivity extends AbstractActivity implements OnClickListener {
    private GradientDrawable loginBtnShape;
    private Button mSignUpBtn;
    private TextView msigninClick;
    private EditText mUserNameEdt, mEmailIdEdt, mConfirmEmailId, mFirstName, mLastName;
    private AutoCompleteTextView mCountry;
    private String[] values;
    private TextView mHeader;
    private ImageView center_logo;
    private LoaderManager mLoaderManager;
    private String country = "India";
    private SignUpBroadcastReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        mHeader = (TextView) tb.findViewById(R.id.title);
        center_logo = (ImageView) tb.findViewById(R.id.center_logo);
        mHeader.setText(R.string.Signup_txt);
        setToolBarImage();
        mSignUpBtn = (Button) findViewById(R.id.signUpBtn);
        msigninClick = (TextView) findViewById(R.id.signin_click);
        mUserNameEdt = (EditText) findViewById(R.id.username);
        mEmailIdEdt = (EditText) findViewById(R.id.email_id);
        mConfirmEmailId = (EditText) findViewById(R.id.confirm_email_id);
        mFirstName = (EditText) findViewById(R.id.first_name);
        mLastName = (EditText) findViewById(R.id.last_name);
        mCountry = (AutoCompleteTextView) findViewById(R.id.country);
        values = getResources().getStringArray(R.array.countries_array);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCountry.setAdapter(spinnerArrayAdapter);
        loginBtnShape = (GradientDrawable) mSignUpBtn.getBackground();
        loginBtnShape.setColor(getResources().getColor(R.color.drawer_bg));

        mSignUpBtn.setOnClickListener(this);
        msigninClick.setOnClickListener(this);
        mCountry.setOnClickListener(this);
        mLoaderManager = getSupportLoaderManager();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:
                boolean IsValidate = validateFields();
                if (IsValidate) {
                    String username = mUserNameEdt.getText().toString();
                    String email = mEmailIdEdt.getText().toString().trim();
                    String confemail = mConfirmEmailId.getText().toString().trim();
                    String firstname = mFirstName.getText().toString();
                    String lastname = mLastName.getText().toString();
                    String country = this.country;
                    SignUpRequest signUpRequest = new SignUpRequest(username, email, confemail, firstname, lastname, country);
                    mLoaderManager.initLoader(R.id.loader_signup, null, new SignUpCallback(this, true, signUpRequest));
//                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
//                    startActivity(intent);
//                    finish();
                }
                break;
            case R.id.signin_click:
                Intent signInIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(signInIntent);
                finish();
                break;
            case R.id.country:
                mCountry.showDropDown();
                break;
        }
    }

    private boolean validateFields() {
        String userName = mUserNameEdt.getText().toString();
        String emailId = mEmailIdEdt.getText().toString();
        String confirmEmailId = mConfirmEmailId.getText().toString();
        String firstName = mFirstName.getText().toString();
        String lastName = mLastName.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            mUserNameEdt.setError(getResources().getString(R.string.empty_username));
            return false;
        } else if (TextUtils.isEmpty(emailId)) {
            mEmailIdEdt.setError(getResources().getString(R.string.empty_email_id));
            return false;
        } else if (TextUtils.isEmpty(confirmEmailId)) {
            mConfirmEmailId.setError(getResources().getString(R.string.empty_confirm_email_id));
            return false;
        } else if (TextUtils.isEmpty(firstName)) {
            mFirstName.setError(getResources().getString(R.string.empty_first_name));
            return false;
        } else if (TextUtils.isEmpty(lastName)) {
            mLastName.setError(getResources().getString(R.string.empty_last_name));
            return false;
        } else if (!emailId.equals(confirmEmailId)) {
            mConfirmEmailId.setError(getResources().getString(R.string.email_confirm_doesnot_match));
            return false;
        }
        return true;
    }

    public void setToolBarImage() {
        mHeader.setVisibility(View.GONE);
        center_logo.setVisibility(View.VISIBLE);
    }

    class SignUpBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.SIGNUP_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                    SignUpResponse signUpResponse = (SignUpResponse) intent.getSerializableExtra("data");
                    if (signUpResponse.isSuccess()) {
                        String user_id = signUpResponse.getData().getUser_id();
                        FishingPreferences.getInstance().saveCurrentUserId(user_id);
                        Log.d("user id", user_id + "");
                        Intent homeIntent = new Intent(SignUpActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                        finish();
                    } else {
                        AlertMessageDialog dialog = new AlertMessageDialog(SignUpActivity.this, getString(R.string.error_txt), getString(R.string.username_email_id_exists));
                        dialog.setAcceptButtonText(getString(R.string.ok_txt));
                        dialog.show();
                    }
                }

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter(AppConstants.SIGNUP_CALLBACK_BROADCAST);
        receiver = new SignUpBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.unregisterReceiver(receiver);
    }
}
