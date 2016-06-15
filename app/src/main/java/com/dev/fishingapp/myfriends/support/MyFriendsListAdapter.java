package com.dev.fishingapp.myfriends.support;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.Friend;
import com.dev.fishingapp.util.DotsProgressBar;
import com.dev.fishingapp.util.FishingAppHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 4/21/2016.
 */
public class MyFriendsListAdapter extends BaseAdapter implements Filterable {
    private List<Friend> myFriendsArrayList;
    private Context context;
    LayoutInflater inflater;
    private DotsProgressBar dotsProgressBar;
    DisplayImageOptions options;
    List<Friend> mOriginalValues;
    List<String> mListItem; // any k
    boolean showDetails;

    public MyFriendsListAdapter(Context context, ArrayList<Friend> myFriendsArrayList, boolean showDetails) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.myFriendsArrayList = myFriendsArrayList;
        this.showDetails = showDetails;
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
        mViewHolder.tvTitle.setText(myFriendsArrayList.get(position).getName());
        dotsProgressBar = new DotsProgressBar(context, mViewHolder.ivIcon);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(dotsProgressBar)
                .showImageForEmptyUri(R.drawable.cf_list_no_image_thumbnail)
                .showImageOnFail(R.drawable.cf_list_no_image_thumbnail)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        FishingAppHelper.getImageLoader().displayImage(currentFriend.getPicture(), mViewHolder.ivIcon, options);
        if (!showDetails) {
            mViewHolder.arrow.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        /**
         * A filter object which will
         * filter message key
         * */
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                myFriendsArrayList = (List<Friend>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values. Only filtered values will be shown on the list
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation for publishing

                List<Friend> FilteredArrList = new ArrayList<Friend>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<Friend>(myFriendsArrayList); // saves the original data in mOriginalValues
                }

                if (mListItem == null) {
                    mListItem = new ArrayList<String>();
                    for (Friend friend : mOriginalValues) {
                        mListItem.add(friend.getName());
                    }
                }

                /**
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 **/

                if (constraint == null || constraint.length() == 0) {

                    /* CONTRACT FOR IMPLEMENTING FILTER : set the Original values to result which will be returned for publishing */
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    /* Do the filtering */
                    constraint = constraint.toString().toLowerCase();

                    for (int i = 0; i < mListItem.size(); i++) {
                        String data = mListItem.get(i);
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(mOriginalValues.get(i));
                        }
                    }

                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

    private class MyViewHolder {
        TextView tvTitle;
        ImageView ivIcon;
        ImageView arrow;

        public MyViewHolder(View item) {
            tvTitle = (TextView) item.findViewById(R.id.title);
            ivIcon = (ImageView) item.findViewById(R.id.iv_icon);
            arrow = (ImageView) item.findViewById(R.id.arrow);
        }
    }
}
