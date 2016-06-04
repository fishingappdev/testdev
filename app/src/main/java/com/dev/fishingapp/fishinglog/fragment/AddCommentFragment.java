package com.dev.fishingapp.fishinglog.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dev.fishingapp.AbstractActivity;
import com.dev.fishingapp.LoaderCallbacks.AddCommentCallback;
import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.AddFishResponse;
import com.dev.fishingapp.util.AlertMessageDialog;
import com.dev.fishingapp.util.AppConstants;
import com.dev.fishingapp.util.FishingPreferences;

/**
 * Created by user on 6/3/2016.
 */
public class AddCommentFragment extends AbstractActivity {
    private Button mAddCommentBtn;
    private onCommentAddListener mListener;
    private LoaderManager loaderManager;
    private AlertMessageDialog dialog;
    private AddCommentBroadcastReceiver receiver;
    private String subject,commentBody,nId,uID;
    private EditText mTitle,mDescription;
    private TextView mHeader;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comment_view);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        tb.setVisibility(View.VISIBLE);
        mHeader = (TextView) tb.findViewById(R.id.title);
        mHeader.setText("Add Comment");

        nId= getIntent().getStringExtra("nid");
        mAddCommentBtn=(Button)findViewById(R.id.CommentBtn);
        mTitle=(EditText)findViewById(R.id.commentTitle);
        mDescription=(EditText)findViewById(R.id.comment_desc);
        loaderManager = getSupportLoaderManager();

        /*try {
            mListener = (onCommentAddListener) this;
        } catch (ClassCastException e) {
            throw new ClassCastException(this.toString() + " must implement OnArticleSelectedListener");
        }*/

        mAddCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                subject = mTitle.getText().toString();
                commentBody = mDescription.getText().toString();
                uID = FishingPreferences.getInstance().getCurrentUserId();
                if (loaderManager.getLoader(R.id.loader_add_comment) == null) {
                    loaderManager.initLoader(R.id.loader_add_comment, null, new AddCommentCallback(AddCommentFragment.this, true, uID, subject, commentBody, nId));
                } else {
                    loaderManager.restartLoader(R.id.loader_add_comment, null, new AddCommentCallback(AddCommentFragment.this, true, uID, subject, commentBody, nId));
                }

                //   getActivity().getSupportFragmentManager().beginTransaction().remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.content_frame)).commit();

            }
        });
    }



    public interface onCommentAddListener{
        public void onCommentAdd();
    }



    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter(AppConstants.ADD_COMMENT_CALLBACK_BROADCAST);
        receiver = new AddCommentBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.unregisterReceiver(receiver);
    }

    class AddCommentBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.ADD_COMMENT_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                    AddFishResponse response= (AddFishResponse)intent.getSerializableExtra("data");
                    if(response.getStatus().equalsIgnoreCase(getString(R.string.success_txt))){
                        Intent intent1 = new Intent(AppConstants.LOAD_COMMENT_CALLBACK_BROADCAST);
                        LocalBroadcastManager.getInstance(AddCommentFragment.this).sendBroadcast(intent1);
                       finish();
                    }else{
                        dialog=new AlertMessageDialog(AddCommentFragment.this,getString(R.string.error_txt),response.getStatus());
                        dialog.setAcceptButtonText(getString(R.string.ok_txt));
                        dialog.show();

                    }

                }

            }
        }
    }



}
