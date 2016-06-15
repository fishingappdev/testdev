package com.dev.fishingapp.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.dev.fishingapp.FishingApplication;
import com.dev.fishingapp.R;

import org.apache.commons.validator.routines.UrlValidator;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 5/2/2016.
 */
public class Utils {

    public static String dateFormat = " EEE dd/MM/yyyy hh:mm";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

    public static void watchVideo(String imageUrl, Activity activity) {
        if (imageUrl != null
                && !imageUrl.isEmpty()
                && UrlValidator.getInstance().isValid(imageUrl)) {
            FishingApplication app = FishingApplication.getInstance();
            if (app.checkCanOpenVideoMP4Url(imageUrl)) {
                if (imageUrl.contains("vimeo")) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(imageUrl));
                    activity.startActivity(i);
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String[] segments = imageUrl.split("/");
                    File file = new File(app.getFileCacheLocation(), segments[segments.length - 1]);
                    Uri uri = Uri.fromFile(file.getAbsoluteFile());
                    if (file.exists())
                        intent.setDataAndType(uri, "video/*");
                    else
                        intent.setDataAndType(Uri.parse(imageUrl), "video/mp4");
                    activity.startActivity(intent);
                }
            } else {
                Toast.makeText(activity, R.string.VIDEO_PLAYER_ERROR, Toast.LENGTH_LONG).show();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/search?q=mp4+video+player&c=apps&price=1&rating=1"));
                activity.startActivity(i);
            }
        }
    }





    public static String ConvertMilliSecondsToFormattedDate(long milliSeconds){
        Timestamp stamp = new Timestamp(milliSeconds*1000);
        Date date = new Date(stamp.getTime());
        return simpleDateFormat.format(date);

    }

}
