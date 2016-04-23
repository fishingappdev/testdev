package com.dev.fishingapp.myfriends.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.Friend;
import com.dev.fishingapp.myfriends.FriendDetailsActivity;
import com.dev.fishingapp.myfriends.support.MyFriendsListAdapter;
import com.dev.fishingapp.support.BaseToolbarFragment;

import java.util.ArrayList;

/**
 * Created by user on 4/18/2016.
 */
public class MyFriendsFragment extends BaseToolbarFragment implements AdapterView.OnItemClickListener {
    private ListView mFriendListView;
    private ArrayList<Friend> mFriendList;
    private MyFriendsListAdapter myFriendsListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmnet_myfriends, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) getActivity()).setToolBarTitle(getResources().getString(R.string.my_friends_header));
        mFriendListView = (ListView) view.findViewById(R.id.myfriend_list);
        ((HomeActivity) getActivity()).showRightOption(HomeActivity.SEARCH_OPTION, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mFriendList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Friend myFriend = new Friend("BIG RED", null);
            mFriendList.add(myFriend);
        }
        myFriendsListAdapter = new MyFriendsListAdapter(getActivity(), mFriendList);
        mFriendListView.setAdapter(myFriendsListAdapter);
        mFriendListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(getActivity(), FriendDetailsActivity.class));
    }
}
