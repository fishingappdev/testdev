package com.dev.fishingapp.fishinglog.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.fishingapp.AbstractActivity;
import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.LoaderCallbacks.FishingLogDetailCallback;
import com.dev.fishingapp.R;
import com.dev.fishingapp.support.BaseToolbarFragment;
import com.dev.fishingapp.util.AlertMessageDialog;
import com.dev.fishingapp.util.AppConstants;
import com.dev.fishingapp.util.Utils;

/**
 * Created by user on 4/23/2016.
 */
public class FishingLogDetail extends BaseToolbarFragment{
    private LoaderManager loaderManager;
    private FishingLogDetailBroadcastReceiver receiver;
    private AddCommentBroadcastReceiver coomentReceiver;
    private AlertMessageDialog dialog;
    private String nId;
    private TextView title,description,date,location,moonTxt,tideTxt,weatherTxt,longitude,latitude;
    private LinearLayout mCommentLayout;
    private Button mCommentBtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        nId= args.getString("nid");
        return inflater.inflate(R.layout.fragment_fishinglog_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loaderManager = getActivity().getSupportLoaderManager();

        title=(TextView)view.findViewById(R.id.logTitle);
        description=(TextView)view.findViewById(R.id.desciption);
        date=(TextView)view.findViewById(R.id.date_label);
        location=(TextView)view.findViewById(R.id.location);
        moonTxt=(TextView)view.findViewById(R.id.moon_txt);
        tideTxt=(TextView)view.findViewById(R.id.tide_txt);
        weatherTxt=(TextView)view.findViewById(R.id.weather_txt);
        longitude=(TextView)view.findViewById(R.id.longitude);
        latitude=(TextView)view.findViewById(R.id.latitude);
        mCommentLayout=(LinearLayout)view.findViewById(R.id.commentView);
        mCommentBtn=(Button)view.findViewById(R.id.CommentBtn);

        ((HomeActivity) getActivity()).setToolBarTitle(getResources().getString(R.string.header_my_fishinglog));
        ((HomeActivity) getActivity()).showRightOption(HomeActivity.ADD_LOG_OPTION, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().add(R.id.content_frame, new AddFishingLog()).hide(FishingLogDetail.this).addToBackStack(null).commit();

            }
        });

        loadContent();

        mCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(),AddCommentFragment.class);
                intent.putExtra("nid",nId);
                startActivity(intent);

            }
        });

    }
    public void loadContent(){
        if(loaderManager.getLoader(R.id.loader_fish_log_detail)== null){
            loaderManager.initLoader(R.id.loader_fish_log_detail, null, new FishingLogDetailCallback(((AbstractActivity) getActivity()),true,nId));
        } else {
            loaderManager.restartLoader(R.id.loader_fish_log_detail, null, new FishingLogDetailCallback(((AbstractActivity) getActivity()),true,nId));
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter commentFilter = new IntentFilter(AppConstants.LOAD_COMMENT_CALLBACK_BROADCAST);
        coomentReceiver = new AddCommentBroadcastReceiver();
        localBroadcastManager.registerReceiver(coomentReceiver, commentFilter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.unregisterReceiver(coomentReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("onResume",">>>>");
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter(AppConstants.FISHING_LOG_DETAIL_CALLBACK_BROADCAST);
        receiver = new FishingLogDetailBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);
       /* IntentFilter commentFilter = new IntentFilter(AppConstants.LOAD_COMMENT_CALLBACK_BROADCAST);
        coomentReceiver = new AddCommentBroadcastReceiver();
        localBroadcastManager.registerReceiver(coomentReceiver, commentFilter);*/
    }



    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.unregisterReceiver(receiver);
       // localBroadcastManager.unregisterReceiver(coomentReceiver);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            ((HomeActivity) getActivity()).showRightOption(HomeActivity.ADD_LOG_OPTION, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.beginTransaction().add(R.id.content_frame, new AddFishingLog()).hide(FishingLogDetail.this).addToBackStack(null).commit();

                }
            });
        }

    }

    class FishingLogDetailBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.FISHING_LOG_DETAIL_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                    com.dev.fishingapp.data.model.FishingLogDetail logDetailResponse= ( com.dev.fishingapp.data.model.FishingLogDetail)intent.getSerializableExtra("data");
                    if(logDetailResponse!=null){
                        setData(logDetailResponse);
                    }else{
                        dialog=new AlertMessageDialog(((HomeActivity)getActivity()),getActivity().getString(R.string.error_txt),getString(R.string.empty_list));
                        dialog.setAcceptButtonText(getString(R.string.ok_txt));
                        dialog.show();

                    }

                }

            }
        }
    }

   public class AddCommentBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.LOAD_COMMENT_CALLBACK_BROADCAST)) {
                    loaderManager.initLoader(R.id.loader_fish_log_detail, null, new FishingLogDetailCallback(((AbstractActivity) getActivity()), true, nId));

            }
        }
    }


    private void setData(com.dev.fishingapp.data.model.FishingLogDetail logDetailResponse){

        if(logDetailResponse.getComment().size()==0){
            mCommentLayout.setVisibility(View.GONE);
        }else {
            mCommentLayout.setVisibility(View.VISIBLE);
        }
        title.setText(logDetailResponse.getTitle());
        description.setText(logDetailResponse.getDescription());
        date.setText(logDetailResponse.getDate());
        longitude.setText(logDetailResponse.getLongitude());
        latitude.setText(logDetailResponse.getLatitude());
        moonTxt.setText(logDetailResponse.getMoon());
        tideTxt.setText(logDetailResponse.getTide());
        weatherTxt.setText(logDetailResponse.getWeather());
        location.setText(logDetailResponse.getLocation());
        mCommentLayout.removeAllViews();

//Add comment section
        for (int i = 0; i < logDetailResponse.getComment().size(); i++) {
            View layout2 = LayoutInflater.from(getActivity()).inflate(R.layout.view_comment, mCommentLayout, false);
            TextView title = (TextView)layout2.findViewById(R.id.commentTitle);
            TextView description = (TextView)layout2.findViewById(R.id.commentBody);
            TextView author=(TextView)layout2.findViewById(R.id.commentAuthor);
            title.setText(logDetailResponse.getComment().get(i).getSubject());
            description.setText(logDetailResponse.getComment().get(i).getComment_body());
            author.setText(getString(R.string.submitted_by)+" "+logDetailResponse.getComment().get(i).getRegistered_name()+" on "+ Utils.ConvertMilliSecondsToFormattedDate(logDetailResponse.getComment().get(i).getCreated()));

            mCommentLayout.addView(layout2);
        }
    }
}
