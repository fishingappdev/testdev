package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.UpdateNewAlbumResponse;
import com.dev.fishingapp.util.AppConstants;
import com.dev.fishingapp.util.FishingPreferences;

import java.io.File;

import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by user on 4/20/2016.
 */
public class UpdateAlbumLoader extends AbstractLoader<UpdateNewAlbumResponse> {
    private String nid;
    private File image;

    public UpdateAlbumLoader(String nid, File image) {
        super(FishingApplication.getContext());
        this.nid = nid;
        this.image = image;
    }

    @Override
    protected UpdateNewAlbumResponse doLoadInBackground() {
        UpdateNewAlbumResponse response;
        TypedFile typedFile = new TypedFile("image/*", image);
        response = getService().updateAlbum(new TypedString(AppConstants.UPDATE_ALBUM_API_ACTION), new TypedString(FishingPreferences.getInstance().getCurrentUserId()), new TypedString(nid), typedFile);
        return response;
    }
}
