package com.dev.fishingapp.myfriends.fragments;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.LoaderCallbacks.FriendsCallback;
import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.Friend;
import com.dev.fishingapp.data.model.FriendsResponse;
import com.dev.fishingapp.myfriends.FriendDetailsActivity;
import com.dev.fishingapp.myfriends.support.MyFriendsListAdapter;
import com.dev.fishingapp.support.BaseToolbarFragment;
import com.dev.fishingapp.util.AlertMessageDialog;
import com.dev.fishingapp.util.AppConstants;
import com.dev.fishingapp.util.FishingPreferences;

import java.util.ArrayList;

/**
 * Created by user on 4/18/2016.
 */
public class MyFriendsFragment extends BaseToolbarFragment implements AdapterView.OnItemClickListener {
    private ListView mFriendListView;
    private ArrayList<Friend> mFriendList;
    private MyFriendsListAdapter myFriendsListAdapter;
    private LoaderManager loaderManager;
    private FriendBroadcastReceiver receiver;
    private boolean isNavigation;
    private String uid;
    private boolean find_friends;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isNavigation = bundle.getBoolean("locked", false);
            uid = bundle.getString("uid");
            find_friends = bundle.getBoolean("find_friends", false);
        }
        if (uid == null) {
            uid = FishingPreferences.getInstance().getCurrentUserId();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragmnet_myfriends, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (find_friends)
            ((HomeActivity) getActivity()).setToolBarTitle(getResources().getString(R.string.find_friends_header));
        else
            ((HomeActivity) getActivity()).setToolBarTitle(getResources().getString(R.string.my_friends_header));
        mFriendListView = (ListView) view.findViewById(R.id.myfriend_list);
        loaderManager = getActivity().getSupportLoaderManager();
        loaderManager.initLoader(R.id.loader_friends, null, new FriendsCallback((AppCompatActivity) getActivity(), true, "all", uid, find_friends));
        mFriendList = new ArrayList<>();
        boolean myfrens=uid.equals(FishingPreferences.getInstance().getCurrentUserId());
        myFriendsListAdapter = new MyFriendsListAdapter(getActivity(), mFriendList, myfrens);
        mFriendListView.setAdapter(myFriendsListAdapter);
        if (myfrens) {
            mFriendListView.setOnItemClickListener(this);
        } else {
            mFriendListView.setEnabled(false);
            mFriendListView.setOnItemClickListener(null);
        }
        mFriendListView.setTextFilterEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), FriendDetailsActivity.class);
            intent.putExtra("userId", mFriendList.get(position).getUid());
            startActivity(intent);
    }

    class FriendBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.FRIEND_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                    FriendsResponse response = (FriendsResponse) intent.getSerializableExtra("data");
                    if (response.getStatus().equals("success")) {
                        mFriendList.clear();
                        mFriendList.addAll(response.getData());
                        myFriendsListAdapter.notifyDataSetChanged();
                    } else {
                        AlertMessageDialog dialog = new AlertMessageDialog(getActivity(), getString(R.string.error_txt), getString(R.string.some_error_occured));
                        dialog.setAcceptButtonText(getString(R.string.ok_txt));
                        dialog.show();
                    }
                }

            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter(AppConstants.FRIEND_CALLBACK_BROADCAST);
        receiver = new FriendBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.unregisterReceiver(receiver);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (TextUtils.isEmpty(newText)) {
                        mFriendListView.clearTextFilter();
                    } else {
                        mFriendListView.setFilterText(newText.toString());
                    }
                    return true;
                }
            });
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

}
