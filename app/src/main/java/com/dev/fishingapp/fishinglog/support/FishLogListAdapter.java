package com.dev.fishingapp.fishinglog.support;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.FishingLogData;

import java.util.ArrayList;

/**
 * Created by user on 4/23/2016.
 */
public class FishLogListAdapter extends BaseAdapter {
    private ArrayList<FishingLogData> myFishArrayList;
    private Context context;
    LayoutInflater inflater;
    boolean showDetails;
    public FishLogListAdapter(Context context, ArrayList<FishingLogData> myFishList, boolean toShowDetails){
        this.context=context;
        this.myFishArrayList=myFishList;
        inflater = LayoutInflater.from(this.context);
        this.showDetails=toShowDetails;
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
            convertView = inflater.inflate(R.layout.view_fishlog_item, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        mViewHolder.tvLogTitle.setText(myFishArrayList.get(position).getTitle());
        mViewHolder.tvLogDetail.setText(myFishArrayList.get(position).getDescription());
        mViewHolder.tvLogDate.setText(myFishArrayList.get(position).getDate());
        mViewHolder.tvLogLocation.setText(myFishArrayList.get(position).getLocation());
        if (!showDetails)  {
            mViewHolder.arrow.setVisibility(View.GONE);
        }
        return convertView;
    }

    private class MyViewHolder {
        TextView tvLogTitle, tvLogDetail,tvLogDate,tvLogLocation;
        ImageView arrow;

        public MyViewHolder(View item) {
            tvLogTitle = (TextView) item.findViewById(R.id.fishlog_title);
            tvLogDetail = (TextView) item.findViewById(R.id.log_detail);
            tvLogDate = (TextView) item.findViewById(R.id.log_date);
            tvLogLocation=(TextView)item.findViewById(R.id.place);
            arrow = (ImageView) item.findViewById(R.id.arrow);
        }
    }
}

