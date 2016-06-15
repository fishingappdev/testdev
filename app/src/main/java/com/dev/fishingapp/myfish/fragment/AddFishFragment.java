package com.dev.fishingapp.myfish.fragment;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.dev.fishingapp.AbstractActivity;
import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.LoaderCallbacks.AddFishCallback;
import com.dev.fishingapp.LoaderCallbacks.FishTypeCallback;
import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.AddFishRequest;
import com.dev.fishingapp.data.model.FishType;
import com.dev.fishingapp.data.model.FishTypeResponse;
import com.dev.fishingapp.support.BaseToolbarFragment;
import com.dev.fishingapp.util.AlertMessageDialog;
import com.dev.fishingapp.util.AppConstants;
import com.dev.fishingapp.util.FishingPreferences;
import com.dev.fishingapp.util.UpdateAlbumImageUtil;
import com.dev.fishingapp.util.UpdateImageUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit.mime.TypedFile;

/**
 * Created by user on 4/21/2016.
 */
public class AddFishFragment extends BaseToolbarFragment implements OnClickListener {
    private EditText mDate, mLengthEdt, mWeightEdt, mLocationEdt, mDescription, pbTxt;
    private Spinner mTypeFishSpinner;
    private LoaderManager loaderManager;
    private FishTypeBroadcastReceiver receiver;
    private AddFishBroadcastReceiver addreceiver;
    private AlertMessageDialog dialog;
    private ArrayList<FishType> mFishTypeList;
    private Button saveBtn;
    Calendar myCalendar = Calendar.getInstance();
    private String nid, typeoffishnid, mTitle;
    private ImageView mFishImg;
    UpdateAlbumImageUtil updateImageUtil;
    private String albumimage;
    private String image_url;
    SetAlbumBroadcastReceiver setAlbumBroadcastReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_fish, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loaderManager = getActivity().getSupportLoaderManager();

        mDate = (EditText) view.findViewById(R.id.date);
        mLengthEdt = (EditText) view.findViewById(R.id.length);
        mWeightEdt = (EditText) view.findViewById(R.id.weight);
        mLocationEdt = (EditText) view.findViewById(R.id.where_caught);
        mTypeFishSpinner = (Spinner) view.findViewById(R.id.fishType);
        saveBtn = (Button) view.findViewById(R.id.save_btn);
        mDescription = (EditText) view.findViewById(R.id.fish_desc);
        pbTxt = (EditText) view.findViewById(R.id.pbTxt);
        mFishImg = (ImageView) view.findViewById(R.id.fish_img);

        mDate.setInputType(InputType.TYPE_NULL);

        saveBtn.setOnClickListener(this);
        mFishImg.setOnClickListener(this);

        mDate.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        mLengthEdt.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        mWeightEdt.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        mWeightEdt.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        updateImageUtil = ((HomeActivity) getActivity()).getUpdateAlbumImageUtil();


        if (loaderManager.getLoader(R.id.loader_fish_type) == null) {
            loaderManager.initLoader(R.id.loader_fish_type, null, new FishTypeCallback(((AbstractActivity) getActivity()), true));
        } else {
            loaderManager.restartLoader(R.id.loader_fish_type, null, new FishTypeCallback(((AbstractActivity) getActivity()), true));
        }

        mTypeFishSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                FishType fishType = (FishType) parent.getSelectedItem();
                Log.d("Fishtype is", fishType.getNid() + ">>>" + fishType.getTitle());
                typeoffishnid = fishType.getNid();
                mTitle = fishType.getTitle();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        mDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);  // hide the soft keyboard
                }
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity)getActivity()).setCurrentFragment(AddFishFragment.this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("Add Fish", "On Resume" + ">>>>>");

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter(AppConstants.FISH_TYPE_CALLBACK_BROADCAST);
        IntentFilter intentFilter1 = new IntentFilter(AppConstants.ADD_FISH_CALLBACK_BROADCAST);
        IntentFilter intentFilter2 = new IntentFilter(AppConstants.SET_IMAGE_ALBUM_CALLBACK_BROADCAST);


        receiver = new FishTypeBroadcastReceiver();
        addreceiver = new AddFishBroadcastReceiver();
        setAlbumBroadcastReceiver = new SetAlbumBroadcastReceiver();


        localBroadcastManager.registerReceiver(receiver, intentFilter);
        localBroadcastManager.registerReceiver(addreceiver, intentFilter1);
        localBroadcastManager.registerReceiver(setAlbumBroadcastReceiver, intentFilter2);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("Add Fish", "On Stop" + ">>>>>");
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.unregisterReceiver(receiver);
        localBroadcastManager.unregisterReceiver(addreceiver);
        // localBroadcastManager.unregisterReceiver(setAlbumBroadcastReceiver);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_btn:
                if (validateFields()) {
                    TypedFile photoTypedFile = null;
                    File photoFile = null;
                    String uid = FishingPreferences.getInstance().getCurrentUserId();
                    String date = mDate.getText().toString().trim();
                    String description = mDescription.getText().toString().trim();
                    String length = mLengthEdt.getText().toString().trim();
                    String weight = mWeightEdt.getText().toString().trim();
                    String location = mLocationEdt.getText().toString().trim();
                    String pbVal = pbTxt.getText().toString().trim();


                    if (TextUtils.isEmpty(image_url)) {
                        albumimage = "no";
                    } else {
                        albumimage = "yes";
                        photoFile = new File(image_url);
                        photoTypedFile = new TypedFile("image/*", photoFile);
                    }

                    AddFishRequest request = new AddFishRequest(mTitle, description, pbVal, typeoffishnid, date, location, length, weight, albumimage, uid, photoFile);

                    if (loaderManager.getLoader(R.id.loader_add_fish) == null) {
                        loaderManager.initLoader(R.id.loader_add_fish, null, new AddFishCallback(((AbstractActivity) getActivity()), true, request));
                    } else {
                        loaderManager.restartLoader(R.id.loader_add_fish, null, new AddFishCallback(((AbstractActivity) getActivity()), true, request));
                    }

                }
                break;
            case R.id.fish_img:
                showImageOptions();
                break;

        }
    }

    private void updateLabel() {

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mDate.setText(sdf.format(myCalendar.getTime()));
    }

    private boolean validateFields() {
        if (mDate.getText().toString().trim().isEmpty()) {
            mDate.setError(getString(R.string.select_date_error));
            return false;
        }
        return true;
    }

    class FishTypeBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.FISH_TYPE_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                    mFishTypeList = (FishTypeResponse) intent.getSerializableExtra("data");
                    if (mFishTypeList.size() > 0) {
                        ArrayAdapter<FishType> adapter = new ArrayAdapter<FishType>(getActivity(), android.R.layout.simple_spinner_item, mFishTypeList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mTypeFishSpinner.setAdapter(adapter);

                    } else {
                        dialog = new AlertMessageDialog(((HomeActivity) getActivity()), getActivity().getString(R.string.error_txt), getString(R.string.empty_list));
                        dialog.setAcceptButtonText(getString(R.string.ok_txt));
                        dialog.show();

                    }

                }

            }
        }
    }

    class AddFishBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.ADD_FISH_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                    Intent intent1 = new Intent(AppConstants.LOAD_FISH_CALLBACK_BROADCAST);
                    LocalBroadcastManager.getInstance((AbstractActivity) getActivity()).sendBroadcast(intent1);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                        fm.popBackStack();
                    }
                  //  getActivity().getSupportFragmentManager().popBackStack();

                } else {
                    dialog = new AlertMessageDialog(((HomeActivity) getActivity()), getActivity().getString(R.string.error_txt), getString(R.string.empty_list));
                    dialog.setAcceptButtonText(getString(R.string.ok_txt));
                    dialog.show();

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
                        updateImageUtil.updateProfilePic(UpdateImageUtil.GALLERY_CAPTURE_IMAGE_REQUEST_CODE, mFishImg, 0);
                    }
                    break;
                    case 1: {
                        updateImageUtil.updateProfilePic(UpdateImageUtil.CAMERA_CAPTURE_IMAGE_REQUEST_CODE, mFishImg, 0);
                    }
                    break;
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


}

