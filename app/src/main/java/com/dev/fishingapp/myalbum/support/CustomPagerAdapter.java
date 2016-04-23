package com.dev.fishingapp.myalbum.support;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dev.fishingapp.R;
import com.dev.fishingapp.util.DotsProgressBar;
import com.dev.fishingapp.util.FishingAppHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

/**
 * Created by skumari on 4/23/2016.
 */
public class CustomPagerAdapter extends PagerAdapter {
    ArrayList<String> urlList;
    Context mContext;
    LayoutInflater mLayoutInflater;
    private DotsProgressBar dotsProgressBar;
    private DisplayImageOptions options;

    public CustomPagerAdapter(Context context, ArrayList<String> urlList) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.urlList = urlList;
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.viewpager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.grid_item_image);
        dotsProgressBar = new DotsProgressBar(mContext, imageView);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(dotsProgressBar)
                .showImageForEmptyUri(R.drawable.no_image)
                .showImageOnFail(R.drawable.no_image)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        FishingAppHelper.getImageLoader().displayImage(urlList.get(position), imageView, options);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
