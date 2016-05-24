package com.dev.fishingapp.support;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.FishingRecord;
import com.dev.fishingapp.util.DotsProgressBar;
import com.dev.fishingapp.util.FishingAppHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

/**
 * Created by user on 5/24/2016.
 */
public class FishingRecordAdapter extends BaseAdapter {
    private ArrayList<FishingRecord> recordList;
    private Context context;
    LayoutInflater inflater;
    private DotsProgressBar dotsProgressBar;
    DisplayImageOptions options;
    public FishingRecordAdapter(Context context,ArrayList<FishingRecord> recordList){
        this.context=context;
        this.recordList=recordList;
        inflater = LayoutInflater.from(this.context);
    }
    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int position) {
        return recordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fish_record_list_item, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        mViewHolder.tvTitle.setText(recordList.get(position).getTitle());
        mViewHolder.tvDate.setText(recordList.get(position).getField_date());
        mViewHolder.type.setText(recordList.get(position).getRecord_type());
        mViewHolder.tvWhereCaught.setText(recordList.get(position).getWhere_caught());
        dotsProgressBar = new DotsProgressBar(context, mViewHolder.ivIcon);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(dotsProgressBar)
                .showImageForEmptyUri(R.drawable.cf_list_no_image_thumbnail)
                .showImageOnFail(R.drawable.cf_list_no_image_thumbnail)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        FishingAppHelper.getImageLoader().displayImage(recordList.get(position).getImageurl(), mViewHolder.ivIcon, options);


        return convertView;
    }

    private class MyViewHolder {
        TextView tvTitle, type,tvDate,tvWhereCaught;
        ImageView ivIcon;

        public MyViewHolder(View item) {
            tvTitle = (TextView) item.findViewById(R.id.title);
            type = (TextView) item.findViewById(R.id.type);
            tvDate = (TextView) item.findViewById(R.id.date_caught);
            tvWhereCaught=(TextView)item.findViewById(R.id.where_caught);
            ivIcon = (ImageView) item.findViewById(R.id.fish_ic);
        }
    }
}
