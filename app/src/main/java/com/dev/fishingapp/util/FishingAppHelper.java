package com.dev.fishingapp.util;

import android.text.TextUtils;

import com.dev.fishingapp.FishingApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

/**
 * Created by skumari on 4/23/2016.
 */
public class FishingAppHelper {
    public static ImageLoader getImageLoader() {
        return FishingApplication.getInstance().getImageLoader();
    }
    public static void removeImageFromCache(String path) {
        if (path != null && !TextUtils.isEmpty(path)) {
            MemoryCacheUtils.removeFromCache(path, getImageLoader().getMemoryCache());
            DiskCacheUtils.removeFromCache(path, getImageLoader().getDiskCache());
        }
    }
}
