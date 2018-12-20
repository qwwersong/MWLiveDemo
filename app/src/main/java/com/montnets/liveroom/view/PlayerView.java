package com.montnets.liveroom.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.montnets.liveroom.R;
import com.montnets.liveroom.adapter.IMAdapter;
import com.montnets.liveroom.im.IMManager;
import com.montnets.liveroom.im.OnPlayerStateListener;
import com.montnets.liveroom.im.bean.MsgVideoState;
import com.montnets.liveroom.utils.OrientationManager;
import com.montnets.mwlive.base.CommonHandler;
import com.montnets.mwlive.player.OnPlayerListener;
import com.montnets.mwlive.player.PlayException;
import com.montnets.mwlive.player.PlayerConstants;
import com.montnets.mwlive.view.MWTextureView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 封装播放控件组件
 * Created by songlei on 2018/12/04.
 */
public class PlayerView extends RelativeLayout implements CommonHandler.HandlerCallBack {
    public static final int TYPE_LIVE = 1;
    public static final int TYPE_VIDEO = 2;

    public static final int STATE_PLAY = 1;
    public static final int STATE_PAUSE = 2;
    public static final int STATE_FINISH = 3;
    public static final int STATE_ERROR = 4;
    public static final int STATE_BUFFING = 5;

    private static final int TIME_DELAY = 5 * 1000;
    private static final int MSG_HIDE = 0;
    private Context context;
    private MWTextureView playerView;           //播放器
    private VideoCover videoCover;              //播放器覆盖层（用于当前播放器状态）
    private MyMediaController mediaController;  //播放器控制器（只用于播放点播视频）
    private RelativeLayout rlContainer;
    private TextView tvCopyRight;               //水印控件

    private String videoUrl;
    private int type;                           //播放器的类型：直播、短视频
    private int playState = STATE_BUFFING;      //播放状态：缓冲中、播放、暂停、结束、异常
    private boolean isMain;                     //是否是主屏
    private ArrayList<String> rateList;
    private HashMap<String, String> rateMap;
    private OnPlayerViewListener onPlayerViewListener;

    private PlayerLayoutManager layoutManager;  //播放器布局管理类

    private CommonHandler handler = new CommonHandler(this);

    @Override
    public void handleMessage(Message msg) {
        hideHeader();
    }

    public PlayerView(Context context) {
        this(context, null);
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initParams(context, attrs);
        initView(context);
    }

    private void initParams(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PlayerView);
        if (typedArray != null) {
            isMain = typedArray.getBoolean(R.styleable.PlayerView_isMain, false);
            typedArray.recycle();
        }
    }

    private void initView(Context context) {
        this.context = context;

        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_player_view, this);
        playerView = (MWTextureView) rootView.findViewById(R.id.player_view);
        playerView.setScaleType(PlayerConstants.VIDEO_SCALE_FIT);
        playerView.setOnPlayerListener(onPlayerListener);

        videoCover = (VideoCover) rootView.findViewById(R.id.cover_view);
        videoCover.showBuffering();
        mediaController = (MyMediaController) rootView.findViewById(R.id.media_controller);
        rlContainer = (RelativeLayout) rootView.findViewById(R.id.player_container);
        tvCopyRight = (TextView) rootView.findViewById(R.id.tv_cover);

        layoutManager = new PlayerLayoutManager(context);
        if (isMain) {
            layoutManager.setLayoutType(rlContainer, PlayerLayoutManager.TYPE_BIG);
        } else {
            layoutManager.setLayoutType(rlContainer, PlayerLayoutManager.TYPE_SMALL);
        }

        initListener();
    }

    private void initListener() {
        mediaController.setOnMediaControllerListener(new MyMediaController.OnMediaControllerListener() {
            @Override
            public int getDuration() {
                return playerView.getDuration();
            }

            @Override
            public int getCurrentTime() {
                return playerView.getCurrentTime();
            }

            @Override
            public void seekTo(int ms) {
                playerView.seekTo(ms);
            }

            @Override
            public void startPlay() {
                playerView.startPlay(videoUrl);
            }

            @Override
            public void pausePlay() {
                playerView.pausePlay();
            }

            @Override
            public void resumePlay() {
                playerView.resumePlay();
            }

            @Override
            public void stopPlay() {
                playerView.stopPlay();
            }

            @Override
            public void switchModel(TextView modelView) {
                showPop(modelView);
            }

            @Override
            public void showFullScreen() {
                onPlayerViewListener.showFullScreen();
            }
        });

        rlContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMain) {
                    if (enableShow) {
                        showHeader();
                    }

                    onPlayerViewListener.onClick();
                } else {
                    if (enableShow) {
                        if (tag == 0) {
                            hideHeader();
                        } else {
                            showHeader();
                        }
                    }
                }
            }
        });

        IMManager.getInstance().registerOnPlayerStateListener(new OnPlayerStateListener() {
            @Override
            public void onPlayerState(MsgVideoState msgVideoState) {
                if (isMain) {
                    setVideoState(msgVideoState.data.live_status);
                } else {
                    setVideoState(msgVideoState.data.slave_status);
                }
            }
        });
    }

    public void initConfig(int type) {
        this.type = type;
        if (type == TYPE_LIVE) {
            mediaController.setVisibility(View.GONE);
        } else {
            if (isMain) {
                mediaController.setVisibility(View.VISIBLE);
            } else {
                mediaController.setVisibility(View.GONE);
            }
        }
    }

    public interface OnPlayerViewListener {
        void onClick();

        void showFullScreen();

        void showHeader();

        void hideHeader();
    }

    public void setOnPlayerViewListener(OnPlayerViewListener onPlayerViewListener) {
        this.onPlayerViewListener = onPlayerViewListener;
    }

    OnPlayerListener onPlayerListener = new OnPlayerListener() {

        @Override
        public void onError(PlayException e) {
            mediaController.stopProgress();
            playState = STATE_ERROR;
            videoCover.showTip(e.cause);
        }

        @Override
        public void onPlayState(int code) {
            switch (code) {
                case PlayerConstants.STATE_PLAYING:
                    videoCover.hide();
                    playState = STATE_PLAY;
                    if (type == TYPE_VIDEO) {
                        mediaController.startTimer();
                    }
                    OrientationManager.getInstance().initRotateListener(context);
                    break;
                case PlayerConstants.STATE_PAUSE:
                    playState = STATE_PAUSE;
                    break;
                case PlayerConstants.STATE_FINISH:
                    playState = STATE_FINISH;
                    videoCover.showTip("播放结束");
                    break;
                case PlayerConstants.STATE_BUFFERING:
                    if (playState != STATE_FINISH) {//这个判断原因：聊天室流状态和播放器状态在结束时可能会冲突
                        playState = STATE_BUFFING;
                        videoCover.showBuffering();
                        if (type == TYPE_VIDEO) {
                            mediaController.stopTimer();
                        }
                    }
                    break;
            }
        }
    };

    private void setVideoState(int state) {
        switch (state) {
            case PlayerConstants.LIVE_STATUS_PREVIEW:
                playState = STATE_ERROR;
                videoCover.showTip("视频未审核");
                break;
            case PlayerConstants.LIVE_STATUS_LIVING:
                break;
            case PlayerConstants.LIVE_STATUS_END:
                playState = STATE_FINISH;
                videoCover.showTip("播放结束");
                break;
            case PlayerConstants.LIVE_STATUS_ERROR:
                playState = STATE_ERROR;
                videoCover.showTip("异常中断");
                break;
            case PlayerConstants.LIVE_STATUS_TIMEOUT:
                playState = STATE_ERROR;
                videoCover.showTip("直播超时");
                break;
        }
    }

    //==================================播放控制=================================
    public int getPlayState(){
        return playState;
    }

    public void startPlayLive(String url) {
        playerView.startPlay(url);
    }

    public void startPlayVideo(String url) {
        videoUrl = url;
        mediaController.startPlay();
        if (isMain) {
            mediaController.setVisibility(View.VISIBLE);
        }
    }

    public void startPlayLive() {
        String model = rateList.get(0);
        String play_url = rateMap.get(model);
        playerView.startPlay(play_url);
        mediaController.setModelText(model);
    }

    public void startPlayVideo() {
        String model = rateList.get(0);
        videoUrl = rateMap.get(model);
        mediaController.startPlay();
        mediaController.setModelText(model);
        if (isMain) {
            mediaController.setVisibility(View.VISIBLE);
        }
    }

    public void stopPlay() {
        if (mediaController != null) {
            mediaController.stopProgress();
        }
        playerView.stopPlay();
        playerView.release();
    }

    public void pausePlay() {
        if (type == TYPE_LIVE) {
            playerView.pausePlay();
        } else {
            mediaController.pausePlay();
        }
    }

    public void resumePlay() {
        if (type == TYPE_LIVE) {
            playerView.resumePlay();
        } else {
            mediaController.startPlay();
        }
    }

    public void reset() {
        playerView.reset();
    }

    //====================================设置属性===================================

    /**
     * 设置水印数据
     */
    public void setCopyRight(String copyRight){
        tvCopyRight.setText(copyRight);
    }

    /**
     * 设置支持的分辨率列表
     */
    public void setRateList(ArrayList<String> rateList) {
        this.rateList = rateList;
    }

    /**
     * 设置分辨率地址数据
     */
    public void setRateMap(HashMap<String, String> rateMap) {
        this.rateMap = rateMap;
    }

    /**
     * 设置主屏
     */
    public void setMain(boolean isMain) {
        this.isMain = isMain;
        if (type == TYPE_VIDEO) {
            if (isMain) {
                mediaController.setVisibility(View.VISIBLE);
                tvCopyRight.setVisibility(View.VISIBLE);
            } else {
                mediaController.setVisibility(View.GONE);
                tvCopyRight.setVisibility(View.GONE);
            }
        }
    }

    public boolean isMain() {
        return isMain;
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ||
                newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
            if (isMain) {
                playerView.setScaleType(PlayerConstants.VIDEO_SCALE_FILL);
                layoutManager.setLayoutType(rlContainer, PlayerLayoutManager.TYPE_FULL);
                if (type == TYPE_VIDEO) {
                    if (handler.hasMessages(MSG_HIDE)) {
                        handler.removeMessages(MSG_HIDE);
                    }
                    showHeader();
                }
            }
        } else {
            if (isMain) {
                playerView.setScaleType(PlayerConstants.VIDEO_SCALE_FIT);
                layoutManager.setLayoutType(rlContainer, PlayerLayoutManager.TYPE_BIG);
                if (type == TYPE_VIDEO) {
                    if (handler.hasMessages(MSG_HIDE)) {
                        handler.removeMessages(MSG_HIDE);
                    }
                    showHeader();
                }
            }
        }
        super.onConfigurationChanged(newConfig);
    }

    //==================================状态显示========================================

    /**
     * 显示小屏
     */
    public void showSmallView() {
        layoutManager.setLayoutType(rlContainer, PlayerLayoutManager.TYPE_SMALL);
        setMain(false);
    }

    /**
     * 显示大屏
     */
    public void showBigView() {
        layoutManager.setLayoutType(rlContainer, PlayerLayoutManager.TYPE_BIG);
        setMain(true);
    }

    /**
     * 显示全屏
     */
    public void showFullView() {
        layoutManager.setLayoutType(rlContainer, PlayerLayoutManager.TYPE_FULL);
        setMain(true);
    }

    /**
     * 播放器控制器显示
     */
    public void showMediaController(boolean isShow) {
        if (isMain && type == TYPE_VIDEO) {
            if (isShow) {
                mediaController.setVisibility(View.VISIBLE);
            } else {
                mediaController.setVisibility(View.GONE);
            }
        }
    }

    private boolean enableShow;
    public void enableShowHeader(boolean enable){
        enableShow = enable;
    }

    private int tag = 0;
    private void showHeader(){
        onPlayerViewListener.showHeader();
        mediaController.setVisibility(View.VISIBLE);
        tag = 0;
        handler.sendEmptyMessageDelayed(MSG_HIDE, TIME_DELAY);
    }

    private void hideHeader(){
        onPlayerViewListener.hideHeader();
        mediaController.setVisibility(View.GONE);
        tag = 1;
        if (handler.hasMessages(MSG_HIDE)) {
            handler.removeMessages(MSG_HIDE);
        }
    }

    /**
     * 显示分辨率切换布局
     */
    private void showPop(TextView modelView) {
        RecyclerView lvModel = new RecyclerView(context);
        lvModel.setBackgroundColor(Color.WHITE);
        lvModel.setLayoutManager(new LinearLayoutManager(context));
        IMAdapter modelAdapter = new IMAdapter(context, rateList);
        lvModel.setAdapter(modelAdapter);
        final SmartPopupWindow popupWindow = SmartPopupWindow.Builder
                .build((Activity) context, lvModel)
                .createPopupWindow();
        popupWindow.showAtAnchorView(modelView, VerticalPosition.ABOVE, HorizontalPosition.CENTER);
        modelAdapter.setOnItemClickListener(new IMAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String model = rateList.get(position);
                String play_url = rateMap.get(model);
                mediaController.setModelText(model);
                if (type == TYPE_LIVE) {
                    playerView.startPlay(play_url);
                } else if (type == TYPE_VIDEO) {
                    switchModelPlay(play_url);
                }
                popupWindow.dismiss();
            }
        });
    }

    private void switchModelPlay(String url) {
        if (mediaController.getVisibility() == View.VISIBLE) {
            if (playState == STATE_PAUSE) {
                mediaController.startPlay();
            } else if (playState == STATE_PLAY) {
                playerView.startPlay(url, mediaController.getCurrentTime());
            }
        }
    }
}
