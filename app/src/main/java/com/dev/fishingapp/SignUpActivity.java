package com.dev.fishingapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by user on 4/19/2016.
 */
public class SignUpActivity extends Activity implements OnClickListener {
    private GradientDrawable loginBtnShape;
    private Button mSignUpBtn;
    private TextView msigninClick;
    private EditText mUserNameEdt, mEmailIdEdt, mConfirmEmailId, mFirstName, mLastName;
    private AutoCompleteTextView mCountry;
    private String[] values;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:
                boolean IsValidate = validateFields();
                if (IsValidate) {
                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
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
        } else if (emailId.equals(confirmEmailId)) {
            mLastName.setError(getResources().getString(R.string.email_confirm_doesnot_match));
            return false;
        }
        return true;
    }
}
