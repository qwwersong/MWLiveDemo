package com.montnets.liveroom.im;

/**
 * Created by songlei on 2018/12/20.
 */
public interface OnIMStateListener {
    /**
     * 连接成功
     */
    void onSuccess();

    /**
     *  聊天室异常状态
     */
    void onError(IMException e);
}
