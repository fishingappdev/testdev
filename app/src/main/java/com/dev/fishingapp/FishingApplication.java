package com.dev.fishingapp;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by user on 4/20/2016.
 */
public class FishingApplication extends Application {
    private static Context mContext = null;
    private static FishingApplication instance = null;
    private ImageLoader imageLoader;

    static public Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        instance = this;
        FishingApplication fishingApplication = this;
    }

    public static FishingApplication getInstance() {
        return instance;
    }

    public ImageLoader getImageLoader() {
        setupImageLoader();
        return imageLoader;
    }

    private void setupImageLoader() {
        imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {
            FishingApplication application = FishingApplication.getInstance();
            File cacheDir = StorageUtils.getCacheDirectory(application);
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(false)
                    .resetViewBeforeLoading(true)
                    .cacheOnDisk(true)
                    .displayer(new FadeInBitmapDisplayer(250))
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .build();

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(application)
                    .defaultDisplayImageOptions(options)
                    .diskCacheSize(50 * 1024 * 1024)
                    .diskCache(new UnlimitedDiscCache(cacheDir))
                    .build();
            imageLoader.init(config);
        }
    }

}
