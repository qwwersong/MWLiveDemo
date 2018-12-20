package com.montnets.liveroom.im.bean;

import java.io.Serializable;

/**
 * 消息：点赞
 */
public class MsgStar implements Serializable {
    public int cmd;             //消息命令号
    public String msg;          //消息标题
    public String nickName;     //昵称
    public String imgUrl;       //用户头像
    public String user_id;      //用户ID
    public long send_time;      //消息发送时间戳
    public String room_id;      //消息所属房间号

    @Override
    public String toString() {
        return "MsgStar{" +
                "cmd=" + cmd +
                ", msg='" + msg + '\'' +
                ", nickName='" + nickName + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", user_id='" + user_id + '\'' +
                ", send_time=" + send_time +
                ", room_id=" + room_id +
                '}';
    }
}
