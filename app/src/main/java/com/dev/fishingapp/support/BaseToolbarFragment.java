package com.dev.fishingapp.support;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.R;

/**
 * Created by skumari on 4/23/2016.
 */
public class BaseToolbarFragment extends Fragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) getActivity()).resetOptions();
    }

    public void addFragmentWithBackStack(Fragment fragment){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
        ((HomeActivity)getActivity()).setCurrentFragment(fragment);
    }

}
