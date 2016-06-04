package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.AddFishResponse;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 6/1/2016.
 */
public class AddCommentLoader extends AbstractLoader<AddFishResponse> {
    private String uid;
    private String subject;
    private String commentBody;
    private String nid;

    public AddCommentLoader(String uid,String subject,String commentBody,String nid) {
        super(FishingApplication.getContext());
        this.uid=uid;
        this.subject=subject;
        this.commentBody=commentBody;
        this.nid=nid;

    }

    @Override
    protected AddFishResponse doLoadInBackground() {
        AddFishResponse response ;
        response=getService().addComment("application/x-www-form-urlencoded", "application/json", AppConstants.ADD_COMMENT_API_ACTION, uid, subject,commentBody,nid);
        return response;
    }
}
