package com.dev.fishingapp.fragments.dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.fishingapp.R;

public class GeneralDialogFragment extends DialogFragment {
    private static final String ARG_TITLE = "mTitle";
    private static final String ARG_MESSAGE = "mMessage";
    private static final String ARG_MESSAGE_STR = "mMessageStr";
    private static final String ARG_FINISH_ON_DISMISS = "arg_finish_on_dismiss";
    private static final String ARG_ID = "id";
    private int mTitle, mMessage;
    private String messageStr;
    private View okButton;
    private boolean finishActivityOnDismiss;
    private boolean backpressOnOk;
    private Intent intent;


    public static GeneralDialogFragment newInstance(int title, int message) {
        GeneralDialogFragment generalDialogFragment = new GeneralDialogFragment();
        final Bundle args = new Bundle();
        args.putInt(ARG_TITLE, title);
        args.putInt(ARG_MESSAGE, message);
        generalDialogFragment.setArguments(args);
        return generalDialogFragment;
    }

    public GeneralDialogFragment setFinishActivityOnOk(boolean finishActivity)
    {
        this.finishActivityOnDismiss =finishActivity;
        return this;
    }

    public GeneralDialogFragment setBackpressOnOk(boolean finishActivity)
    {
        this.backpressOnOk =finishActivity;
        return this;
    }

    public GeneralDialogFragment setIntent(Intent intent)
    {
        this.intent = intent;
        return this;
    }


    public static GeneralDialogFragment newInstance(int message) {
        GeneralDialogFragment generalDialogFragment = new GeneralDialogFragment();
        final Bundle args = new Bundle();
        args.putInt(ARG_MESSAGE, message);
        generalDialogFragment.setArguments(args);
        return generalDialogFragment;
    }

    public static GeneralDialogFragment newInstance(String message) {
        GeneralDialogFragment generalDialogFragment = new GeneralDialogFragment();
        final Bundle args = new Bundle();
        args.putString(ARG_MESSAGE_STR, message);
        generalDialogFragment.setArguments(args);
        return generalDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.custom_dialog);
        this.mTitle = getArguments().getInt(ARG_TITLE);
        this.mMessage = getArguments().getInt(ARG_MESSAGE);
        this.messageStr = getArguments().getString(ARG_MESSAGE_STR);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_general, container, false);
        TextView title = (TextView) view.findViewById(R.id.title_agree_disagree_dialog);
        if (mTitle == 0) {
            title.setVisibility(View.GONE);
        } else {
            title.setText(getString(mTitle));
        }
        TextView message = (TextView) view.findViewById(R.id.message_agree_disagree_dialog);
        if (messageStr == null) {

            if(mMessage!= 0)
                message.setText(getString(mMessage));

        } else {
            message.setText(messageStr);
        }
        okButton = view.findViewById(R.id.ok_general_dialog);

        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GeneralDialogFragment.this.dismiss();
                if (GeneralDialogFragment.this.finishActivityOnDismiss)
                    getActivity().finish();
                else if (GeneralDialogFragment.this.backpressOnOk)
                    getActivity().onBackPressed();
                if(intent!=null)
                    startActivity(intent);
            }
        });

        return view;
    }

    public int getDialogId() {
        return getArguments().getInt(ARG_ID);
    }

    public void setDialogId(final int id) {
        getArguments().putInt(ARG_ID, id);
    }
}
