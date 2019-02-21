package com.montnets.liveroom;

public class VideoConstants {
    public static final int TYPE_LIVE = 1;
    public static final int TYPE_VIDEO = 2;
    public static final int TYPE_LIVE_RECORD = 3;

    public static final int STATE_PREVIEW = 0;      //预告
    public static final int STATE_PLAYING = 1;      //正在直播
    public static final int STATE_FINISH = 2;       //直播结束
    public static final int STATE_ERROR = 3;        //直播异常
    public static final int STATE_OVER_VIEW = 4;    //过期

    public static final String TYPE = "type";
    public static final String VIDEO_ID = "video_id";

    public static final String MODEL_ORIGINAL = "原画";
    public static final String MODEL_480P = "480P";
    public static final String MODEL_720P = "720P";
}
