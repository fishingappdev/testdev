package com.dev.fishingapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.dev.fishingapp.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by skumari on 3/9/2016.
 */
public class UpdateAlbumImageUtil {
    private Uri fileUri;
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int GALLERY_CAPTURE_IMAGE_REQUEST_CODE = 101;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "FishingApp";
    private Object mParent;
    private ImageView imageView;
    private int position;

    /**
     * @param parent parent should be an activity or a fragment
     * @return instance of fragment
     */
    public static UpdateAlbumImageUtil getInstance(Object parent) {
        UpdateAlbumImageUtil obj = new UpdateAlbumImageUtil();
        obj.mParent = parent;
        return obj;
    }

    private Context getContext() {
        if (mParent instanceof Activity) {
            return ((Context) mParent);
        } else {
            return ((Context) ((Fragment) mParent).getActivity());
        }
    }

    public void updateProfilePic(int what, ImageView imageView, int position) {
        this.imageView = imageView;
        this.position = position;
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getContext().getApplicationContext(),
                    "Sorry!! Your device doesn't support Camera",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (StorageHelper.isExternalStorageAvailableAndWriteable()) {
            if (what == CAMERA_CAPTURE_IMAGE_REQUEST_CODE)
                captureImage();
            else if (what == GALLERY_CAPTURE_IMAGE_REQUEST_CODE)
                selectImage();
        } else {
            Toast.makeText(getContext(), "Sorry!! Your device doesn't support SD Card", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isDeviceSupportCamera() {
        // this device has a camera
        // no camera on this device
        return getContext().getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        // start the image capture Intent
        if (mParent instanceof Activity) {
            ((Activity) mParent).startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        } else {
            ((Fragment) mParent).getActivity().startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }
    }

    private void selectImage() {
        if (StorageHelper.isExternalStorageAvailableAndWriteable()) {
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            if (mParent instanceof Activity) {
                ((Activity) mParent).startActivityForResult(i, GALLERY_CAPTURE_IMAGE_REQUEST_CODE);
            } else {
                ((Fragment) mParent).getActivity().startActivityForResult(i, GALLERY_CAPTURE_IMAGE_REQUEST_CODE);
            }
        } else {
            Toast.makeText(getContext(), "Sorry!! Your device doesn't support SD Card", Toast.LENGTH_LONG).show();
        }

    }

    protected File resizePic(Uri fileUri) {
        File file, mReturnFile = null;
        Bitmap b = null;
//        float density;
//        density = getResources().getDisplayMetrics().density;
//        int THUMBSIZE = (int) (200 * density);
        String filePath = DocumentUtils.getPath(getContext().getApplicationContext(), fileUri);
        Log.e("File", "filePath: " + filePath);
        try {
            file = new File(new URI("file://" + filePath.replace(" ", "%20")));
            Log.d("Original Size:", "" + file.length());
            b = BitmapUtils.getOrientatedScaledBitmap(file, getContext().getApplicationContext());
//            b = ThumbnailUtils.extractThumbnail(BitmapUtils.getOrientatedScaledBitmap(file, getActivity().getApplicationContext()), THUMBSIZE, THUMBSIZE);
            // create a file to write bitmap data
            mReturnFile = new File(getContext().getApplicationContext().getCacheDir().getAbsolutePath(), "AlbumImage" + position + ".jpeg");
            // Convert bitmap to byte array
            FileOutputStream fout;
            fout = new FileOutputStream(mReturnFile);
            b.compress(Bitmap.CompressFormat.JPEG, 75, fout);
            fout.close();
            fout.flush();
            b.recycle();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("Compressed Size:", "" + mReturnFile.length());
        return mReturnFile;
    }

    protected void performCrop(Uri picUri) {
        Uri destination = Uri.fromFile(new File(getContext().getApplicationContext().getCacheDir(), "albumcropped"+position+".jpg"));
        if (mParent instanceof Activity) {
            Crop.of(picUri, destination).start(((Activity) mParent), Crop.REQUEST_CROP);
        } else {
            Crop.of(picUri, destination).start(getContext(), ((Fragment) mParent), Crop.REQUEST_CROP);
        }
    }


    /**
     * ----------------------------- Helper Methods for Image capturing  ------------------------------------------------- *
     */

    protected void handleCrop(int resultCode, Intent result) {
        if (resultCode == Activity.RESULT_OK) {
            Uri finalUri = Crop.getOutput(result);
            if (finalUri != null) {
                final DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.drawable.user_icon)
                        .showImageForEmptyUri(R.drawable.user_icon)
                        .showImageOnFail(R.drawable.user_icon)
                        .cacheInMemory(true).cacheOnDisc(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .postProcessor(new BitmapProcessor() {
                            @Override
                            public Bitmap process(Bitmap bmp) {
                                return Bitmap.createScaledBitmap(bmp, imageView.getWidth(), imageView.getHeight(), false);
                            }
                        }).build();
                FishingAppHelper.removeImageFromCache(finalUri.toString());
                FishingAppHelper.getImageLoader().displayImage(finalUri.toString(), imageView, displayImageOptions);
                Intent intent = new Intent(AppConstants.SET_IMAGE_ALBUM_CALLBACK_BROADCAST);
                intent.putExtra("data", finalUri.getPath().toString());
                intent.putExtra("position", position);
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
            }

        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(getContext(), Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Creating file uri to store image/video
     */
    protected Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    protected File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {

                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        File mUri;
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_CAPTURE_IMAGE_REQUEST_CODE:
                    mUri = resizePic(fileUri);
                    performCrop(Uri.fromFile(mUri));
                    break;
                case GALLERY_CAPTURE_IMAGE_REQUEST_CODE:
                    mUri = resizePic(data.getData());
                    performCrop(Uri.fromFile(mUri));
                    break;
                case Crop.REQUEST_CROP:
                    handleCrop(resultCode, data);
                    break;
                default:
                    break;
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
        }
    }
}
