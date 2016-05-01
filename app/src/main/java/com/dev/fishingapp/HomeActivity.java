package com.dev.fishingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dev.fishingapp.fishinglog.fragment.FishingLog;
import com.dev.fishingapp.fragments.ChangePassword;
import com.dev.fishingapp.fragments.MyEpisodeList;
import com.dev.fishingapp.myalbum.fragments.MyAlbumFragment;
import com.dev.fishingapp.myfish.fragment.MyFishFragment;
import com.dev.fishingapp.myfriends.fragments.MyFriendsFragment;
import com.dev.fishingapp.myprofile.fragments.MyProfile;
import com.dev.fishingapp.support.DrawerItemAdapter;
import com.dev.fishingapp.support.NavDrawerItem;
import com.dev.fishingapp.util.UpdateImageUtil;

import java.util.ArrayList;


/**
 * Created by user on 4/18/2016.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private RelativeLayout mRelativeDrawerLayout;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private String[] navMenuTitles;
    private DrawerItemAdapter mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private TextView mHeader;
    private Button mAddFishBtn, mAddLogBtn;
    private ImageView mRightOption;
    private ImageView mEdit;
    private ImageView mProfilePic;
    private TextView logoutbtn;

    // used to store app title
    private CharSequence mTitle;
    public static final int CAMERA_OPTION = 0;
    public static final int EDIT_OPTION = 1;
    public static final int SEARCH_OPTION = 2;
    public static final int ADD_FISH_OPTION = 3;
    public static final int ADD_LOG_OPTION = 4;
    UpdateImageUtil updateImageUtil;
    private ImageView center_logo;
    Fragment currentFragment=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Always cast your custom Toolbar here, and set it as the ActionBar.
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        center_logo = (ImageView) tb.findViewById(R.id.center_logo);
        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home button
        ab.setDisplayShowHomeEnabled(true); // show or hide the default home button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        ab.setDisplayShowTitleEnabled(false);
        mHeader = (TextView) tb.findViewById(R.id.title);
        mAddFishBtn = (Button) tb.findViewById(R.id.add_fish);
        mAddLogBtn = (Button) tb.findViewById(R.id.add_log);
        updateImageUtil = UpdateImageUtil.getInstance(this);
        mRightOption = (ImageView) tb.findViewById(R.id.iv_right);
        logoutbtn = (TextView) findViewById(R.id.logout);
        logoutbtn.setOnClickListener(this);

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mRelativeDrawerLayout = (RelativeLayout) findViewById(R.id.rl_DrawerLayout);
        mProfilePic = (ImageView) findViewById(R.id.profile_pic);
        mEdit = (ImageView) findViewById(R.id.profile_pic_edit);
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
        mProfilePic.setOnClickListener(this);
        mEdit.setOnClickListener(this);
        navDrawerItems = new ArrayList<NavDrawerItem>();
        navMenuTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);

        // adding nav drawer items to array
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6]));

        // setting the nav drawer list adapter
        mAdapter = new DrawerItemAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(mAdapter);

        mDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this, mDrawerLayout, R.drawable.prv_btn, R.string.app_name, R.string.hello_world) {
            public void onDrawerClosed(View view) {
                //  getSupportActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                //  getSupportActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.sidebar_ic);


        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(1);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    public void setToolBarTitle(String title) {
        mHeader.setText(title);
    }

    public void setToolBarImage() {
        mHeader.setVisibility(View.GONE);
        mHeader.setText("");
        center_logo.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_pic_edit: {
                showImageOptions();
            }
            break;
            case R.id.logout: {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            break;
        }
    }


    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items

        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mRelativeDrawerLayout);
        menu.findItem(R.id.action_settings).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position) {
        // update the main content by replacing fishinglog
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MyProfile();
                break;
            case 1:
                fragment = new MyFishFragment();
                break;
            case 2:
                fragment = new MyFriendsFragment();
                break;
            case 3:
                fragment = new MyAlbumFragment();
                break;
            case 4:
                fragment = new FishingLog();
                break;
            case 5:
                fragment = new MyEpisodeList();
                break;
            case 6:
                fragment = new ChangePassword();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mRelativeDrawerLayout);
            currentFragment=fragment;

        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }


    public void resetOptions() {
        center_logo.setVisibility(View.GONE);
        mRightOption.setVisibility(View.GONE);
        mAddFishBtn.setVisibility(View.GONE);
        mAddLogBtn.setVisibility(View.GONE);
    }

    public void showRightOption(int option, View.OnClickListener listener) {
        switch (option) {
            case CAMERA_OPTION: {
                mRightOption.setVisibility(View.VISIBLE);
                mRightOption.setImageResource(R.drawable.add_photo);
                mRightOption.setOnClickListener(listener);
            }
            break;
            case EDIT_OPTION: {
                mRightOption.setVisibility(View.VISIBLE);
                mRightOption.setImageResource(R.drawable.edit_ic);
                mRightOption.setOnClickListener(listener);
            }
            break;
            case SEARCH_OPTION: {
                mRightOption.setVisibility(View.VISIBLE);
                mRightOption.setImageResource(R.drawable.search_ic);
                mRightOption.setOnClickListener(listener);
            }
            break;
            case ADD_LOG_OPTION: {
                mAddLogBtn.setVisibility(View.VISIBLE);
                mAddLogBtn.setOnClickListener(listener);
            }
            break;
            case ADD_FISH_OPTION: {
                mAddFishBtn.setVisibility(View.VISIBLE);
                mAddFishBtn.setOnClickListener(listener);
            }
            break;
        }
    }

    public void executeFragment(DialogFragment errorDialog) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(errorDialog, null);
        ft.commitAllowingStateLoss();
    }

    private void showImageOptions() {
        final CharSequence[] items = {
                "Gallery", "Camera"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Make your selection");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0: {
                        updateImageUtil.updateProfilePic(UpdateImageUtil.GALLERY_CAPTURE_IMAGE_REQUEST_CODE, mProfilePic);
                    }
                    break;
                    case 1: {
                        updateImageUtil.updateProfilePic(UpdateImageUtil.CAMERA_CAPTURE_IMAGE_REQUEST_CODE, mProfilePic);
                    }
                    break;
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        updateImageUtil.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            if(currentFragment instanceof MyFishFragment){
                super.onBackPressed();
            }else{
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.content_frame, new MyFishFragment()).addToBackStack(null).commit();
                // update selected item and title, then close the drawer
                mDrawerList.setItemChecked(1, true);
                mDrawerList.setSelection(1);
                setTitle(navMenuTitles[1]);
                mDrawerLayout.closeDrawer(mRelativeDrawerLayout);
            }
        } else {
            super.onBackPressed();

        }
    }
}
