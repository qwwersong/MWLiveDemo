package com.montnets.liveroom.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.montnets.liveroom.R;


/**
 * Description: 加载图片的工具类
 * Created by hubj on 2016/7/27 18:09.
 */
public class GlideUtils {

    public static void displayHeadImg(Context context, String url, ImageView imageView) {
        try {
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.header_default)
                    .error(R.drawable.header_default)
                    .crossFade()
                    .into(imageView);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static void displayGiftImg(Context context, String url, ImageView imageView) {
        try {
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.drawable.transparent_bg)
                    .error(R.drawable.transparent_bg)
                    .crossFade()
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
