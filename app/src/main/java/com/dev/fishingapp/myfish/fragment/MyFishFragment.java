package com.dev.fishingapp.myfish.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dev.fishingapp.AbstractActivity;
import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.LoaderCallbacks.MyFishCallback;
import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.MyFishResponse;
import com.dev.fishingapp.myfish.support.FishListAdapter;
import com.dev.fishingapp.support.BaseToolbarFragment;
import com.dev.fishingapp.util.AlertMessageDialog;
import com.dev.fishingapp.util.AppConstants;
import com.dev.fishingapp.util.FishingPreferences;

/**
 * Created by user on 4/18/2016.
 */
public class MyFishFragment extends BaseToolbarFragment implements AdapterView.OnItemClickListener {
    private ListView mFishList;
    private FishListAdapter mFishListAdapter;
    private LoaderManager loaderManager;
    private MyFishListBroadcastReceiver receiver;
    private LoadFishBroadcastReceiver loadreceiver;
    private AlertMessageDialog dialog;
    MyFishResponse fishList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myfish, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loaderManager = getActivity().getSupportLoaderManager();
        ((HomeActivity) getActivity()).setToolBarTitle(getResources().getString(R.string.my_fish_header));
        ((HomeActivity) getActivity()).showRightOption(HomeActivity.ADD_FISH_OPTION, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.add(R.id.content_frame, new AddFishFragment()).hide(MyFishFragment.this).addToBackStack(null).commit();
            }
        });
        mFishList = (ListView) view.findViewById(R.id.myfish_list);
        String uid = FishingPreferences.getInstance().getCurrentUserId();

        if (loaderManager.getLoader(R.id.loader_fish_list) == null) {
            loaderManager.initLoader(R.id.loader_fish_list, null, new MyFishCallback(((AbstractActivity) getActivity()), true, uid));
        } else {
            loaderManager.restartLoader(R.id.loader_fish_list, null, new MyFishCallback(((AbstractActivity) getActivity()), true, uid));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("My Fish", "On RESUME");
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter(AppConstants.MYFISH_LIST_CALLBACK_BROADCAST);
        IntentFilter intentfilter = new IntentFilter(AppConstants.LOAD_FISH_CALLBACK_BROADCAST);

        receiver = new MyFishListBroadcastReceiver();
        loadreceiver = new LoadFishBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);
        localBroadcastManager.registerReceiver(loadreceiver, intentfilter);
    }

    @Override
    public void onDetach() {
        Log.d("My Fish", "On detach");
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.unregisterReceiver(receiver);
        localBroadcastManager.unregisterReceiver(loadreceiver);
        super.onDetach();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putString("catid", fishList.get(position).getCatid());
        Fragment fragment = new FishCategoryFragment();
        fragment.setArguments(bundle);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction().add(R.id.content_frame, fragment).hide(this).addToBackStack(null).commit();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d("setUserVisibleHint", "MyFish Fragment" + hidden);
        if (!hidden) {
            ((HomeActivity) getActivity()).showRightOption(HomeActivity.ADD_FISH_OPTION, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.add(R.id.content_frame, new AddFishFragment()).hide(MyFishFragment.this).addToBackStack(null).commit();
                }
            });
        }

    }

    class MyFishListBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.MYFISH_LIST_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                    fishList = (MyFishResponse) intent.getSerializableExtra("data");
                    if (fishList.size() > 0) {
                        mFishListAdapter = new FishListAdapter(getActivity(), fishList);
                        mFishList.setAdapter(mFishListAdapter);
                        mFishList.setOnItemClickListener(MyFishFragment.this);
                    } else {
                        dialog = new AlertMessageDialog(((HomeActivity) getActivity()), getActivity().getString(R.string.error_txt), getString(R.string.empty_list));
                        dialog.setAcceptButtonText(getString(R.string.ok_txt));
                        dialog.show();

                    }

                }

            }
        }
    }

    class LoadFishBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.LOAD_FISH_CALLBACK_BROADCAST)) {
                loaderManager.initLoader(R.id.loader_fish_list, null, new MyFishCallback(((AbstractActivity) getActivity()), true, FishingPreferences.getInstance().getCurrentUserId()));
            }
        }
    }

}
