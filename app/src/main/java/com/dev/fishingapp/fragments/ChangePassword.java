package com.dev.fishingapp.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dev.fishingapp.AbstractActivity;
import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.LoaderCallbacks.ChangePasswordCallback;
import com.dev.fishingapp.R;
import com.dev.fishingapp.support.BaseToolbarFragment;
import com.dev.fishingapp.util.AlertMessageDialog;
import com.dev.fishingapp.util.AppConstants;
import com.dev.fishingapp.util.FishingPreferences;

/**
 * Created by user on 4/18/2016.
 */
public class ChangePassword extends BaseToolbarFragment {
    private GradientDrawable submitBtnShape;
    private Button mSubmitBtn;
    private EditText mOldPwdtxt,mNewPwdTxt,mCnfPwdTxt;
    private String mOldPwd,mNewPwd,mCnfPwd;
    private LoaderManager loaderManager;
    private ChangePasswordBroadcastReceiver receiver;
    private AlertMessageDialog dialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) getActivity()).setToolBarImage();
        loaderManager = getActivity().getSupportLoaderManager();
        mSubmitBtn = (Button) view.findViewById(R.id.changePwdBtn);
        submitBtnShape = (GradientDrawable) mSubmitBtn.getBackground();
        submitBtnShape.setColor(getResources().getColor(R.color.drawer_bg));
        mOldPwdtxt = (EditText) view.findViewById(R.id.old_pwd_edt);
        mNewPwdTxt = (EditText) view.findViewById(R.id.new_pwd_txt);
        mCnfPwdTxt = (EditText) view.findViewById(R.id.cnf_pwd_edt);

       mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewPwd = mNewPwdTxt.getText().toString();
                mOldPwd = mOldPwdtxt.getText().toString();
                mCnfPwd = mCnfPwdTxt.getText().toString();
                String userId= FishingPreferences.getInstance().getCurrentUserId();
                if(validateFields()){
                    if(loaderManager.getLoader(R.id.loader_change_password)== null){
                        loaderManager.initLoader(R.id.loader_change_password, null, new ChangePasswordCallback(((AbstractActivity) getActivity()),true,mOldPwd,mNewPwd,userId));
                    } else {
                        loaderManager.restartLoader(R.id.loader_change_password, null, new ChangePasswordCallback(((AbstractActivity) getActivity()),true,mOldPwd,mNewPwd,userId));
                    }
                }
            }
        });
    }

    private boolean validateFields(){
        if(mOldPwd.isEmpty()){
            mOldPwdtxt.setError(getString(R.string.empty_password));
            return false;
        }else if(mNewPwd.isEmpty()){
            mNewPwdTxt.setError(getString(R.string.empty_password));
            return false;
        }else if(mCnfPwd.isEmpty()){
            mCnfPwdTxt.setError(getString(R.string.empty_password));
            return false;
        }else if(!mNewPwd.equals(mCnfPwd)){
            mCnfPwdTxt.setError(getString(R.string.password_not_match));
            return false;
        }
        return true;
    }

    class ChangePasswordBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.CHANGE_PWD_CALLBACK_BROADCAST)) {
                if (intent.getParcelableExtra("data") != null) {
                    com.dev.fishingapp.data.model.ChangePassword changePwdData= intent.getParcelableExtra("data");
                    if(changePwdData.getStatus().equalsIgnoreCase(getString(R.string.success_txt))){
                        dialog=new AlertMessageDialog(((HomeActivity)getActivity()),getActivity().getString(R.string.success_txt),changePwdData.getMessage());
                        dialog.setAcceptButtonText(getString(R.string.ok_txt));
                        dialog.show();
                        mNewPwdTxt.getText().clear();
                        mOldPwdtxt.getText().clear();
                        mCnfPwdTxt.getText().clear();
                    }else{
                        dialog=new AlertMessageDialog(((HomeActivity)getActivity()),getActivity().getString(R.string.error_txt),changePwdData.getMessage());
                        dialog.setAcceptButtonText(getString(R.string.ok_txt));
                        dialog.show();
                        mNewPwdTxt.getText().clear();
                        mOldPwdtxt.getText().clear();
                        mCnfPwdTxt.getText().clear();
                    }

                }

            }
        }
    }


   @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter(AppConstants.CHANGE_PWD_CALLBACK_BROADCAST);
        receiver = new ChangePasswordBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.unregisterReceiver(receiver);
    }
}

