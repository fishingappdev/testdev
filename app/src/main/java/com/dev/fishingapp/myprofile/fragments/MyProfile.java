package com.dev.fishingapp.myprofile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.R;
import com.dev.fishingapp.support.BaseToolbarFragment;

/**
 * Created by user on 4/18/2016.
 */
public class MyProfile extends BaseToolbarFragment {
    EditText etAboutMe;
    LinearLayout checkboxContainer;
    Button saveBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myprofile, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) getActivity()).setToolBarTitle(getString(R.string.my_profile_txt));
        etAboutMe = (EditText) view.findViewById(R.id.about_me);
        checkboxContainer = (LinearLayout) view.findViewById(R.id.checkbox_container);
        saveBtn = (Button) view.findViewById(R.id.savebtn);
        enableDisableAllView(false);
        saveBtn.setVisibility(View.GONE);
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
                enableDisableAllView(false);
                saveBtn.setVisibility(View.GONE);
            }
        });
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

}
