package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.AddFishResponse;
import com.dev.fishingapp.util.AppConstants;
import com.dev.fishingapp.util.FishingPreferences;
import java.io.File;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by user on 6/1/2016.
 */
public class UpdateProfielPicLoader extends AbstractLoader<AddFishResponse> {
    private File image;

    public UpdateProfielPicLoader(File image) {
        super(FishingApplication.getContext());
        this.image=image;
    }

    @Override
    protected AddFishResponse doLoadInBackground() {
        AddFishResponse response ;

            TypedFile typedFile=new TypedFile("image/*", image);
            response=getService().updateProfileImage(new TypedString(AppConstants.UPDATE_PROFILE_PIC_API_ACTION), new TypedString(FishingPreferences.getInstance().getCurrentUserId()), new TypedString("yes"),typedFile);

        return response;
    }
}
