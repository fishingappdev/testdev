package com.dev.fishingapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.dev.fishingapp.fragments.dialogs.ProgressDialogFragment;

public abstract class AbstractActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;
    private Object pushNotificationEvent;
    private Object imageEvent;
    private Thread mintSplunkThread;

    public static final int MEDIA_TYPE_IMAGE = 1;
    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int GALLERY_CAPTURE_IMAGE_REQUEST_CODE = 101;
    // directory name to store captured images
    private static final String IMAGE_DIRECTORY_NAME = "Virgin-Red";
    private Uri fileUri;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();

        if(savedInstanceState!=null) {
            String uri = savedInstanceState.getString("fileUri");
            if (uri!=null)
                fileUri= Uri.parse(uri);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("fileUri", String.valueOf(fileUri));
        super.onSaveInstanceState(outState);
    }


    public void showDialogFragment(final DialogFragment dialogFragment) {
        if(!isFinishing())
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(!isFinishing())
                        dialogFragment.show(getSupportFragmentManager(), dialogFragment.getClass().getSimpleName());
                }
            });
    }

    public void showProgressDialog() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                DialogFragment dialogFragment = ((DialogFragment) getSupportFragmentManager().findFragmentByTag(ProgressDialogFragment.class.getSimpleName()));
                if (dialogFragment == null)
                    dialogFragment = new ProgressDialogFragment();
                else
                    getSupportFragmentManager().beginTransaction().remove(dialogFragment).commit();
                showDialogFragment(dialogFragment);
            }
        });
    }

    public void hideProgressDialog() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                DialogFragment dialogFragment = ((DialogFragment) getSupportFragmentManager().findFragmentByTag(ProgressDialogFragment.class.getSimpleName()));
                if (dialogFragment != null)
                    if (!isFinishing())
                        dialogFragment.dismiss();
            }
        });
    }

    public void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (!(view instanceof EditText) || !isTouchInsideView(ev, view))) {
            hideKeyboard();
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isTouchInsideView(final MotionEvent ev, final View currentFocus) {
        final int[] loc = new int[2];
        currentFocus.getLocationOnScreen(loc);
        return ev.getRawX() > loc[0] && ev.getRawY() > loc[1] && ev.getRawX() < (loc[0] + currentFocus.getWidth())
                && ev.getRawY() < (loc[1] + currentFocus.getHeight());
    }

    void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);//getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }


    @Override
    protected void onResume() {
        super.onResume();
        hideProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}