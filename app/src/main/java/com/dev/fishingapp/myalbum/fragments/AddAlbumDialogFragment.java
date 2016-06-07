package com.dev.fishingapp.myalbum.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.LoaderCallbacks.AddNewAlbumCallback;
import com.dev.fishingapp.LoaderCallbacks.UpdateAlbumCallback;
import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.AddNewAlbumRequest;
import com.dev.fishingapp.data.model.AddNewAlbumResponse;
import com.dev.fishingapp.data.model.UpdateNewAlbumResponse;
import com.dev.fishingapp.myalbum.support.CustomAddAlbumPagerAdapter;
import com.dev.fishingapp.util.AlertMessageDialog;
import com.dev.fishingapp.util.AppConstants;
import com.dev.fishingapp.util.FishingPreferences;
import com.dev.fishingapp.util.UpdateAlbumImageUtil;
import com.dev.fishingapp.util.UpdateImageUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by user on 4/21/2016.
 */
public class AddAlbumDialogFragment extends DialogFragment implements View.OnClickListener {
    private MultiAutoCompleteTextView mCountry;
    private String[] values;
    private AddAlbumBroadcastReceiver receiver;
    private LoaderManager loaderManager;
    private EditText et_title, et_details, et_location, et_street_address;
    Button save_btn;
    private ViewPager pager;
    UpdateAlbumImageUtil updateImageUtil;
    SetAlbumBroadcastReceiver setAlbumBroadcastReceiver;
    UpdateAlbumBroadcastReceiver updateReceiver;
    private CustomAddAlbumPagerAdapter adapter;
    private ArrayList<String> urlList;
    private int lastUploaded = -1;
    AddNewAlbumResponse response;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Translucent);
        loaderManager = getActivity().getSupportLoaderManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_add_album, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        mCountry = (MultiAutoCompleteTextView) view.findViewById(R.id.country);
        mCountry.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        et_title = (EditText) view.findViewById(R.id.et_title);
        et_details = (EditText) view.findViewById(R.id.et_details);
        et_location = (EditText) view.findViewById(R.id.et_details);
        et_street_address = (EditText) view.findViewById(R.id.et_street_address);
        save_btn = (Button) view.findViewById(R.id.save_btn);
        pager = (ViewPager) view.findViewById(R.id.pager);
        values = getResources().getStringArray(R.array.countries_array);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, values);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        urlList = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            urlList.add("");
        adapter = new CustomAddAlbumPagerAdapter(getActivity(), urlList, uploadClick);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(4);
        mCountry.setAdapter(spinnerArrayAdapter);
        mCountry.setOnClickListener(this);
        save_btn.setOnClickListener(this);
        updateImageUtil = ((HomeActivity) getActivity()).getUpdateAlbumImageUtil();
        registerReceivers();
    }

    private void registerReceivers() {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter(AppConstants.ADD_ALBUM_CALLBACK_BROADCAST);
        receiver = new AddAlbumBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);
        setAlbumBroadcastReceiver = new SetAlbumBroadcastReceiver();
        intentFilter = new IntentFilter(AppConstants.SET_IMAGE_ALBUM_CALLBACK_BROADCAST);
        localBroadcastManager.registerReceiver(setAlbumBroadcastReceiver, intentFilter);
        updateReceiver = new UpdateAlbumBroadcastReceiver();
        intentFilter = new IntentFilter(AppConstants.UPDATE_ALBUM_CALLBACK_BROADCAST);
        localBroadcastManager.registerReceiver(updateReceiver, intentFilter);
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        window.setLayout(width, WindowManager.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.country:
                mCountry.showDropDown();
                break;
            case R.id.save_btn:
                addAlbum();
                break;
//            case R.id.fish_img:
//                showImageOptions();
//                break;
        }
    }

    class AddAlbumBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.ADD_ALBUM_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                    AddNewAlbumResponse response = (AddNewAlbumResponse) intent.getSerializableExtra("data");
                    if (response.getFaultcode() == 1) {
                        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
                        localBroadcastManager.unregisterReceiver(receiver);
                        lastUploaded++;
                        AddAlbumDialogFragment.this.response = response;
                        uploadRestImages();
                    } else {
                        AlertMessageDialog dialog = new AlertMessageDialog(getActivity(), getString(R.string.error_txt), response.getMessage());
                        dialog.setAcceptButtonText(getString(R.string.ok_txt));
                        dialog.show();
                    }
                }

            }
        }
    }

    class UpdateAlbumBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.UPDATE_ALBUM_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                    UpdateNewAlbumResponse response = (UpdateNewAlbumResponse) intent.getSerializableExtra("data");
                    if (response.getFaultcode() == 1) {
                        lastUploaded++;
                        uploadRestImages();
                    } else {
                        AlertMessageDialog dialog = new AlertMessageDialog(getActivity(), getString(R.string.error_txt), response.getMessage());
                        dialog.setAcceptButtonText(getString(R.string.ok_txt));
                        dialog.show();
                    }
                }

            }
        }
    }


    private void uploadRestImages() {
        if (lastUploaded >= (urlList.size() - 1) || TextUtils.isEmpty(urlList.get(lastUploaded + 1))) {
            finishAddAlbum(response);
        } else {
            File photoFile = new File(urlList.get(lastUploaded + 1));
            loaderManager.initLoader(R.id.loader_addalbum, null, new UpdateAlbumCallback((AppCompatActivity) getActivity(), true, response.getNid(), photoFile));
        }
    }

    private void finishAddAlbum(AddNewAlbumResponse response) {
        Intent loadIntent = new Intent(AppConstants.LOAD_ALBUM_LIST_CALLBACK_BROADCAST);
        loadIntent.putExtra("data", response);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(loadIntent);
        AddAlbumDialogFragment.this.dismiss();
    }


    class SetAlbumBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.SET_IMAGE_ALBUM_CALLBACK_BROADCAST)) {
                if (intent.getStringExtra("data") != null) {
                    String image_url = intent.getStringExtra("data");
                    int position = intent.getIntExtra("position", 0);
                    urlList.set(position, image_url);
                }
            }
        }
    }


    public void addAlbum() {
        String uid, title, description, locname, street, additional, country_name, country, albumimage;
        File photoFile = null;
        uid = FishingPreferences.getInstance().getCurrentUserId();
        title = et_title.getText().toString();
        description = et_details.getText().toString();
        locname = et_location.getText().toString();
        street = et_street_address.getText().toString();
        additional = "";
        country_name = "";
        country = mCountry.getText().toString();
        if (TextUtils.isEmpty(urlList.get(0))) {
            albumimage = "no";
        } else {
            albumimage = "yes";
            photoFile = new File(urlList.get(0));
        }
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(getActivity(), R.string.empty_album_title, Toast.LENGTH_SHORT).show();
            return;
        }

        AddNewAlbumRequest addNewAlbumRequest = new AddNewAlbumRequest(uid, title, description, locname, street, additional, country_name, country, albumimage, photoFile);
        loaderManager.initLoader(R.id.loader_addalbum, null, new AddNewAlbumCallback((AppCompatActivity) getActivity(), true, addNewAlbumRequest));
    }

    View.OnClickListener uploadClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();
            boolean canUpload = true;
            for (int i = 0; i < position; i++) {
                if (TextUtils.isEmpty(urlList.get(i))) {
                    canUpload = false;
                    break;
                }
            }
            if (canUpload)
                showImageOptions(view, position);
        }
    };


    private void showImageOptions(final View view, final int position) {
        ((HomeActivity) getActivity()).setAlbum(true);
        final CharSequence[] items = {
                "Gallery", "Camera"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Make your selection");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0: {
                        updateImageUtil.updateProfilePic(UpdateImageUtil.GALLERY_CAPTURE_IMAGE_REQUEST_CODE, (ImageView) view, position);
                    }
                    break;
                    case 1: {
                        updateImageUtil.updateProfilePic(UpdateImageUtil.CAMERA_CAPTURE_IMAGE_REQUEST_CODE, (ImageView) view, position);
                    }
                    break;
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.unregisterReceiver(setAlbumBroadcastReceiver);
        localBroadcastManager.unregisterReceiver(updateReceiver);
        super.onDismiss(dialog);
    }
}
