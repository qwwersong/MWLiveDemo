package com.montnets.liveroom.view.praise;

import android.graphics.Bitmap;


public class HiPraiseWithCallback extends HiPraise {

    private OnDrawCallback mOnDrawCallback;

    public HiPraiseWithCallback(Bitmap bitmap, OnDrawCallback onDrawCallback) {
        super(bitmap);
        mOnDrawCallback = onDrawCallback;
    }

    @Override
    public IDrawable toDrawable() {
        return new PraiseWithCallbackDrawable(bitmap, scale,
                alpha, duration, startDelay, delayAplhaTime, 0.45f, mOnDrawCallback);
    }
}
