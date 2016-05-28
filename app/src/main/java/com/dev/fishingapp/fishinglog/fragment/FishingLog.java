package com.dev.fishingapp.fishinglog.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.dev.fishingapp.AbstractActivity;
import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.LoaderCallbacks.FishingLogCallback;
import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.FishingLogData;
import com.dev.fishingapp.data.model.FishingLogResponse;
import com.dev.fishingapp.fishinglog.support.FishLogListAdapter;
import com.dev.fishingapp.support.BaseToolbarFragment;
import com.dev.fishingapp.util.AlertMessageDialog;
import com.dev.fishingapp.util.AppConstants;

import java.util.ArrayList;

/**
 * Created by user on 4/18/2016.
 */
public class FishingLog extends BaseToolbarFragment implements OnItemClickListener {
    private ListView mLogList;
    private ArrayList<FishingLogData> myFishLogArrayList;
    private FishLogListAdapter mFishLogAdapter;
    private LoaderManager loaderManager;
    private FishingLogBroadcastReceiver receiver;
    private AlertMessageDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fishing_log, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) getActivity()).setToolBarTitle(getResources().getString(R.string.header_fishinglog));
        ((HomeActivity) getActivity()).showRightOption(HomeActivity.ADD_LOG_OPTION, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.content_frame, new AddFishingLog()).hide(FishingLog.this).addToBackStack(null).commit();
            }
        });

        mLogList = (ListView) view.findViewById(R.id.fishlog_list);


        loaderManager = getActivity().getSupportLoaderManager();

        if(loaderManager.getLoader(R.id.loader_fish_log)== null){
            loaderManager.initLoader(R.id.loader_fish_log, null, new FishingLogCallback(((AbstractActivity) getActivity()),true));
        } else {
            loaderManager.restartLoader(R.id.loader_fish_log, null, new FishingLogCallback(((AbstractActivity) getActivity()),true));
        }

        mLogList.setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter(AppConstants.FISHING_LOG_CALLBACK_BROADCAST);
        receiver = new FishingLogBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.unregisterReceiver(receiver);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putString("nid", myFishLogArrayList.get(position).getNid());
        Fragment fragment=new FishingLogDetail();
        fragment.setArguments(bundle);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction().add(R.id.content_frame, fragment).hide(this).addToBackStack(null).commit();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            ((HomeActivity) getActivity()).showRightOption(HomeActivity.ADD_LOG_OPTION, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.beginTransaction().add(R.id.content_frame, new AddFishingLog()).hide(FishingLog.this).addToBackStack(null).commit();
                }
            });
        }
    }


    class FishingLogBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.FISHING_LOG_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                    myFishLogArrayList=(FishingLogResponse)intent.getSerializableExtra("data");
                    if(myFishLogArrayList.size()>0){
                        mFishLogAdapter = new FishLogListAdapter(getActivity(), myFishLogArrayList);
                        mLogList.setAdapter(mFishLogAdapter);
                    }else{
                        dialog=new AlertMessageDialog(((HomeActivity)getActivity()),getActivity().getString(R.string.error_txt),getString(R.string.empty_list));
                        dialog.setAcceptButtonText(getString(R.string.ok_txt));
                        dialog.show();

                    }

                }

            }
        }
    }
}
