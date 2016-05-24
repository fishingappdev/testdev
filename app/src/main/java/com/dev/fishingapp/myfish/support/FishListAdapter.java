package com.dev.fishingapp.myfish.support;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.MyFish;
import com.dev.fishingapp.util.DotsProgressBar;
import com.dev.fishingapp.util.FishingAppHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

/**
 * Created by user on 4/21/2016.
 */
public class FishListAdapter extends BaseAdapter {
    private ArrayList<MyFish> myFishArrayList;
    private Context context;
    LayoutInflater inflater;
    private DotsProgressBar dotsProgressBar;
    DisplayImageOptions options;
    public FishListAdapter(Context context,ArrayList<MyFish> myFishList){
         this.context=context;
        this.myFishArrayList=myFishList;
        inflater = LayoutInflater.from(this.context);
    }
    @Override
    public int getCount() {
        return myFishArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return myFishArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fish_list_item_view, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

      mViewHolder.tvTitle.setText(myFishArrayList.get(position).getTitle());
        mViewHolder.tvLength.setText(myFishArrayList.get(position).getFish_length());
        mViewHolder.tvWeight.setText(myFishArrayList.get(position).getWeight());
        mViewHolder.tvLocation.setText(myFishArrayList.get(position).getWhere_caught());
        mViewHolder.tvDate.setText(myFishArrayList.get(position).getField_date_value());
        dotsProgressBar = new DotsProgressBar(context, mViewHolder.ivIcon);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(dotsProgressBar)
                .showImageForEmptyUri(R.drawable.cf_list_no_image_thumbnail)
                .showImageOnFail(R.drawable.cf_list_no_image_thumbnail)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        FishingAppHelper.getImageLoader().displayImage(myFishArrayList.get(position).getImageurl(), mViewHolder.ivIcon, options);


        return convertView;
    }

    private class MyViewHolder {
        TextView tvTitle, tvLength,tvDate,tvWeight,tvLocation;
        ImageView ivIcon;

        public MyViewHolder(View item) {
            tvTitle = (TextView) item.findViewById(R.id.title);
            tvLength = (TextView) item.findViewById(R.id.length);
            tvWeight = (TextView) item.findViewById(R.id.weight);
            tvLocation=(TextView)item.findViewById(R.id.location);
            tvDate = (TextView) item.findViewById(R.id.date);
            ivIcon = (ImageView) item.findViewById(R.id.fish_ic);
        }
    }
}
