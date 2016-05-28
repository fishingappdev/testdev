package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.AddNewAlbumRequest;
import com.dev.fishingapp.data.model.AddNewAlbumResponse;
import com.dev.fishingapp.util.AppConstants;

import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

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
            response = getService().addNewAlbum( AppConstants.ADD_ALBUM_API_ACTION, addNewAlbumRequest.getUid(), addNewAlbumRequest.getTitle(), addNewAlbumRequest.getDescription(), addNewAlbumRequest.getLocname(), addNewAlbumRequest.getStreet(), addNewAlbumRequest.getAdditional(), addNewAlbumRequest.getCountry_name(), addNewAlbumRequest.getCountry(), addNewAlbumRequest.getAlbumimage());
        else {
            TypedFile typedFile=new TypedFile("image/*", addNewAlbumRequest.getImage());
            response = getService().addNewAlbumWithFile(new TypedString(AppConstants.ADD_ALBUM_API_ACTION), new TypedString(addNewAlbumRequest.getUid()), new TypedString(addNewAlbumRequest.getTitle()), new TypedString(addNewAlbumRequest.getDescription()), new TypedString(addNewAlbumRequest.getLocname()), new TypedString(addNewAlbumRequest.getStreet()), new TypedString(addNewAlbumRequest.getAdditional()), new TypedString(addNewAlbumRequest.getCountry_name()), new TypedString(addNewAlbumRequest.getCountry()), new TypedString(addNewAlbumRequest.getAlbumimage()),typedFile );
        }
        return response;
        }
        }
