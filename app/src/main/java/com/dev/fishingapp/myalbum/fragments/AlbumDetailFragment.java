package com.dev.fishingapp.myalbum.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.dev.fishingapp.R;
import com.dev.fishingapp.myalbum.support.CustomGridAdapter;
import com.dev.fishingapp.myalbum.support.CustomPagerAdapter;
import com.dev.fishingapp.support.BaseToolbarFragment;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        for (int i = 0; i < 10; i++) {
            urlList.add("");
        }
        customGridAdapter = new CustomGridAdapter(getActivity(), urlList);
        customPagerAdapter = new CustomPagerAdapter(getActivity(), urlList);
        pager.setAdapter(customPagerAdapter);
        gridView.setAdapter(customGridAdapter);
        pager.setOnPageChangeListener(this);
        nxtBtn.setOnClickListener(this);
        prevBtn.setOnClickListener(this);
        gridView.setOnItemClickListener(this);
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
}
