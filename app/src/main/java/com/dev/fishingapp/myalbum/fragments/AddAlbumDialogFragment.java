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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.LoaderCallbacks.AddNewAlbumCallback;
import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.AddNewAlbumRequest;
import com.dev.fishingapp.data.model.AddNewAlbumResponse;
import com.dev.fishingapp.util.AlertMessageDialog;
import com.dev.fishingapp.util.AppConstants;
import com.dev.fishingapp.util.FishingPreferences;
import com.dev.fishingapp.util.UpdateAlbumImageUtil;
import com.dev.fishingapp.util.UpdateImageUtil;

import java.io.File;

import retrofit.mime.TypedFile;

/**
 * Created by user on 4/21/2016.
 */
public class AddAlbumDialogFragment extends DialogFragment implements View.OnClickListener {
    private AutoCompleteTextView mCountry;
    private String[] values;
    private AddAlbumBroadcastReceiver receiver;
    private LoaderManager loaderManager;
    private EditText et_title, et_details, et_location, et_street_address;
    Button save_btn;
    ImageView fish_img;
    String image_url;
    UpdateAlbumImageUtil updateImageUtil;
    SetAlbumBroadcastReceiver setAlbumBroadcastReceiver;


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
        mCountry = (AutoCompleteTextView) view.findViewById(R.id.country);
        et_title = (EditText) view.findViewById(R.id.et_title);
        et_details = (EditText) view.findViewById(R.id.et_details);
        et_location = (EditText) view.findViewById(R.id.et_details);
        et_street_address = (EditText) view.findViewById(R.id.et_street_address);
        save_btn = (Button) view.findViewById(R.id.save_btn);
        fish_img = (ImageView) view.findViewById(R.id.fish_img);
        values = getResources().getStringArray(R.array.countries_array);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, values);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCountry.setAdapter(spinnerArrayAdapter);
        mCountry.setOnClickListener(this);
        save_btn.setOnClickListener(this);
        fish_img.setOnClickListener(this);
        updateImageUtil = ((HomeActivity)getActivity()).getUpdateAlbumImageUtil();
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
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter(AppConstants.ADD_ALBUM_CALLBACK_BROADCAST);
        receiver = new AddAlbumBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);
        setAlbumBroadcastReceiver = new SetAlbumBroadcastReceiver();
        intentFilter = new IntentFilter(AppConstants.SET_IMAGE_ALBUM_CALLBACK_BROADCAST);
        localBroadcastManager.registerReceiver(setAlbumBroadcastReceiver, intentFilter);
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
            case R.id.fish_img:
                showImageOptions();
                break;
        }
    }

    class AddAlbumBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.ADD_ALBUM_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                    AddNewAlbumResponse response = (AddNewAlbumResponse) intent.getSerializableExtra("data");
                    if (response.getFaultcode() == 1) {
                        Intent loadIntent = new Intent(AppConstants.LOAD_ALBUM_LIST_CALLBACK_BROADCAST);
                        intent.putExtra("data", response);
                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(loadIntent);
                        AddAlbumDialogFragment.this.dismiss();
                    } else {
                        AlertMessageDialog dialog = new AlertMessageDialog(getActivity(), getString(R.string.error_txt), response.getMessage());
                        dialog.setAcceptButtonText(getString(R.string.ok_txt));
                        dialog.show();
                    }
                }

            }
        }
    }


    class SetAlbumBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.SET_IMAGE_ALBUM_CALLBACK_BROADCAST)) {
                if (intent.getStringExtra("data") != null) {
                    image_url = intent.getStringExtra("data");
                }
            }
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.unregisterReceiver(receiver);
    }

    public void addAlbum() {
        String uid, title, description, locname, street, additional, country_name, country, albumimage;
        TypedFile photoTypedFile = null;
        uid = FishingPreferences.getInstance().getCurrentUserId();
        title = et_title.getText().toString();
        description = et_details.getText().toString();
        locname = et_location.getText().toString();
        street = et_street_address.getText().toString();
        additional = "";
        country_name = mCountry.getText().toString();
        country = "";
        if (TextUtils.isEmpty(image_url)) {
            albumimage = "no";
        } else {
            albumimage = "yes";
            File photoFile = new File(image_url);
            photoTypedFile = new TypedFile("image/*", photoFile);
        }
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(getActivity(), R.string.empty_album_title, Toast.LENGTH_SHORT).show();
            return;
        }

        AddNewAlbumRequest addNewAlbumRequest = new AddNewAlbumRequest(uid, title, description, locname, street, additional, country_name, country, albumimage, photoTypedFile);
        loaderManager.initLoader(R.id.loader_myalbumdetails, null, new AddNewAlbumCallback((AppCompatActivity) getActivity(), true, addNewAlbumRequest));
    }

    private void showImageOptions() {
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
                        updateImageUtil.updateProfilePic(UpdateImageUtil.GALLERY_CAPTURE_IMAGE_REQUEST_CODE, fish_img);
                    }
                    break;
                    case 1: {
                        updateImageUtil.updateProfilePic(UpdateImageUtil.CAMERA_CAPTURE_IMAGE_REQUEST_CODE, fish_img);
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
        super.onDismiss(dialog);
    }
}
