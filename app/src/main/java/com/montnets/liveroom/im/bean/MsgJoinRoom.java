package com.montnets.liveroom.im.bean;

import java.io.Serializable;

/**
 * 消息：加入房间
 * Created by songlei on 2018/11/16.
 */
public class MsgJoinRoom implements Serializable {
    public int cmd;             //消息命令号 必填
    public int roomID;          //房间号 必填
    public String nickName;     //昵称 必填
    public String user_id;      //用户ID 必填   游客传空字符
    public String imgUrl;       //用户头像url 必填
    public int sendMsg;         //是否发送聊天消息  1是 0否
    public int isViewer;        //是否浏览（新增）  1是 0否
    public DataBean data;

    public static class DataBean implements Serializable{
        public int room_online_users;   //房间在线人数
        public int silence;             //用户禁言状态    0-正常 1-被禁言
        public int silence_all;         //房间全体禁言状态  0-正常 1-全体禁言
        public int total_views;         //房间被浏览次数

        @Override
        public String toString() {
            return "DataBean{" +
                    "room_online_users=" + room_online_users +
                    ", silence=" + silence +
                    ", silence_all=" + silence_all +
                    ", total_views=" + total_views +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MsgJoinRoom{" +
                "cmd=" + cmd +
                ", roomID=" + roomID +
                ", nickName='" + nickName + '\'' +
                ", user_id='" + user_id + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", sendMsg=" + sendMsg +
                ", isViewer=" + isViewer +
                ", data=" + data +
                '}';
    }
}
