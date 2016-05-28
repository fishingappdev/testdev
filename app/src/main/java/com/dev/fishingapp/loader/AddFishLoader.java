package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.AddFishRequest;
import com.dev.fishingapp.data.model.AddFishResponse;
import com.dev.fishingapp.util.AppConstants;

import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by user on 5/28/2016.
 */
public class AddFishLoader extends AbstractLoader<AddFishResponse> {
    private AddFishRequest addFishRequest;

    public AddFishLoader(AddFishRequest addFishRequest) {
        super(FishingApplication.getContext());
        this.addFishRequest = addFishRequest;
    }

    @Override
    protected AddFishResponse doLoadInBackground() {
        AddFishResponse response ;

        if (addFishRequest.getFishimage().equals("no"))
            response=getService().addFish("application/x-www-form-urlencoded", "application/json", AppConstants.ADD_FISH_API_ACTION, addFishRequest.getUid(), addFishRequest.getTitle(), addFishRequest.getDescription(), addFishRequest.getPbval(), addFishRequest.getTypeoffishnid(), addFishRequest.getDate(), addFishRequest.getWhere_caught(), addFishRequest.getLength(), addFishRequest.getWeight(),addFishRequest.getFishimage());
        else {
            TypedFile typedFile=new TypedFile("image/*", addFishRequest.getImage());
            response=getService().addNewFishWithFile(new TypedString(AppConstants.ADD_FISH_API_ACTION), new TypedString(addFishRequest.getUid()), new TypedString(addFishRequest.getTitle()),  new TypedString(addFishRequest.getDescription()),  new TypedString(addFishRequest.getPbval()),  new TypedString(addFishRequest.getTypeoffishnid()),  new TypedString(addFishRequest.getDate()),  new TypedString(addFishRequest.getWhere_caught()),  new TypedString(addFishRequest.getLength()),  new TypedString(addFishRequest.getWeight()), new TypedString(addFishRequest.getFishimage()),typedFile);


           // response = getService().addNewAlbumWithFile(new TypedString(AppConstants.ADD_ALBUM_API_ACTION), new TypedString(addNewAlbumRequest.getUid()), new TypedString(addNewAlbumRequest.getTitle()), new TypedString(addNewAlbumRequest.getDescription()), new TypedString(addNewAlbumRequest.getLocname()), new TypedString(addNewAlbumRequest.getStreet()), new TypedString(addNewAlbumRequest.getAdditional()), new TypedString(addNewAlbumRequest.getCountry_name()), new TypedString(addNewAlbumRequest.getCountry()), new TypedString(addNewAlbumRequest.getAlbumimage()),typedFile );
        }


        return response;
    }
}
