package com.montnets.liveroom.im;


import com.montnets.liveroom.im.bean.MsgVideoState;

/**
 * Created by songlei on 2018/12/11.
 */
public interface OnPlayerStateListener {

    /**
     * 监听视频流状态
     * @param msgVideoState 流状态
     */
    void onPlayerState(MsgVideoState msgVideoState);
}
