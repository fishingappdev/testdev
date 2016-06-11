package com.dev.fishingapp.fishinglog.fragment;

import android.app.DatePickerDialog;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.dev.fishingapp.AbstractActivity;
import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.LoaderCallbacks.AddLogCallback;
import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.AddLogRequest;
import com.dev.fishingapp.support.BaseToolbarFragment;
import com.dev.fishingapp.util.AlertMessageDialog;
import com.dev.fishingapp.util.AppConstants;
import com.dev.fishingapp.util.FishingPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by user on 4/23/2016.
 */
public class AddFishingLog extends BaseToolbarFragment implements View.OnClickListener,AdapterView.OnItemSelectedListener{
    private Spinner mMoonSpinner,mtideSpinner;
    private EditText title,description,location,weather,longitude,latitude;
    private EditText mDate;
    private Button mSaveBtn;
    private String mMoonValue;
    private String mTideValue;
    private String date;
    private LoaderManager loaderManager;
    private AddLogBroadcastReceiver receiver;
    Calendar myCalendar = Calendar.getInstance();
    private AlertMessageDialog dialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmnet_add_fishing_log, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) getActivity()).setToolBarTitle(getResources().getString(R.string.header_fishinglog));
        loaderManager = getActivity().getSupportLoaderManager();

        mMoonSpinner=(Spinner)view.findViewById(R.id.moon_view);
        mtideSpinner=(Spinner)view.findViewById(R.id.tide_view);
        title=(EditText)view.findViewById(R.id.logTitle);
        description=(EditText)view.findViewById(R.id.description);
        location=(EditText)view.findViewById(R.id.location_name);
        weather=(EditText)view.findViewById(R.id.weather);
        longitude=(EditText)view.findViewById(R.id.longitude);
        latitude=(EditText)view.findViewById(R.id.latitude);
        mDate=(EditText)view.findViewById(R.id.date_value);
        mSaveBtn=(Button)view.findViewById(R.id.save_btn);

        mSaveBtn.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.moon_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMoonSpinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.tide_type_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mtideSpinner.setAdapter(adapter1);

        mMoonSpinner.setOnItemSelectedListener(this);
        mtideSpinner.setOnItemSelectedListener(this);


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


        mDate.setOnClickListener(new View.OnClickListener() {
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

    private void updateLabel() {

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mDate.setText(sdf.format(myCalendar.getTime()));
    }
    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter(AppConstants.ADD_FISHING_LOG_CALLBACK_BROADCAST);
        receiver = new AddLogBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);

    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.unregisterReceiver(receiver);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_btn:
                String logTitle=title.getText().toString().trim();
                String logDesc=description.getText().toString().trim();
                String logWeather=weather.getText().toString().trim();
                String logLatitude=longitude.getText().toString().trim();
                String logLongitude=latitude.getText().toString().trim();
                String logLocation=location.getText().toString().trim();
                String uid= FishingPreferences.getInstance().getCurrentUserId();
                date=mDate.getText().toString();


                AddLogRequest request= new AddLogRequest(logTitle,logDesc,logLocation,mMoonValue,date,mTideValue,logWeather,logLatitude,logLongitude);
                if (loaderManager.getLoader(R.id.loader_add_log_fish) == null) {
                    loaderManager.initLoader(R.id.loader_add_log_fish, null, new AddLogCallback(((AbstractActivity) getActivity()), true, request,uid));
                } else {
                    loaderManager.restartLoader(R.id.loader_add_log_fish, null, new AddLogCallback(((AbstractActivity) getActivity()), true, request,uid));
                }

                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.moon_view:
                mMoonValue=(String)parent.getSelectedItem();
                break;
            case R.id.tide_view:
                mTideValue=(String)parent.getSelectedItem();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class AddLogBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.ADD_FISHING_LOG_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                        Intent intent1 = new Intent(AppConstants.LOAD_FISH_LOG_CALLBACK_BROADCAST);
                        LocalBroadcastManager.getInstance((AbstractActivity) getActivity()).sendBroadcast(intent1);
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                            fm.popBackStack();
                        }

                    } else {
                        dialog = new AlertMessageDialog(((HomeActivity) getActivity()), getActivity().getString(R.string.error_txt), getString(R.string.empty_list));
                        dialog.setAcceptButtonText(getString(R.string.ok_txt));
                        dialog.show();

                    }

                }

            }
        }
    }

