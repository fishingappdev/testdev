package com.dev.fishingapp.fishinglog.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.R;

/**
 * Created by user on 4/23/2016.
 */
public class AddFishingLog extends Fragment {
    private Spinner mMoonSpinner,mtideSpinner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmnet_add_fishing_log, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) getActivity()).setToolBarTitle(getResources().getString(R.string.header_fishinglog));
        mMoonSpinner=(Spinner)view.findViewById(R.id.moon_view);
        mtideSpinner=(Spinner)view.findViewById(R.id.tide_view);
        String compareValue = "some value";
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.moon_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMoonSpinner.setAdapter(adapter);

        String compareValue1 = "some value";
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.tide_type_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mtideSpinner.setAdapter(adapter1);

    }
}
