package com.dev.fishingapp.loader;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.data.model.EpisodeListResponse;
import com.dev.fishingapp.util.AppConstants;

/**
 * Created by user on 5/21/2016.
 */
public class EpisodeListLoader extends AbstractLoader<EpisodeListResponse> {

    public EpisodeListLoader() {
        super(FishingApplication.getContext());
    }

    @Override
    protected EpisodeListResponse doLoadInBackground() {
        EpisodeListResponse reponse=getService().getEpisodeList("application/x-www-form-urlencoded", "application/json", AppConstants.EPISODE_LIST_API_ACTION,"all");
        return reponse;
    }
}
