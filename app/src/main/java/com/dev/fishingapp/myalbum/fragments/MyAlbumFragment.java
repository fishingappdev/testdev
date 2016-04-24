package com.dev.fishingapp.myalbum.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.MyAlbum;
import com.dev.fishingapp.myalbum.support.MyAlbumListAdapter;
import com.dev.fishingapp.support.BaseToolbarFragment;

import java.util.ArrayList;

/**
 * Created by user on 4/18/2016.
 */
public class MyAlbumFragment extends BaseToolbarFragment implements AdapterView.OnItemClickListener {
    private ListView mAlbumList;
    private ArrayList<MyAlbum> myAlbumArraylist;
    private MyAlbumListAdapter myAlbumListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myalbum, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) getActivity()).setToolBarTitle(getResources().getString(R.string.my_albums_header));
        mAlbumList = (ListView) view.findViewById(R.id.myalbum_list);
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
        myAlbumArraylist = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MyAlbum myAlbum = new MyAlbum("BIG FISH", "Lorem Ipsum is simply dummy text of the printing and typesetting industry", null, "Australia");
            myAlbumArraylist.add(myAlbum);
        }
        myAlbumListAdapter = new MyAlbumListAdapter(getActivity(), myAlbumArraylist);
        mAlbumList.setAdapter(myAlbumListAdapter);
        mAlbumList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager fm= getActivity().getSupportFragmentManager();
        fm.beginTransaction().add(R.id.content_frame,new AlbumDetailFragment()).hide(this).addToBackStack(null).commit();

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
}
