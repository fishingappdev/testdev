package com.dev.fishingapp.loader;

/**
 * Created by skumari on 5/1/2016.
 */

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.WatchVideoRequest;
import com.dev.fishingapp.data.model.WatchVideoResponse;

public class WatchVideoLoader extends AbstractLoader<WatchVideoResponse> {
    WatchVideoRequest watchVideoRequest;

    public WatchVideoLoader(WatchVideoRequest watchVideoRequest) {
        super(FishingApplication.getContext());
        this.watchVideoRequest = watchVideoRequest;
    }

    protected WatchVideoResponse doLoadInBackground() {
        return getService().watchVideo("application/json","application/json",watchVideoRequest);
    }
}
