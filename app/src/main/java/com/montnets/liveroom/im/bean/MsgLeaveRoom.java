package com.montnets.liveroom.im.bean;

import java.io.Serializable;

/**
 * 消息：离开房间
 * Created by songlei on 2018/11/16.
 */
public class MsgLeaveRoom implements Serializable {
    public int cmd;             //消息命令号 必填
    public int roomID;          //房间号 必填
    public String nickName;     //昵称 必填
    public String user_id;      //用户ID 必填   游客传空字符
    public String imgUrl;       //用户头像url

    @Override
    public String toString() {
        return "MsgLeaveRoom{" +
                "cmd=" + cmd +
                ", roomID=" + roomID +
                ", nickName='" + nickName + '\'' +
                ", user_id='" + user_id + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
