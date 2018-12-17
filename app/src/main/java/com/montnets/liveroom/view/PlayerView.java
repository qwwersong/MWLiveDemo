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
import com.montnets.liveroom.utils.OrientationManager;
import com.montnets.mwlive.LiveRoom;
import com.montnets.mwlive.base.CommonHandler;
import com.montnets.mwlive.base.LogUtil;
import com.montnets.mwlive.player.OnPlayerListener;
import com.montnets.mwlive.player.PlayException;
import com.montnets.mwlive.player.PlayerConstants;
import com.montnets.mwlive.socket.OnPlayerStateListener;
import com.montnets.mwlive.socket.bean.MsgVideoState;
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
    private MWTextureView playerView;
    private VideoCover videoCover;
    private MyMediaController mediaController;
    private RelativeLayout rlContainer;
    private TextView tvCopyRight;               //水印控件

    private String videoUrl;
    private int type;                           //播放器的类型：直播、短视频
    private int playState = STATE_BUFFING;      //播放状态：缓冲中、播放、暂停、结束、异常
    private boolean isMain;                     //是否是主屏
    private ArrayList<String> rateList;
    private HashMap<String, String> rateMap;
    private OnPlayerViewListener onPlayerViewListener;

    private PlayerLayoutManager layoutManager;

    private CommonHandler handler = new CommonHandler(this);

    @Override
    public void handleMessage(Message msg) {
        hideHeader();
    }

    public PlayerView(Context context) {
        super(context);
        initView(context);
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
        LiveRoom.getInstance().registerOnPlayerStateListener(new OnPlayerStateListener() {
            @Override
            public void onPlayerState(MsgVideoState msgVideoState) {
                if (isMain) {
                    setVideoState(msgVideoState.data.live_status);
                } else {
                    setVideoState(msgVideoState.data.slave_status);
                }
            }
        });
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
                    //show
                    showHeader();

                    onPlayerViewListener.onClick();
                } else {
                    if (tag == 0) {
                        //hide
                        hideHeader();
                    } else {
                        //show
                        showHeader();
                    }
                }
            }
        });
    }

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

    private int tag = 0;

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
                    playState = STATE_BUFFING;
                    videoCover.showBuffering();
                    if (type == TYPE_VIDEO) {
                        mediaController.stopTimer();
                    }
                    break;
            }
        }
    };

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

    private void switchModelPlay(String url) {
        if (mediaController.getVisibility() == View.VISIBLE) {
            if (playState == STATE_PAUSE) {
                mediaController.startPlay();
            } else if (playState == STATE_PLAY) {
                playerView.startPlay(url, mediaController.getCurrentTime());
            }
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

    private void setVideoState(int state) {
        switch (state) {
            case PlayerConstants.LIVE_STATUS_PREVIEW:
                videoCover.showTip("视频未审核");
                break;
            case PlayerConstants.LIVE_STATUS_LIVING:
                break;
            case PlayerConstants.LIVE_STATUS_END:
                videoCover.showTip("播放结束");
                break;
            case PlayerConstants.LIVE_STATUS_ERROR:
                videoCover.showTip("异常中断");
                break;
            case PlayerConstants.LIVE_STATUS_TIMEOUT:
                videoCover.showTip("直播超时");
                break;
        }
    }

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
                    LogUtil.e("pp", "直播 分辨率 = " + rateList.get(position) + "url = " + play_url);
                    playerView.startPlay(play_url);
                } else if (type == TYPE_VIDEO) {
                    LogUtil.e("pp", "短视频 分辨率 = " + rateList.get(position) + "url = " + play_url);
                    switchModelPlay(play_url);
                }
                popupWindow.dismiss();
            }
        });
    }

    public void setCopyRight(String copyRight){
        tvCopyRight.setText(copyRight);
    }

    public void setRateList(ArrayList<String> rateList) {
        this.rateList = rateList;
    }

    public void setRateMap(HashMap<String, String> rateMap) {
        this.rateMap = rateMap;
    }

    public void showSmallView() {
        layoutManager.setLayoutType(rlContainer, PlayerLayoutManager.TYPE_SMALL);
        setMain(false);
    }

    public void showBigView() {
        layoutManager.setLayoutType(rlContainer, PlayerLayoutManager.TYPE_BIG);
        setMain(true);
    }

    public void showFullView() {
        layoutManager.setLayoutType(rlContainer, PlayerLayoutManager.TYPE_FULL);
        setMain(true);
    }

    public void showMediaController(boolean isShow) {
        if (isMain && type == TYPE_VIDEO) {
            if (isShow) {
                mediaController.setVisibility(View.VISIBLE);
            } else {
                mediaController.setVisibility(View.GONE);
            }
        }
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
}
