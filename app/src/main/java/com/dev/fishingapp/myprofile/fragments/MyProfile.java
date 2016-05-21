package com.dev.fishingapp.myprofile.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dev.fishingapp.AbstractActivity;
import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.LoaderCallbacks.GetProfileCallback;
import com.dev.fishingapp.LoaderCallbacks.SetProfileCallback;
import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.GetEditProfileRequest;
import com.dev.fishingapp.data.model.GetEditProfileResponse;
import com.dev.fishingapp.data.model.SetEditProfileRequest;
import com.dev.fishingapp.data.model.SetEditProfileResponse;
import com.dev.fishingapp.support.BaseToolbarFragment;
import com.dev.fishingapp.util.AlertMessageDialog;
import com.dev.fishingapp.util.AppConstants;
import com.dev.fishingapp.util.FishingPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 4/18/2016.
 */
public class MyProfile extends BaseToolbarFragment {
    EditText etAboutMe;
    LinearLayout checkboxContainer;
    Button saveBtn;
    private LoaderManager mLoaderManager;
    private GetProfileBroadcastReceiver receiver;
    private SetProfileBroadcastReceiver setReceiver;
    CheckBox checkBox[];
    Resources resources;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myprofile, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkBox = new CheckBox[11];
        ((HomeActivity) getActivity()).setToolBarTitle(getString(R.string.my_profile_txt));
        etAboutMe = (EditText) view.findViewById(R.id.about_me);
        checkboxContainer = (LinearLayout) view.findViewById(R.id.checkbox_container);
        saveBtn = (Button) view.findViewById(R.id.savebtn);
        enableDisableAllView(false);
        saveBtn.setVisibility(View.GONE);
        mLoaderManager = getActivity().getSupportLoaderManager();
        resources = getResources();
        for (int i = 0; i < checkBox.length; i++) {
            int id = resources.getIdentifier("cb_" + i, "id", getContext().getPackageName());
            checkBox[i] = (CheckBox) view.findViewById(id);
        }
        ((HomeActivity) getActivity()).showRightOption(HomeActivity.EDIT_OPTION, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableDisableAllView(true);
                saveBtn.setVisibility(View.VISIBLE);
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> prefArray = new ArrayList<String>();
                for (int i = 0; i < checkBox.length; i++) {
                    if (checkBox[i].isChecked())
                        prefArray.add((String) checkBox[i].getText());
                }
                StringBuilder result = new StringBuilder();
                for (String string : prefArray) {
                    result.append(string);
                    result.append(",");
                }
                String prefAsString = result.length() > 0 ? result.substring(0, result.length() - 1) : "";
                SetEditProfileRequest setEditProfileRequest = new SetEditProfileRequest(FishingPreferences.getInstance().getCurrentUserId(), etAboutMe.getText().toString(), prefAsString);
                mLoaderManager.initLoader(R.id.loader_setprofile, null, new SetProfileCallback(((AbstractActivity) getActivity()), true, setEditProfileRequest));
            }
        });
        GetEditProfileRequest getEditProfileRequest = new GetEditProfileRequest(FishingPreferences.getInstance().getCurrentUserId());
        mLoaderManager.initLoader(R.id.loader_getprofile, null, new GetProfileCallback(((AbstractActivity) getActivity()), true, getEditProfileRequest));
    }

    private void enableDisableAllView(boolean enable) {
        etAboutMe.setEnabled(enable);
        enableDisableView(checkboxContainer, enable);
    }

    private void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);

        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;

            for (int idx = 0; idx < group.getChildCount(); idx++) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }

    class GetProfileBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.GET_PROFILE_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                    GetEditProfileResponse response = (GetEditProfileResponse) intent.getSerializableExtra("data");
                    if (response.getStatus() != null && response.getStatus().equals("success")) {
                        updateUI(response);
                    } else {
                        AlertMessageDialog dialog = new AlertMessageDialog(getActivity(), getString(R.string.error_txt), getString(R.string.some_error_occured));
                        dialog.setAcceptButtonText(getString(R.string.ok_txt));
                        dialog.show();
                    }
                }

            }
        }
    }

    class SetProfileBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.SET_PROFILE_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                    SetEditProfileResponse response = (SetEditProfileResponse) intent.getSerializableExtra("data");
                    if (response.getStatus() != null && response.getStatus().equals("success")) {
                        enableDisableAllView(false);
                        saveBtn.setVisibility(View.GONE);
                    } else {
                        AlertMessageDialog dialog = new AlertMessageDialog(getActivity(), getString(R.string.error_txt), getString(R.string.some_error_occured));
                        dialog.setAcceptButtonText(getString(R.string.ok_txt));
                        dialog.show();
                    }
                }

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter(AppConstants.GET_PROFILE_CALLBACK_BROADCAST);
        receiver = new GetProfileBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);
        intentFilter = new IntentFilter(AppConstants.SET_PROFILE_CALLBACK_BROADCAST);
        setReceiver = new SetProfileBroadcastReceiver();
        localBroadcastManager.registerReceiver(setReceiver, intentFilter);

    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.unregisterReceiver(receiver);
        localBroadcastManager.unregisterReceiver(setReceiver);
    }

    private void updateUI(GetEditProfileResponse response) {
        try {
            etAboutMe.setText(response.getProfiledata().getField_about_me());
            List<String> items = Arrays.asList(response.getProfiledata().getField_fishing_preferences().split("\\s*,\\s*"));
            for (int i = 0; i < checkBox.length; i++) {
                if (items.contains(checkBox[i].getText())) {
                    checkBox[i].setChecked(true);
                }
            }
        } catch (NullPointerException e) {
            Log.d("", "Nullpointerexception at Editprofile");
        }
    }
}
