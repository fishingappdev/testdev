package com.dev.fishingapp.myalbum.support;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.dev.fishingapp.R;
import com.dev.fishingapp.util.DotsProgressBar;
import com.dev.fishingapp.util.FishingAppHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

/**
 * Created by skumari on 4/23/2016.
 */
public class CustomGridAdapter extends BaseAdapter {

    private Context context;
    private final ArrayList<String> gridValues;
    private DotsProgressBar dotsProgressBar;
    private DisplayImageOptions options;
    int selectedIndex;

    public CustomGridAdapter(Context context, ArrayList<String> gridValues) {
        this.context = context;
        this.gridValues = gridValues;
        selectedIndex = 0;
    }

    @Override
    public int getCount() {
        return gridValues.size();
    }

    @Override
    public Object getItem(int position) {
        return gridValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        if (convertView == null) {
            gridView = inflater.inflate(R.layout.grid_item, null);
        } else {
            gridView = convertView;
        }
        ImageView imageView = (ImageView) gridView
                .findViewById(R.id.grid_item_image);
        dotsProgressBar = new DotsProgressBar(context, imageView);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(dotsProgressBar)
                .showImageForEmptyUri(R.drawable.cf_list_no_image_thumbnail)
                .showImageOnFail(R.drawable.cf_list_no_image_thumbnail)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        FishingAppHelper.getImageLoader().displayImage(gridValues.get(position), imageView, options);
        if (position == selectedIndex) {
            imageView.setBackgroundColor(context.getResources().getColor(R.color.drawer_bg));
        } else {
            imageView.setBackgroundColor(Color.TRANSPARENT);
        }
        return gridView;
    }

    public void setSelectedIndexAndUpdate(int selectedIndex) {
        this.selectedIndex = selectedIndex;
        notifyDataSetChanged();
    }
}
