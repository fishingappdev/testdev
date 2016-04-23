package com.dev.fishingapp.support;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dev.fishingapp.R;

import java.util.ArrayList;

/**
 * Created by user on 4/23/2016.
 */
public class EpisodeListAdapter extends BaseAdapter {
    private ArrayList<String> episodeList;
    private Context context;
    LayoutInflater inflater;
    public EpisodeListAdapter(Context context,ArrayList<String> episodeList){
        this.context=context;
        this.episodeList=episodeList;
        inflater = LayoutInflater.from(this.context);
    }
    @Override
    public int getCount() {
        return episodeList.size();
    }

    @Override
    public Object getItem(int position) {
        return episodeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_episode_list_item, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        mViewHolder.tvEpisodeTitle.setText(episodeList.get(position));



        return convertView;
    }

    private class MyViewHolder {
        TextView tvEpisodeTitle;

        public MyViewHolder(View item) {
            tvEpisodeTitle = (TextView) item.findViewById(R.id.episodeTitle);


        }
    }
}

