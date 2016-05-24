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
import com.dev.fishingapp.LoaderCallbacks.FishingRecordCallback;
import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.FishingRecordResponse;
import com.dev.fishingapp.support.BaseToolbarFragment;
import com.dev.fishingapp.support.FishingRecordAdapter;
import com.dev.fishingapp.util.AlertMessageDialog;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 5/24/2016.
 */
public class FishingRecordFRagmnet extends BaseToolbarFragment {
    private ListView mRecordList;
    private FishingRecordAdapter mFishListAdapter;
    private LoaderManager loaderManager;
    private FishingRecordBroadcastReceiver receiver;
    private AlertMessageDialog dialog;
    private FishingRecordResponse recordList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fishing_records, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) getActivity()).setToolBarTitle(getResources().getString(R.string.fishing_record_header));
        mRecordList=(ListView)view.findViewById(R.id.fish_record_list);
        loaderManager = getActivity().getSupportLoaderManager();

        if(loaderManager.getLoader(R.id.loader_fish_record)== null){
            loaderManager.initLoader(R.id.loader_fish_record, null, new FishingRecordCallback(((AbstractActivity) getActivity()),true));
        } else {
            loaderManager.restartLoader(R.id.loader_fish_record, null, new FishingRecordCallback(((AbstractActivity) getActivity()),true));
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter(AppConstants.FISHING_RECORD_CALLBACK_BROADCAST);
        receiver = new FishingRecordBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.unregisterReceiver(receiver);
    }


    class FishingRecordBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.FISHING_RECORD_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                    recordList=(FishingRecordResponse)intent.getSerializableExtra("data");
                    if(recordList.size()>0){
                        mFishListAdapter=new FishingRecordAdapter(getActivity(),recordList);
                        mRecordList.setAdapter(mFishListAdapter);
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
