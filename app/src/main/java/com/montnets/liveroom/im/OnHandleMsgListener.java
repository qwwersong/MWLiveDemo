package com.montnets.liveroom.im;

import com.montnets.liveroom.im.bean.MsgCustomize;
import com.montnets.liveroom.im.bean.MsgGift;
import com.montnets.liveroom.im.bean.MsgJoinRoom;
import com.montnets.liveroom.im.bean.MsgMessage;
import com.montnets.liveroom.im.bean.MsgNotice;
import com.montnets.liveroom.im.bean.MsgQuestion;
import com.montnets.liveroom.im.bean.MsgSilence;
import com.montnets.liveroom.im.bean.MsgStar;
import com.montnets.liveroom.im.bean.MsgSystemTip;

/**
 * Created by songlei on 2018/12/20.
 */
public interface OnHandleMsgListener {
    /**
     * 收到进入聊天室
     */
    void onReceiveEnter(MsgJoinRoom enterRoom);

    /**
     * 收到聊天信息
     */
    void onReceivedMessage(MsgMessage message);

    /**
     * 收到礼物信息
     */
    void onReceivedGift(MsgGift gift);

    /**
     * 收到公告信息
     */
    void onReceivedNotice(MsgNotice notice);

    /**
     * 收到点赞信息
     */
    void onReceivedStar(MsgStar star);

    /**
     * 收到自定义信息
     */
    void onReceivedCustomMsg(MsgCustomize customize);

    /**
     * 收到系统提示信息
     */
    void onReceivedSysTip(MsgSystemTip systemTip);

    /**
     * 收到禁言信息
     */
    void onReceivedSilence(MsgSilence silence);

    /**
     * 收到问卷信息
     */
    void onReceivedQuestion(MsgQuestion question);

}
