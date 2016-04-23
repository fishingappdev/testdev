package com.dev.fishingapp.myfish.fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.R;

/**
 * Created by user on 4/21/2016.
 */
public class AddFishFragment extends Fragment {
    private EditText mDate,mLengthEdt,mWeightEdt,mLocationEdt;
    private Spinner mTypeFishSpinner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_fish,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) getActivity()).mAddFishBtn.setVisibility(View.GONE);

        mDate=(EditText)view.findViewById(R.id.date);
        mLengthEdt=(EditText)view.findViewById(R.id.length);
        mWeightEdt=(EditText)view.findViewById(R.id.weight);
        mLocationEdt=(EditText)view.findViewById(R.id.where_caught);
        mTypeFishSpinner=(Spinner)view.findViewById(R.id.fishType);

        mDate.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        mLengthEdt.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        mWeightEdt.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        mWeightEdt.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        String compareValue = "some value";
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.fish_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeFishSpinner.setAdapter(adapter);

    }
}
