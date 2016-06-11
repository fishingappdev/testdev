package com.dev.fishingapp.myalbum.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.LoaderCallbacks.MyAlbumCallback;
import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.MyAlbum;
import com.dev.fishingapp.data.model.MyAlbumResponse;
import com.dev.fishingapp.myalbum.support.MyAlbumListAdapter;
import com.dev.fishingapp.support.BaseToolbarFragment;
import com.dev.fishingapp.util.AlertMessageDialog;
import com.dev.fishingapp.util.AppConstants;
import com.dev.fishingapp.util.FishingPreferences;

import java.util.ArrayList;

/**
 * Created by user on 4/18/2016.
 */
public class MyAlbumFragment extends BaseToolbarFragment implements AdapterView.OnItemClickListener {
    private ListView mAlbumList;
    private ArrayList<MyAlbum> myAlbumArraylist;
    private MyAlbumListAdapter myAlbumListAdapter;
    private LoaderManager loaderManager;
    private MyAlbumBroadcastReceiver receiver;
    private LoadAlbumBroadcastReceiver loadAlbumBroadcastReceiver;
    private boolean isNavigation;
    private String uid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isNavigation = bundle.getBoolean("locked", false);
            uid = bundle.getString("uid");
        }
        if (uid == null) {
            uid = FishingPreferences.getInstance().getCurrentUserId();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myalbum, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) getActivity()).setToolBarTitle(getResources().getString(R.string.my_albums_header));
        mAlbumList = (ListView) view.findViewById(R.id.myalbum_list);
        if (uid.equals(FishingPreferences.getInstance().getCurrentUserId())) {
            ((HomeActivity) getActivity()).showRightOption(HomeActivity.CAMERA_OPTION, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddAlbumDialogFragment performDialogFragment = new AddAlbumDialogFragment();
                    performDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Dialog);
                    performDialogFragment.setCancelable(true);
                    performDialogFragment.setTargetFragment(MyAlbumFragment.this, 1001);
                    ((HomeActivity) getActivity()).executeFragment(performDialogFragment);

                }
            });
        }
        loaderManager = getActivity().getSupportLoaderManager();
        loaderManager.initLoader(R.id.loader_myalbum, null, new MyAlbumCallback((AppCompatActivity) getActivity(), true, FishingPreferences.getInstance().getCurrentUserId()));
        myAlbumArraylist = new ArrayList<>();
        myAlbumListAdapter = new MyAlbumListAdapter(getActivity(), myAlbumArraylist);
        mAlbumList.setAdapter(myAlbumListAdapter);
        mAlbumList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (uid.equals(FishingPreferences.getInstance().getCurrentUserId())) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putString("nid", myAlbumArraylist.get(position).getNid());
            AlbumDetailFragment frag = new AlbumDetailFragment();
            frag.setArguments(bundle);
            ((HomeActivity)getActivity()).setCurrentFragment(frag);
            fm.beginTransaction().replace(R.id.content_frame, frag).addToBackStack("back").commit();
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            ((HomeActivity) getActivity()).showRightOption(HomeActivity.CAMERA_OPTION, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddAlbumDialogFragment performDialogFragment = new AddAlbumDialogFragment();
                    performDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Dialog);
                    performDialogFragment.setCancelable(true);
                    performDialogFragment.setTargetFragment(MyAlbumFragment.this, 1001);
                    ((HomeActivity) getActivity()).executeFragment(performDialogFragment);

                }
            });
        }
    }

    class MyAlbumBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.MY_ALBUM_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                    MyAlbumResponse response = (MyAlbumResponse) intent.getSerializableExtra("data");
                    if (response.getStatus().equals("success")) {
                        myAlbumArraylist.clear();
                        myAlbumArraylist.addAll(response.getMyalbums());
                        myAlbumListAdapter.notifyDataSetChanged();
                    } else {
                        AlertMessageDialog dialog = new AlertMessageDialog(getActivity(), getString(R.string.error_txt), getString(R.string.some_error_occured));
                        dialog.setAcceptButtonText(getString(R.string.ok_txt));
                        dialog.show();
                    }
                }

            }
        }
    }

    class LoadAlbumBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.LOAD_ALBUM_LIST_CALLBACK_BROADCAST)) {
                loaderManager.initLoader(R.id.loader_myalbum, null, new MyAlbumCallback((AppCompatActivity) getActivity(), true, uid));
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter(AppConstants.MY_ALBUM_CALLBACK_BROADCAST);
        receiver = new MyAlbumBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);
        intentFilter = new IntentFilter(AppConstants.LOAD_ALBUM_LIST_CALLBACK_BROADCAST);
        loadAlbumBroadcastReceiver = new LoadAlbumBroadcastReceiver();
        localBroadcastManager.registerReceiver(loadAlbumBroadcastReceiver, intentFilter);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.unregisterReceiver(receiver);
        localBroadcastManager.unregisterReceiver(loadAlbumBroadcastReceiver);
    }
}
