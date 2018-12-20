package com.montnets.liveroom.im.bean;

import java.io.Serializable;

/**
 * 消息：自定义信息
 */
public class MsgCustomize implements Serializable {
    public int cmd;             //消息命令号
    public String msg;          //消息标题
    public String nickName;     //昵称
    public String user_id;      //用户ID
    public String msg_id;       //消息ID
    public long send_time;      //消息发送时间戳
    public String room_id;      //消息所属房间号
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
        return "MsgCustomize{" +
                "cmd=" + cmd +
                ", msg='" + msg + '\'' +
                ", nickName='" + nickName + '\'' +
                ", user_id='" + user_id + '\'' +
                ", msg_id='" + msg_id + '\'' +
                ", send_time=" + send_time +
                ", room_id=" + room_id +
                ", data=" + data +
                '}';
    }
}
