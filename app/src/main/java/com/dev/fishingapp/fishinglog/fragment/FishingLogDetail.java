package com.dev.fishingapp.fishinglog.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.R;

/**
 * Created by user on 4/23/2016.
 */
public class FishingLogDetail extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fishinglog_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) getActivity()).setToolBarTitle(getResources().getString(R.string.header_my_fishinglog));
        ((HomeActivity) getActivity()).mAddFishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm= getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.content_frame,new AddFishingLog()).commit();

            }
        });


    }
}
