package com.dev.fishingapp.myfish.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.R;
import com.dev.fishingapp.support.BaseToolbarFragment;

/**
 * Created by user on 4/21/2016.
 */
public class FishDetailFragment extends BaseToolbarFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fish_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) getActivity()).showRightOption(HomeActivity.ADD_FISH_OPTION, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().add(R.id.content_frame, new AddFishFragment()).addToBackStack(null).commit();
            }
        });
    }
}
