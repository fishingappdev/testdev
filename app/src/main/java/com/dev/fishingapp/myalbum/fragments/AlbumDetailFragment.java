package com.dev.fishingapp.myalbum.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.fishingapp.LoaderCallbacks.MyAlbumDetailCallback;
import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.MyAlbumDetailResponse;
import com.dev.fishingapp.data.model.MyAlbumDetails;
import com.dev.fishingapp.myalbum.support.CustomGridAdapter;
import com.dev.fishingapp.myalbum.support.CustomPagerAdapter;
import com.dev.fishingapp.support.BaseToolbarFragment;
import com.dev.fishingapp.util.AlertMessageDialog;
import com.dev.fishingapp.util.AppConstants;

import java.util.ArrayList;

/**
 * Created by user on 4/23/2016.
 */
public class AlbumDetailFragment extends BaseToolbarFragment implements View.OnClickListener, ViewPager.OnPageChangeListener, GridView.OnItemClickListener {
    private ViewPager pager;
    private GridView gridView;
    private ArrayList<String> urlList;
    private CustomGridAdapter customGridAdapter;
    private CustomPagerAdapter customPagerAdapter;
    private ImageView nxtBtn, prevBtn;
    private String nid;
    private LoaderManager loaderManager;
    private MyAlbumBroadcastReceiver receiver;
    private TextView description, location, country_list;
    private LinearLayout comment_list;
    LayoutInflater inflater;
    private Button add_comments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        nid = getArguments().getString("nid");
        return inflater.inflate(R.layout.fragment_album_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        urlList = new ArrayList<>();
        pager = (ViewPager) view.findViewById(R.id.pager);
        gridView = (GridView) view.findViewById(R.id.gridLayout);
        nxtBtn = (ImageView) view.findViewById(R.id.nextBtn);
        prevBtn = (ImageView) view.findViewById(R.id.prevBtn);
        description = (TextView) view.findViewById(R.id.description);
        location = (TextView) view.findViewById(R.id.location);
        country_list = (TextView) view.findViewById(R.id.country);
        comment_list = (LinearLayout) view.findViewById(R.id.comment_list);
        add_comments = (Button) view.findViewById(R.id.add_comments);
        inflater = LayoutInflater.from(getActivity());
        loaderManager = getActivity().getSupportLoaderManager();
        add_comments.setOnClickListener(this);
        loaderManager.initLoader(R.id.loader_myalbumdetails, null, new MyAlbumDetailCallback((AppCompatActivity) getActivity(), true, nid));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nextBtn: {
                int curIndex = pager.getCurrentItem();
                if (curIndex < urlList.size() - 1) {
                    pager.setCurrentItem(curIndex + 1, true);
                }
            }
            break;
            case R.id.prevBtn: {
                int curIndex = pager.getCurrentItem();
                if (curIndex > 0) {
                    pager.setCurrentItem(curIndex - 1, true);
                }
            }
            break;
            case R.id.add_comments: {

            }
            break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        customGridAdapter.setSelectedIndexAndUpdate(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        pager.setCurrentItem(i, true);
    }

    class MyAlbumBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.MY_ALBUM_DETAIL_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                    MyAlbumDetailResponse response = (MyAlbumDetailResponse) intent.getSerializableExtra("data");
                    urlList.clear();
                    if (response.getStatus().equals("success")) {
                        MyAlbumDetails myAlbumDetails = response.getMyalbums().get(0);
                        setUpUI(myAlbumDetails);
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
    public void onResume() {
        super.onResume();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter(AppConstants.MY_ALBUM_DETAIL_CALLBACK_BROADCAST);
        receiver = new MyAlbumBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);

    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.unregisterReceiver(receiver);
    }

    private void setUpUI(MyAlbumDetails myAlbumDetails) {
        urlList.addAll(myAlbumDetails.getImageurl());
        customGridAdapter = new CustomGridAdapter(getActivity(), urlList);
        customPagerAdapter = new CustomPagerAdapter(getActivity(), urlList);
        pager.setAdapter(customPagerAdapter);
        gridView.setAdapter(customGridAdapter);
        pager.setOnPageChangeListener(AlbumDetailFragment.this);
        nxtBtn.setOnClickListener(AlbumDetailFragment.this);
        prevBtn.setOnClickListener(AlbumDetailFragment.this);
        gridView.setOnItemClickListener(AlbumDetailFragment.this);
        description.setText(Html.fromHtml(myAlbumDetails.getBody()));
        if (myAlbumDetails.getCountry() != null && myAlbumDetails.getCountry().size() > 0) {
            String strCountryList = android.text.TextUtils.join(",", myAlbumDetails.getCountry());
            country_list.setText(strCountryList);
        }

        location.setText(myAlbumDetails.getLocation().getName() + "\n" + myAlbumDetails.getLocation().getStreet() + "\n" + myAlbumDetails.getLocation().getCountry_name());
        if (myAlbumDetails.getComment() != null && myAlbumDetails.getComment().size() > 0) {
            ArrayList<String> commentList = myAlbumDetails.getComment();
            for (String comment : commentList) {
                TextView comment_title, comment_description, submitted_by;
                LinearLayout view = (LinearLayout) inflater.inflate(R.layout.album_comment_row, comment_list, false);
                comment_title = (TextView) view.findViewById(R.id.comment_title);
                comment_description = (TextView) view.findViewById(R.id.comment_description);
                submitted_by = (TextView) view.findViewById(R.id.submitted_by);
                comment_title.setText(comment);
                comment_list.addView(view);
            }
        }

    }
}
