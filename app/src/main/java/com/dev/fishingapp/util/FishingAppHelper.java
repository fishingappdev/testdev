package com.dev.fishingapp.util;

import com.dev.fishingapp.FishingApplication;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by skumari on 4/23/2016.
 */
public class FishingAppHelper {
    public static ImageLoader getImageLoader() {
        return FishingApplication.getInstance().getImageLoader();
    }
}
