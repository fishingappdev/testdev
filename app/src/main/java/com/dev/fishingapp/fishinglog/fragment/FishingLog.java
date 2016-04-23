package com.dev.fishingapp.fishinglog.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.FishingLogResponse;
import com.dev.fishingapp.fishinglog.support.FishLogListAdapter;

import java.util.ArrayList;

/**
 * Created by user on 4/18/2016.
 */
public class FishingLog extends Fragment implements OnItemClickListener{
    private ListView mLogList;
    private ArrayList<FishingLogResponse> myFishLogArrayList;
    private FishLogListAdapter mFishLogAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fishing_log,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) getActivity()).setToolBarTitle(getResources().getString(R.string.header_fishinglog));
        ((HomeActivity) getActivity()).mAddLogBtn.setVisibility(View.VISIBLE);
        ((HomeActivity) getActivity()).mAddLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm= getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.content_frame,new AddFishingLog()).commit();
            }
        });

        mLogList=(ListView)view.findViewById(R.id.fishlog_list);
        myFishLogArrayList=new ArrayList<>();
        for(int i=0;i<7;i++){
            FishingLogResponse logResponse=new FishingLogResponse("BIG FISH","Australia","Lorem Ipsum is the simply dummy text of the printing and typesetting industry","21/04/16");
            myFishLogArrayList.add(logResponse);
        }
        mFishLogAdapter=new FishLogListAdapter(getActivity(),myFishLogArrayList);
        mLogList.setAdapter(mFishLogAdapter);
        mLogList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager fm= getActivity().getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame,new FishingLogDetail()).commit();

    }
}
