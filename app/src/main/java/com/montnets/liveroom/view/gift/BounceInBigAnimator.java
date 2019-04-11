package com.montnets.liveroom.view.gift;

import android.view.View;

import com.daimajia.androidanimations.library.BaseViewAnimator;
import com.nineoldandroids.animation.ObjectAnimator;

public class BounceInBigAnimator extends BaseViewAnimator {
    @Override
    public void prepare(View target) {
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target,"alpha",0,1, 1 ,1),
                ObjectAnimator.ofFloat(target,"scaleX",0.3f,3.0f,0.8f,1),
                ObjectAnimator.ofFloat(target,"scaleY",0.3f,3.0f,0.8f,1)
        );
    }
}