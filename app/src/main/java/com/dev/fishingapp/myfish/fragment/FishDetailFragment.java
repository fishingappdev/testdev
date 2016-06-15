package com.dev.fishingapp.myfish.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.fishingapp.AbstractActivity;
import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.LoaderCallbacks.FishDetailCallback;
import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.FishDetailResponse;
import com.dev.fishingapp.data.model.MyFish;
import com.dev.fishingapp.support.BaseToolbarFragment;
import com.dev.fishingapp.util.AlertMessageDialog;
import com.dev.fishingapp.util.AppConstants;
import com.dev.fishingapp.util.DotsProgressBar;
import com.dev.fishingapp.util.FishingAppHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * Created by user on 4/21/2016.
 */
public class FishDetailFragment extends BaseToolbarFragment {
    MyFish fishDetail;
    private TextView mDescription,mTitle,mDate,fishLength,fishWeight,location;
    private String nId;
    private LoaderManager loaderManager;
    private FishDetailBroadcastReceiver receiver;
    private AlertMessageDialog dialog;
    private ImageView fishImage;
    private DotsProgressBar dotsProgressBar;
    DisplayImageOptions options;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        nId= args.getString("nid");
        return inflater.inflate(R.layout.fragment_fish_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loaderManager = getActivity().getSupportLoaderManager();
        mDescription=(TextView)view.findViewById(R.id.detail);
        mTitle=(TextView)view.findViewById(R.id.fishTitle);
        mDate=(TextView)view.findViewById(R.id.date);
        fishLength=(TextView)view.findViewById(R.id.fish_length);
        fishWeight=(TextView)view.findViewById(R.id.fish_weight);
        location=(TextView)view.findViewById(R.id.where_caught_view);
        fishImage=(ImageView)view.findViewById(R.id.fish_image);

        dotsProgressBar = new DotsProgressBar(getActivity(), fishImage);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(dotsProgressBar)
                .showImageForEmptyUri(R.drawable.cf_list_no_image_thumbnail)
                .showImageOnFail(R.drawable.cf_list_no_image_thumbnail)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();



        ((HomeActivity) getActivity()).showRightOption(HomeActivity.ADD_FISH_OPTION, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().add(R.id.content_frame, new AddFishFragment()).hide(FishDetailFragment.this).addToBackStack(null).commit();
*/            addFragmentWithBackStack(new AddFishFragment());
            }
        });


        if(loaderManager.getLoader(R.id.loader_fish_detail)== null){
            loaderManager.initLoader(R.id.loader_fish_detail, null, new FishDetailCallback(((AbstractActivity) getActivity()),true,nId));
        } else {
            loaderManager.restartLoader(R.id.loader_fish_detail, null, new FishDetailCallback(((AbstractActivity) getActivity()),true,nId));
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            ((HomeActivity) getActivity()).showRightOption(HomeActivity.ADD_FISH_OPTION, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.beginTransaction().add(R.id.content_frame, new AddFishFragment()).hide(FishDetailFragment.this).addToBackStack(null).commit();
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity)getActivity()).setCurrentFragment(FishDetailFragment.this);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter(AppConstants.FISH_DETAIL_CALLBACK_BROADCAST);
        receiver = new FishDetailBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.unregisterReceiver(receiver);
    }

    class FishDetailBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.FISH_DETAIL_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                    FishDetailResponse fishResponse= (FishDetailResponse)intent.getSerializableExtra("data");
                    if(fishResponse.size()>0){
                       setData(fishResponse);
                    }else{
                        dialog=new AlertMessageDialog(((HomeActivity)getActivity()),getActivity().getString(R.string.error_txt),getString(R.string.empty_list));
                        dialog.setAcceptButtonText(getString(R.string.ok_txt));
                        dialog.show();

                    }

                }

            }
        }
    }

    private void setData(FishDetailResponse fishResponse){
        mTitle.setText(fishResponse.get(0).getTitle());
        mDescription.setText(fishResponse.get(0).getDescription());
        mDate.setText(fishResponse.get(0).getField_date_value());
        fishLength.setText(fishResponse.get(0).getFish_length());
        fishWeight.setText(fishResponse.get(0).getWeight());
        location.setText(fishResponse.get(0).getWhere_caught());
        FishingAppHelper.getImageLoader().displayImage(fishResponse.get(0).getImageurl(), fishImage, options);


    }
}
