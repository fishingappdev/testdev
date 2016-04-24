package com.dev.fishingapp.support;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.dev.fishingapp.HomeActivity;

/**
 * Created by skumari on 4/23/2016.
 */
public class BaseToolbarFragment extends Fragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) getActivity()).resetOptions();
    }


}
