package com.dev.fishingapp.support;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.EpisodeList;
import com.dev.fishingapp.util.DotsProgressBar;
import com.dev.fishingapp.util.FishingAppHelper;
import com.dev.fishingapp.util.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

/**
 * Created by user on 4/23/2016.
 */
public class EpisodeListAdapter extends BaseAdapter {
    private ArrayList<EpisodeList> episodeList;
    private Context context;
    LayoutInflater inflater;
    private DotsProgressBar dotsProgressBar;
    DisplayImageOptions options;

    public EpisodeListAdapter(Context context, ArrayList<EpisodeList> episodeList){
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_episode_list_item, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        mViewHolder.tvEpisodeTitle.setText(episodeList.get(position).getTitle());
        dotsProgressBar = new DotsProgressBar(context, mViewHolder.videoThumbnail);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(dotsProgressBar)
                .showImageForEmptyUri(R.drawable.cf_list_no_image_thumbnail)
                .showImageOnFail(R.drawable.cf_list_no_image_thumbnail)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        FishingAppHelper.getImageLoader().displayImage(episodeList.get(position).getFull_video_image(), mViewHolder.videoThumbnail, options);

        mViewHolder.platBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Utils.watchVideo(episodeList.get(position).getFull_video_url(), (Activity) context);
              /*  Intent videoIntent = new Intent(Intent.ACTION_VIEW);
                videoIntent.setDataAndType(Uri.parse(episodeList.get(position).getFull_video_url()), "video*//*");
                context.startActivity(Intent.createChooser(videoIntent, "Complete action using"));*/
            }
        });

        return convertView;
    }

    private class MyViewHolder {
        TextView tvEpisodeTitle;
        ImageView videoThumbnail;
        private ImageButton platBtn;

        public MyViewHolder(View item) {
            tvEpisodeTitle = (TextView) item.findViewById(R.id.episodeTitle);
            videoThumbnail=(ImageView) item.findViewById(R.id.video_thumbnail);
            platBtn=(ImageButton)item.findViewById(R.id.video_play_icon);


        }
    }
}

