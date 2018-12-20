package com.montnets.liveroom.im;

/**
 * Created by songlei on 2018/12/20.
 */
public class IMConstants {

    //协议
    public static final int CMD_MSG_SYSTEM_TIP = 999;           //聊天室系统提示
    public static final int CMD_ROOM_UPDATE = 1000;             //聊天室房间人数更新提示
    public static final int CMD_MSG_TXT = 1004;                 //聊天室通用消息
    public static final int CMD_MSG_GIFT = 1005;                //送礼消息
    public static final int CMD_MSG_STAR = 1006;                //点赞消息
    public static final int CMD_MSG_CUSTOMIZE = 1055;           //聊天室自定义消息

    public static final int CMD_MSG_VIDEO_STATE = 2002;         //状态通知消息

    public static final int CMD_MSG_SILENCE_LIST = 2010;        //获取禁言用户列表
    public static final int CMD_MSG_ADD_SILENCE = 2011;         //单个禁言
    public static final int CMD_MSG_REMOVE_SILENCE = 2012;      //单个解禁
    public static final int CMD_MSG_SILENCE_STATE = 2013;       //查询用户禁言状态
    public static final int CMD_MSG_REMOVE_SINGLE = 2022;       //清除单条聊天记录
    public static final int CMD_MSG_SILENCE_ALL = 2024;         //全体禁言请求

    public static final int CMD_MSG_QUESTION_RECEIVED = 2121;   //收到后台发送的问卷

    public static final int CMD_NOTIFICATION = 5001;            //公告栏通知消息
}
