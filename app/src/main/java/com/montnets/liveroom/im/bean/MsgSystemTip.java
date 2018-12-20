package com.montnets.liveroom.im.bean;

import java.io.Serializable;

/**
 * 消息：系统提示信息
 */
public class MsgSystemTip implements Serializable {
    public int cmd;             //消息命令号
    public int room_id;         //房间号
    public String nick_name;    //昵称
    public String msg;          //提示内容
    public int type;            //提示类型  1-单个禁言提示 2-全体禁言提示
    public String send_time;    //发送时间戳

    @Override
    public String toString() {
        return "MsgSystemTip{" +
                "cmd=" + cmd +
                ", room_id=" + room_id +
                ", nick_name='" + nick_name + '\'' +
                ", msg='" + msg + '\'' +
                ", type=" + type +
                ", send_time='" + send_time + '\'' +
                '}';
    }
}
