package com.dev.fishingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.R;
import com.dev.fishingapp.support.EpisodeListAdapter;

import java.util.ArrayList;

/**
 * Created by user on 4/18/2016.
 */
public class MyEpisodeList extends Fragment {
    private ListView mEpisodeList;
    private ArrayList<String> episodeTitleList;
    private EpisodeListAdapter mEpisodeListAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_episodelist, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) getActivity()).setToolBarTitle(getResources().getString(R.string.header_episode_list));

        mEpisodeList=(ListView)view.findViewById(R.id.episode_list);
        episodeTitleList=new ArrayList<>();
        for(int i=0 ;i<6;i++){
            episodeTitleList.add("EPISODE "+(i+1));
        }
        mEpisodeListAdapter=new EpisodeListAdapter(getActivity(),episodeTitleList);
        mEpisodeList.setAdapter(mEpisodeListAdapter);

    }
}
