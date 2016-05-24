package com.dev.fishingapp.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dev.fishingapp.AbstractActivity;
import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.LoaderCallbacks.EpisodeListCallback;
import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.EpisodeListResponse;
import com.dev.fishingapp.support.BaseToolbarFragment;
import com.dev.fishingapp.support.EpisodeListAdapter;
import com.dev.fishingapp.util.AlertMessageDialog;
import com.dev.fishingapp.util.AppConstants;

import java.util.ArrayList;

/**
 * Created by user on 4/18/2016.
 */
public class MyEpisodeList extends BaseToolbarFragment {
    private ListView mEpisodeList;
    private ArrayList<String> episodeTitleList;
    private EpisodeListAdapter mEpisodeListAdapter;
    private AlertMessageDialog dialog;
    private EpisodeListBroadcastReceiver receiver;
    private LoaderManager loaderManager;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_episodelist, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) getActivity()).setToolBarTitle(getResources().getString(R.string.header_episode_list));
        loaderManager = getActivity().getSupportLoaderManager();
        mEpisodeList=(ListView)view.findViewById(R.id.episode_list);
        if(loaderManager.getLoader(R.id.loader_episode_list)== null){
            loaderManager.initLoader(R.id.loader_episode_list, null, new EpisodeListCallback(((AbstractActivity) getActivity()),true));
        } else {
            loaderManager.restartLoader(R.id.loader_episode_list, null, new EpisodeListCallback(((AbstractActivity) getActivity()),true));
        }


    }

    class EpisodeListBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.EPISODE_LIST_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                    EpisodeListResponse episodeList= (EpisodeListResponse)intent.getSerializableExtra("data");
                    if(episodeList.size()>0){
                        mEpisodeListAdapter=new EpisodeListAdapter(getActivity(),episodeList);
                        mEpisodeList.setAdapter(mEpisodeListAdapter);
                    }else{
                        dialog=new AlertMessageDialog(((HomeActivity)getActivity()),getActivity().getString(R.string.error_txt),getString(R.string.empty_list));
                        dialog.setAcceptButtonText(getString(R.string.ok_txt));
                        dialog.show();

                    }

                }

            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter(AppConstants.EPISODE_LIST_CALLBACK_BROADCAST);
        receiver = new EpisodeListBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.unregisterReceiver(receiver);
    }
}
