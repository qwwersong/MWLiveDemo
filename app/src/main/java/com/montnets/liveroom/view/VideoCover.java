package com.montnets.liveroom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.montnets.liveroom.R;

/**
 * 播放器状态提示cover
 */
public class VideoCover extends RelativeLayout {
    private Context context;
    private ProgressBar pbCover;
    private TextView tvCoverTip;

    public VideoCover(Context context) {
        super(context);
    }

    public VideoCover(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView(){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_video_cover, this);
        pbCover = (ProgressBar) view.findViewById(R.id.pb_cover);
        tvCoverTip = (TextView) view.findViewById(R.id.tv_tip);
    }

    public void showTip(String tip){
        setVisibility(View.VISIBLE);
        pbCover.setVisibility(View.GONE);
        tvCoverTip.setVisibility(View.VISIBLE);
        tvCoverTip.setText(tip);
    }

    public void showBuffering(){
        setVisibility(View.VISIBLE);
        tvCoverTip.setVisibility(View.GONE);
        pbCover.setVisibility(View.VISIBLE);
    }

    public void hide(){
        setVisibility(View.GONE);
    }
}
