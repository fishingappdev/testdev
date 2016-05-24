package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.AddNewAlbumRequest;
import com.dev.fishingapp.data.model.AddNewAlbumResponse;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 4/20/2016.
 */
public class AddNewAlbumLoader extends AbstractLoader<AddNewAlbumResponse> {
    private AddNewAlbumRequest addNewAlbumRequest;

    public AddNewAlbumLoader(AddNewAlbumRequest addNewAlbumRequest) {
        super(FishingApplication.getContext());
        this.addNewAlbumRequest = addNewAlbumRequest;
    }

    @Override
    protected AddNewAlbumResponse doLoadInBackground() {
        AddNewAlbumResponse response;
        if (addNewAlbumRequest.getAlbumimage().equals("no"))
            response = getService().addNewAlbum("application/x-www-form-urlencoded", "application/json", AppConstants.ADD_ALBUM_API_ACTION, addNewAlbumRequest.getUid(), addNewAlbumRequest.getTitle(), addNewAlbumRequest.getDescription(), addNewAlbumRequest.getLocname(), addNewAlbumRequest.getStreet(), addNewAlbumRequest.getAdditional(), addNewAlbumRequest.getCountry_name(), addNewAlbumRequest.getCountry(), addNewAlbumRequest.getAlbumimage());
        else
            response = getService().addNewAlbumWithFile("multipart/form-data", "application/json", AppConstants.ADD_ALBUM_API_ACTION, addNewAlbumRequest.getUid(), addNewAlbumRequest.getTitle(), addNewAlbumRequest.getDescription(), addNewAlbumRequest.getLocname(), addNewAlbumRequest.getStreet(), addNewAlbumRequest.getAdditional(), addNewAlbumRequest.getCountry_name(), addNewAlbumRequest.getCountry(), addNewAlbumRequest.getAlbumimage(), addNewAlbumRequest.getImage());
        return response;
    }
}
