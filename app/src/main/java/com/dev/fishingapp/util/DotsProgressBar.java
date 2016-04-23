package com.dev.fishingapp.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;

import com.dev.fishingapp.R;


public class DotsProgressBar extends Drawable {
    private float mRadius;

    private Paint mPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Handler mHandler = new Handler();
    private int mIndex = 0;
    private int widthSize, heightSize;
    private int margin = 15;
    private int mDotCount = 3;
    private View view = null;

    /**
     * @param context
     * @param view    view to get Height and Width
     */
    public DotsProgressBar(Context context, View view) {
        super();
        this.view = view;
        init(context);
    }


//    public DotsProgressBar(Context context) {
//        super();
//        init(context);
//    }


    private void init(Context context) {
        mRadius = context.getResources().getDimension(R.dimen.circle_indicator_radius);
        // dot fill color
        mPaintFill.setStyle(Style.FILL);
        mPaintFill.setColor(context.getResources().getColor(R.color.set_label_color));
        // dot background color
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(context.getResources().getColor(R.color.divider));
        validateSize(null);
        start();
    }

    public void validateSize(View mView) {
        if (mView != null)
            this.view = mView;
        widthSize = 400;
        heightSize = 0;
        if (view != null) {
            int newHeightSize = view.getHeight();
            widthSize = view.getWidth();
            heightSize = newHeightSize - ((newHeightSize / 100) * 50);
        }
        heightSize += (int) mRadius * 2;
    }

    public void validateHeightSize(int mViewHeight) {
        if (mViewHeight != 0) {
            heightSize = (int) (mViewHeight - mRadius) / 2;
        }
//        if (mViewHeight != 0) {
//
//            heightSize = mViewHeight - ((mViewHeight / 100) * 50);
//            heightSize += (int) mRadius * 2;
//        }
    }

    public void validateWidthSize(int mViewWidth) {
        if (mViewWidth != 0) {
            widthSize = mViewWidth;
        }
    }

    public void setDotsCount(int count) {
        mDotCount = count;
    }

    public void start() {
        mIndex = -1;
        mHandler.removeCallbacks(mRunnable);
        mHandler.post(mRunnable);
    }

    public void stop() {
        mHandler.removeCallbacks(mRunnable);
    }

    private int step = 1;
    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {

            mIndex += step;


            if (mIndex > mDotCount - 1) {
                mIndex = 0;
                step = 1;
            }
            invalidateSelf();

            mHandler.postDelayed(mRunnable, 500);

        }

    };


    @Override
    public void draw(Canvas canvas) {
        float dX = (widthSize - ((mDotCount * mRadius) + (mDotCount * margin))) / 2.0f;
        float dY = heightSize;
        for (int i = 0; i < mDotCount; i++) {
            if (i == mIndex) {
                canvas.drawCircle(dX, dY, mRadius, mPaintFill);
            } else {
                canvas.drawCircle(dX, dY, mRadius, mPaint);
            }

            dX += (2 * mRadius + margin);

        }
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}