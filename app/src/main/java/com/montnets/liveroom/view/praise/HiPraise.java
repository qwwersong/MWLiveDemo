package com.montnets.liveroom.view.praise;

import android.graphics.Bitmap;

import java.util.Random;

public class HiPraise implements IPraise {
    protected Bitmap bitmap;
    public float scale = 2.0f;
    public float alpha = 2.0f;
    public long duration;
    public long startDelay;
    public long delayAplhaTime;

    public HiPraise(Bitmap bitmap) {
        this.bitmap = bitmap;
        final int maxDuration = 3000;
        final int minDuration = 2000;
        int minDelayAlphaTime = minDuration / 4;
        duration = Utils.rondomRange(maxDuration, minDuration);
        delayAplhaTime = new Random().nextInt((int) duration)
                % (duration - minDelayAlphaTime + 1) + minDelayAlphaTime;
    }

    @Override
    public IDrawable toDrawable() {
        return new PraiseDrawable(bitmap, scale,
                alpha, duration, startDelay, delayAplhaTime, 0.45f);
    }

}
