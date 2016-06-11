package com.dev.fishingapp.myfriends;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.fishingapp.AbstractActivity;
import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.LoaderCallbacks.AddFriendCallback;
import com.dev.fishingapp.LoaderCallbacks.FriendDetailCallback;
import com.dev.fishingapp.LoaderCallbacks.RemoveFriendCallback;
import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.AddRemoveFriendResponse;
import com.dev.fishingapp.data.model.FriendData;
import com.dev.fishingapp.data.model.FriendDetailResponse;
import com.dev.fishingapp.util.AlertMessageDialog;
import com.dev.fishingapp.util.AppConstants;
import com.dev.fishingapp.util.DotsProgressBar;
import com.dev.fishingapp.util.FishingAppHelper;
import com.dev.fishingapp.util.FishingPreferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * Created by skumari on 4/23/2016.
 */
public class FriendDetailsActivity extends AbstractActivity {
    private TextView mHeader;
    private String userId;
    private LoaderManager loaderManager;
    private FriendDetailBroadcastReceiver receiver;
    private AddFriendBroadcastReceiver addreceiver;
    private RemoveFriendBroadcastReceiver removereceiver;
    FriendData data;
    private Button btn_my_albums, btn_my_friends, btn_my_fishing_log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_details);
        userId = getIntent().getStringExtra("userId");
        if (TextUtils.isEmpty(userId))
            finish();

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        mHeader = (TextView) tb.findViewById(R.id.title);

        loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(R.id.loader_friends_detail, null, new FriendDetailCallback(this, true, userId));

    }

    class FriendDetailBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.FRIEND_DETAIL_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                    FriendDetailResponse response = (FriendDetailResponse) intent.getSerializableExtra("data");
                    if (response.getStatus().equals("success")) {
                        data = response.getData();
                        updateUI();
                    } else {
                        AlertMessageDialog dialog = new AlertMessageDialog(FriendDetailsActivity.this, getString(R.string.error_txt), getString(R.string.some_error_occured));
                        dialog.setAcceptButtonText(getString(R.string.ok_txt));
                        dialog.show();
                    }
                }

            }
        }
    }

    class AddFriendBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.ADD_FRIEND_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                    AddRemoveFriendResponse response = (AddRemoveFriendResponse) intent.getSerializableExtra("data");
                    AlertMessageDialog dialog = new AlertMessageDialog(FriendDetailsActivity.this, response.getStatus(), response.getMessage());
                    dialog.setAcceptButtonText(getString(R.string.ok_txt));
                    dialog.show();
                    if (response.getStatus().equalsIgnoreCase("success")) {
                        data.setFriendstatus("pending");
                        updateUI();
                    }
                }
            }
        }
    }

    class RemoveFriendBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.REMOVE_FRIEND_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                    AddRemoveFriendResponse response = (AddRemoveFriendResponse) intent.getSerializableExtra("data");
                    AlertMessageDialog dialog = new AlertMessageDialog(FriendDetailsActivity.this, response.getStatus(), response.getMessage());
                    dialog.setAcceptButtonText(getString(R.string.ok_txt));
                    dialog.show();
                    if (response.getStatus().equalsIgnoreCase("success")) {
                        data.setFriendstatus("nofriend");
                        updateUI();
                    }
                }
            }
        }
    }

    private void updateUI() {
        DotsProgressBar dotsProgressBar;
        DisplayImageOptions options;

        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ImageView iv_friend = (ImageView) findViewById(R.id.iv_friend);
        TextView tv_country = (TextView) findViewById(R.id.tv_country);
        TextView about_me_description = (TextView) findViewById(R.id.about_me_description);
        TextView tv_preferences = (TextView) findViewById(R.id.tv_preferences);
        TextView tv_add_friemd = (TextView) findViewById(R.id.tv_add_friemd);
        btn_my_albums = (Button) findViewById(R.id.btn_my_albums);
        btn_my_fishing_log = (Button) findViewById(R.id.btn_my_fishing_log);
        btn_my_friends = (Button) findViewById(R.id.btn_my_friends);
        mHeader.setText(data.getName());
        tv_title.setText(data.getName());
        tv_country.setText(data.getField_country());
        about_me_description.setText(data.getAbout_me());
        tv_preferences.setText(data.getFishing_preferences());
        dotsProgressBar = new DotsProgressBar(this, iv_friend);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(dotsProgressBar)
                .showImageForEmptyUri(R.drawable.cf_list_no_image_thumbnail)
                .showImageOnFail(R.drawable.cf_list_no_image_thumbnail)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        FishingAppHelper.getImageLoader().displayImage(data.getPicture(), iv_friend, options);
        if (data.getFriendstatus().equalsIgnoreCase("nofriend")) {
            tv_add_friemd.setText(R.string.add_friend);
        } else if (data.getFriendstatus().equalsIgnoreCase("friend")) {
            tv_add_friemd.setText(R.string.remove_friend);
        } else {
            tv_add_friemd.setText(R.string.remove_friend_request);
        }
        tv_add_friemd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loaderManager = getSupportLoaderManager();
                if (data.getFriendstatus().equalsIgnoreCase("nofriend")) {
                    loaderManager.initLoader(R.id.loader_add_friends, null, new AddFriendCallback(FriendDetailsActivity.this, true, FishingPreferences.getInstance().getCurrentUserId(), data.getUid()));
                } else {
                    loaderManager.initLoader(R.id.loader_remove_friends, null, new RemoveFriendCallback(FriendDetailsActivity.this, true, FishingPreferences.getInstance().getCurrentUserId(), data.getUid()));
                }
            }
        });
        btn_my_albums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendDetailsActivity.this, HomeActivity.class);
                intent.putExtra("locked", true);
                intent.putExtra("selected_fragment", HomeActivity.ALBUM);
                intent.putExtra("uid", data.getUid());
                startActivity(intent);
            }
        });

        btn_my_fishing_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendDetailsActivity.this, HomeActivity.class);
                intent.putExtra("locked", true);
                intent.putExtra("selected_fragment", HomeActivity.FISHING_LOG);
                intent.putExtra("uid", data.getUid());
                startActivity(intent);
            }
        });
        btn_my_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendDetailsActivity.this, HomeActivity.class);
                intent.putExtra("locked", true);
                intent.putExtra("selected_fragment", HomeActivity.MY_FRIENDS);
                intent.putExtra("uid", data.getUid());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter(AppConstants.FRIEND_DETAIL_CALLBACK_BROADCAST);
        receiver = new FriendDetailBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);
        intentFilter = new IntentFilter(AppConstants.ADD_FRIEND_CALLBACK_BROADCAST);
        addreceiver = new AddFriendBroadcastReceiver();
        localBroadcastManager.registerReceiver(addreceiver, intentFilter);
        intentFilter = new IntentFilter(AppConstants.REMOVE_FRIEND_CALLBACK_BROADCAST);
        removereceiver = new RemoveFriendBroadcastReceiver();
        localBroadcastManager.registerReceiver(removereceiver, intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.unregisterReceiver(receiver);
        localBroadcastManager.unregisterReceiver(addreceiver);
        localBroadcastManager.unregisterReceiver(removereceiver);
    }
}
