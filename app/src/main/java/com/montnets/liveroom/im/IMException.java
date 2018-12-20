package com.montnets.liveroom.im;

/**
 * Created by songlei on 2018/12/20.
 */
public class IMException extends Exception {
    //连接异常状态码
    public static final int ERROR_CONNECT = 0x1;                //socket连接异常
    public static final int ERROR_REQUEST_FAIL = 0x2;           //请求聊天室地址异常
    public static final int ERROR_CONNECT_TIME_OUT = 0x3;       //socket连接超时
    public static final int ERROR_RECEIVE_MSG_FAIL = 0x4;       //获取服务器推送消息失败
    public static final int ERROR_PARSE_MSG_FAIL = 0x5;         //解析消息错误

    public int code;           //错误码
    public String cause;       //错误信息

    public IMException(int code, String cause) {
        this.code = code;
        this.cause = cause;
    }
}
