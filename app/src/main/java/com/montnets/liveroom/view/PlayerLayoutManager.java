package com.montnets.liveroom.view;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.montnets.mwlive.base.ScreenUtil;

/**
 * Created by songlei on 2018/12/07.
 */
public class PlayerLayoutManager {
    public static final int TYPE_SMALL = 1;
    public static final int TYPE_BIG = 2;
    public static final int TYPE_FULL = 3;
    private RelativeLayout.LayoutParams layoutParamsBig;
    private RelativeLayout.LayoutParams layoutParamsSmall;
    private RelativeLayout.LayoutParams layoutParamsFull;

    public PlayerLayoutManager(Context context){
        int width_big = ScreenUtil.getScreenWidth(context);
        int height_big = width_big * 9 / 16;
        layoutParamsBig = new RelativeLayout.LayoutParams(width_big, height_big);

        int width_small = ScreenUtil.dip2px(context, 200);
        int height_small = width_small * 9 / 16;
        layoutParamsSmall = new RelativeLayout.LayoutParams(width_small, height_small);
        layoutParamsSmall.addRule(RelativeLayout.ALIGN_BOTTOM);
        layoutParamsSmall.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        int width_full = ScreenUtil.getScreenHeight(context);
        int height_full = ScreenUtil.getScreenWidth(context);
        layoutParamsFull = new RelativeLayout.LayoutParams(width_full, height_full);
    }

    public void setLayoutType(View view, int type){
        switch (type) {
            case TYPE_SMALL:
                view.setLayoutParams(layoutParamsSmall);
                break;
            case TYPE_BIG:
                view.setLayoutParams(layoutParamsBig);
                break;
            case TYPE_FULL:
                view.setLayoutParams(layoutParamsFull);
                break;
        }
    }
}
