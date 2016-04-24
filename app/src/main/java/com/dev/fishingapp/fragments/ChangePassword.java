package com.dev.fishingapp.fragments;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.R;
import com.dev.fishingapp.support.BaseToolbarFragment;

/**
 * Created by user on 4/18/2016.
 */
public class ChangePassword extends BaseToolbarFragment {
    private GradientDrawable submitBtnShape;
    private Button mSubmitBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) getActivity()).setToolBarImage();
        mSubmitBtn = (Button) view.findViewById(R.id.changePwdBtn);
        submitBtnShape = (GradientDrawable) mSubmitBtn.getBackground();
        submitBtnShape.setColor(getResources().getColor(R.color.drawer_bg));

    }
}
