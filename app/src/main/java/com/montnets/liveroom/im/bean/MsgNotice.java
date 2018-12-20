package com.montnets.liveroom.im.bean;

import java.io.Serializable;

/**
 * 消息：公告栏
 * Created by songlei on 2018/11/17.
 */
public class MsgNotice implements Serializable {
    public int cmd;             //消息命令号 必填
    public DataBean data;

    public static class DataBean implements Serializable{
        public String msgbody;              //消息体 必填    msgbody为自定义json结构转为字符串类型，json结构自定义，聊天室提供通道传输。
        public String room_online_users;    //聊天室在线人数 必填
        public String room_views;           //聊天室参与人数 必填

        @Override
        public String toString() {
            return "DataBean{" +
                    "msgbody='" + msgbody + '\'' +
                    ", room_online_users='" + room_online_users + '\'' +
                    ", room_views='" + room_views + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MsgNotice{" +
                "cmd=" + cmd +
                ", data=" + data +
                '}';
    }
}
