package com.montnets.liveroom.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.montnets.liveroom.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 播放器控制组件
 * Created by songlei on 2018/11/21.
 */
public class MyMediaController extends LinearLayout {
    private Context context;
    private ToggleButton togglePlay;
    private SeekBar seekBar;
    private TextView tv_currentTime;
    private TextView tv_totalTime;
    private TextView tv_switchModel;
    private ImageView zoom;
    private int totalTime;
    private boolean isPlayed;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (totalTime <= 0) {
                        totalTime = onMediaControllerListener.getDuration();
                        seekBar.setMax(totalTime);
                        tv_totalTime.setText(formatTime(totalTime));
                    }
                    int currentTime = onMediaControllerListener.getCurrentTime();
                    if (currentTime > 0) {
                        tv_currentTime.setText(formatTime(currentTime));
                        seekBar.setProgress(currentTime);
//                        LogUtil.e("pp", "currentTime = " + currentTime + " totalTime = " + totalTime);
                        if (currentTime == totalTime) {
                            stopProgress();
                        }
                    } else {
                        tv_currentTime.setText("00:00");
                        seekBar.setProgress(0);
                    }
                    handler.sendEmptyMessageDelayed(1, 1000);
                    break;
                default:
                    break;
            }
        }
    };

    private String formatTime(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        return formatter.format(new Date(time));
    }

    public MyMediaController(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_media_controller, this);
        togglePlay = (ToggleButton) view.findViewById(R.id.toggle_play);
        seekBar = (SeekBar) view.findViewById(R.id.seekbar);
        tv_currentTime = (TextView) view.findViewById(R.id.textView_playtime);
        tv_totalTime = (TextView) view.findViewById(R.id.textView_totaltime);
        tv_switchModel = (TextView) view.findViewById(R.id.tv_switch_model);
        zoom = (ImageView) view.findViewById(R.id.imageView_fullscreen);
        initListener();
    }

    private void initListener() {
        togglePlay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!isPlayed) {
                        onMediaControllerListener.startPlay();
                        isPlayed = true;
                    } else {
                        onMediaControllerListener.resumePlay();
                    }
//                    startTimer();
                } else {
                    if (seekBar.getProgress() == 100) {
                        onMediaControllerListener.stopPlay();
                    } else {
                        onMediaControllerListener.pausePlay();
                    }
                    stopTimer();
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                stopTimer();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                onMediaControllerListener.seekTo(seekBar.getProgress());
                togglePlay.setChecked(true);
            }
        });

        tv_switchModel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onMediaControllerListener.switchModel(tv_switchModel);
            }
        });

        zoom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onMediaControllerListener.showFullScreen();
            }
        });
    }

    public interface OnMediaControllerListener {

        int getDuration();

        int getCurrentTime();

        void seekTo(int ms);

        void startPlay();

        void pausePlay();

        void resumePlay();

        void stopPlay();

        void switchModel(TextView modelView);

        void showFullScreen();
    }

    private OnMediaControllerListener onMediaControllerListener;

    public void setOnMediaControllerListener(OnMediaControllerListener onMediaControllerListener) {
        this.onMediaControllerListener = onMediaControllerListener;
    }

    public void stopProgress() {
        isPlayed = false;
        togglePlay.setChecked(false);
        onMediaControllerListener.stopPlay();
        seekBar.setProgress(0);
        tv_currentTime.setText("00:00");
        stopTimer();
    }

    public void stopTimer() {
        handler.removeMessages(1);
    }

    public void startTimer() {
        handler.sendEmptyMessageDelayed(1, 1000);
    }

    public void startPlay() {
        togglePlay.setChecked(true);
    }

    public void pausePlay(){
        togglePlay.setChecked(false);
    }

    public int getCurrentTime(){
        return seekBar.getProgress();
    }

    public void setModelText(String model){
        tv_switchModel.setText(model);
    }
}
