package com.dev.fishingapp.loader;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.dev.fishingapp.AbstractActivity;
import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.ErrorResponse;
import com.dev.fishingapp.service.FishingAppService;
import com.dev.fishingapp.service.FishingRestApi;
import com.dev.fishingapp.util.AlertMessageDialog;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.RetrofitError;


public abstract class AbstractLoader<T> extends AsyncTaskLoader<T> {

    private static Handler mHandler = new Handler();
    private final FishingAppService mFishingAppService;
    private T mResult;
    private RetrofitError mError;
    AlertMessageDialog dialog;

    AbstractLoader(Context context) {
        super(context);
        mFishingAppService = new FishingRestApi(context.getApplicationContext()).getService();
    }

    protected abstract T doLoadInBackground();

    FishingAppService getService() {
        return mFishingAppService;
    }

    @Override
    protected void onStartLoading() {
        if (mResult == null) {
            forceLoad();
        } else {
            deliverResult(mResult);
        }
    }

    @Override
    public final T loadInBackground() {
        try {
            return mResult = doLoadInBackground();
        } catch (RetrofitError e) {
            mError = e;
            return null;
        }
    }

    @Override
    public void deliverResult(final T data) {
        mResult = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    RetrofitError getError() {
        return mError;
    }

    public abstract static class AbstractLoaderCallbacks<T> implements LoaderManager.LoaderCallbacks<T> {

        private final AppCompatActivity mActivity;

        public AbstractLoaderCallbacks(final AppCompatActivity activity, boolean showProgressDialog) {
            mActivity = activity;
            if (showProgressDialog)
                ((AbstractActivity) mActivity).showProgressDialog();
        }


        @Override
        public void onLoadFinished(final Loader<T> loader, T response) {
            LoaderManager loaderManager = mActivity.getSupportLoaderManager();
            loaderManager.destroyLoader(loader.getId());
            ((AbstractActivity) mActivity).hideProgressDialog();

            final AbstractLoader<T> abstractLoader = (AbstractLoader<T>) loader;
            RetrofitError error = abstractLoader.getError();

            if (error != null) {
                onError(abstractLoader, error);
            } else {
                onResponse(abstractLoader, response);
            }
        }

        @Override
        public void onLoaderReset(Loader<T> loader) {
        }

        public abstract void onResponse(final AbstractLoader<T> loader,
                                        T response);

        public boolean onError(final AbstractLoader<T> loader, RetrofitError error) {
            if (error.isNetworkError()) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        AlertMessageDialog dialog = new AlertMessageDialog(mActivity,mActivity.getString(R.string.error_txt),mActivity.getString(R.string.network_error));
                        dialog.setAcceptButtonText(mActivity.getString(R.string.ok_txt));
                        dialog.show();
                        /*GeneralDialogFragment networkErrorDialog = GeneralDialogFragment.newInstance(R.string.network_error);
                        ((AbstractActivity) mActivity).showDialogFragment(networkErrorDialog);
 */
                    }
                });
                return true;
            } else{
                ErrorResponse errorResponse = getErrorResponse(error);
                Toast.makeText(mActivity, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                return true;
            }
        }
    }

    public static ErrorResponse getErrorResponse(RetrofitError error) {
        Gson gson = new Gson();
        BufferedReader br = null;
        String str;
        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new InputStreamReader(error.getResponse().getBody().in()));
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return gson.fromJson(sb.toString(), ErrorResponse.class);
    }
}
