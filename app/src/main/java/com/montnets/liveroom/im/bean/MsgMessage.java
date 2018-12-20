package com.montnets.liveroom.im.bean;

import java.io.Serializable;

/**
 * 消息：用户聊天信息
 * Created by songlei on 2018/11/16.
 */
public class MsgMessage implements Serializable {
    public int cmd;             //消息命令号 必填
    public String msg;          //消息标题 必填
    public String nickName;     //昵称 必填
    public String imgUrl;       //用户头像url 必填
    public String user_id;      //用户ID 必填
    public String msg_id;       //消息ID
    public long send_time;      //消息发送时间戳   eg: 1533174925108
    public String room_id;         //消息所属房间号
    public int silence;         //发送消息用户是否被禁言   1-是 0-否
    public String sender_head;  //消息原始发送者头像
    public DataBean data;

    public static class DataBean implements Serializable{
        public String msgbody;          //消息体

        @Override
        public String toString() {
            return "DataBean{" +
                    "msgbody='" + msgbody + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MsgMessage{" +
                "cmd=" + cmd +
                ", msg='" + msg + '\'' +
                ", nickName='" + nickName + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", user_id='" + user_id + '\'' +
                ", msg_id='" + msg_id + '\'' +
                ", send_time=" + send_time +
                ", room_id=" + room_id +
                ", silence=" + silence +
                ", sender_head='" + sender_head + '\'' +
                ", data=" + data +
                '}';
    }
}
