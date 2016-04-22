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

import java.util.ArrayList;

/**
 * Created by user on 4/21/2016.
 */
public class FishListAdapter extends BaseAdapter {
    private ArrayList<MyFish> myFishArrayList;
    private Context context;
    LayoutInflater inflater;
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

       mViewHolder.tvTitle.setText(myFishArrayList.get(position).getFishType());
        mViewHolder.tvLength.setText(myFishArrayList.get(position).getLength());
        mViewHolder.tvWeight.setText(myFishArrayList.get(position).getWeight());
        mViewHolder.tvLocation.setText(myFishArrayList.get(position).getWhereCaught());
        mViewHolder.tvDate.setText(myFishArrayList.get(position).getDate());



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
