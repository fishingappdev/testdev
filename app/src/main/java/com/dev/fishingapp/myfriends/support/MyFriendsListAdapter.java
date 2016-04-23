package com.dev.fishingapp.myfriends.support;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.Friend;
import com.dev.fishingapp.util.DotsProgressBar;
import com.dev.fishingapp.util.FishingAppHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

/**
 * Created by user on 4/21/2016.
 */
public class MyFriendsListAdapter extends BaseAdapter {
    private ArrayList<Friend> myFriendsArrayList;
    private Context context;
    LayoutInflater inflater;
    private DotsProgressBar dotsProgressBar;
    DisplayImageOptions options;

    public MyFriendsListAdapter(Context context, ArrayList<Friend> myFriendsArrayList) {
        this.context = context;
        this.myFriendsArrayList = myFriendsArrayList;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myFriendsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return myFriendsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.album_list_item_view, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        Friend currentFriend = myFriendsArrayList.get(position);
        mViewHolder.tvTitle.setText(myFriendsArrayList.get(position).getTitle());
        dotsProgressBar = new DotsProgressBar(context, mViewHolder.ivIcon);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(dotsProgressBar)
                .showImageForEmptyUri(R.drawable.cf_list_no_image_thumbnail)
                .showImageOnFail(R.drawable.cf_list_no_image_thumbnail)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        FishingAppHelper.getImageLoader().displayImage(currentFriend.getImageUrl(), mViewHolder.ivIcon, options);
        return convertView;
    }

    private class MyViewHolder {
        TextView tvTitle;
        ImageView ivIcon;

        public MyViewHolder(View item) {
            tvTitle = (TextView) item.findViewById(R.id.title);
            ivIcon = (ImageView) item.findViewById(R.id.iv_icon);
        }
    }
}
