package com.dev.fishingapp.myalbum.support;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.MyAlbum;
import com.dev.fishingapp.util.DotsProgressBar;
import com.dev.fishingapp.util.FishingAppHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

/**
 * Created by user on 4/21/2016.
 */
public class MyAlbumListAdapter extends BaseAdapter {
    private ArrayList<MyAlbum> myAlbumArrayList;
    private Context context;
    LayoutInflater inflater;
    private DotsProgressBar dotsProgressBar;
    DisplayImageOptions options;

    public MyAlbumListAdapter(Context context, ArrayList<MyAlbum> myAlbums) {
        this.context = context;
        this.myAlbumArrayList = myAlbums;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myAlbumArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return myAlbumArrayList.get(position);
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

        MyAlbum currentAlbum = myAlbumArrayList.get(position);

        mViewHolder.tvTitle.setText(currentAlbum.getTitle());
        mViewHolder.tvDetails.setText(currentAlbum.getDescription());
        mViewHolder.tvLocation.setText(currentAlbum.getCountry());
        dotsProgressBar = new DotsProgressBar(context, mViewHolder.ivIcon);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(dotsProgressBar)
                .showImageForEmptyUri(R.drawable.cf_list_no_image_thumbnail)
                .showImageOnFail(R.drawable.cf_list_no_image_thumbnail)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        FishingAppHelper.getImageLoader().displayImage(currentAlbum.getImageUrl(), mViewHolder.ivIcon, options);
        return convertView;
    }

    private class MyViewHolder {
        TextView tvTitle, tvDetails, tvLocation;
        ImageView ivIcon;

        public MyViewHolder(View item) {
            tvTitle = (TextView) item.findViewById(R.id.title);
            tvDetails = (TextView) item.findViewById(R.id.details);
            tvLocation = (TextView) item.findViewById(R.id.location);
            ivIcon = (ImageView) item.findViewById(R.id.iv_icon);
        }
    }
}
