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
import com.dev.fishingapp.fishinglog.fragment.AddCommentFragment;
import com.dev.fishingapp.myalbum.support.CustomGridAdapter;
import com.dev.fishingapp.myalbum.support.CustomPagerAdapter;
import com.dev.fishingapp.support.BaseToolbarFragment;
import com.dev.fishingapp.util.AlertMessageDialog;
import com.dev.fishingapp.util.AppConstants;
import com.dev.fishingapp.util.Utils;

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
    private LinearLayout mCommentLayout;
    private Button mCommentBtn;
    private AddCommentBroadcastReceiver coomentReceiver;

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
        mCommentBtn = (Button) view.findViewById(R.id.CommentBtn);
        mCommentLayout = (LinearLayout) view.findViewById(R.id.commentView);
        inflater = LayoutInflater.from(getActivity());
        loaderManager = getActivity().getSupportLoaderManager();
        loadAlbumDetails();
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
    public void onAttach(Context context) {
        super.onAttach(context);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter(AppConstants.MY_ALBUM_DETAIL_CALLBACK_BROADCAST);
        receiver = new MyAlbumBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);
        IntentFilter commentFilter = new IntentFilter(AppConstants.LOAD_COMMENT_CALLBACK_BROADCAST);
        coomentReceiver = new AddCommentBroadcastReceiver();
        localBroadcastManager.registerReceiver(coomentReceiver, commentFilter);
    }

    @Override
    public void onDetach() {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.unregisterReceiver(receiver);
        super.onDetach();
    }

    private void setUpUI(final MyAlbumDetails myAlbumDetails) {
        if(myAlbumDetails.getComment().size()==0){
            mCommentLayout.setVisibility(View.GONE);
        }else {
            mCommentLayout.setVisibility(View.VISIBLE);
        }
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

        mCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddCommentFragment.class);
                intent.putExtra("nid", myAlbumDetails.getNid());
                startActivity(intent);

            }
        });

        location.setText(myAlbumDetails.getLocation().getName() + "\n" + myAlbumDetails.getLocation().getStreet() + "\n" + myAlbumDetails.getLocation().getCountry_name());
        for (int i = 0; i < myAlbumDetails.getComment().size(); i++) {
            View layout2 = LayoutInflater.from(getActivity()).inflate(R.layout.view_comment, mCommentLayout, false);
            TextView title = (TextView) layout2.findViewById(R.id.commentTitle);
            TextView description = (TextView) layout2.findViewById(R.id.commentBody);
            TextView author = (TextView) layout2.findViewById(R.id.commentAuthor);
            title.setText(myAlbumDetails.getComment().get(i).getSubject());
            description.setText(myAlbumDetails.getComment().get(i).getComment_body());
            author.setText(getString(R.string.submitted_by) + " " + myAlbumDetails.getComment().get(i).getRegistered_name() + " on " + Utils.ConvertMilliSecondsToFormattedDate(myAlbumDetails.getComment().get(i).getCreated()));
            mCommentLayout.addView(layout2);
        }

    }

    public class AddCommentBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.LOAD_COMMENT_CALLBACK_BROADCAST)) {
                loadAlbumDetails();
            }
        }
    }

    private void loadAlbumDetails() {
        loaderManager.initLoader(R.id.loader_myalbumdetails, null, new MyAlbumDetailCallback((AppCompatActivity) getActivity(), true, nid));
    }

}
