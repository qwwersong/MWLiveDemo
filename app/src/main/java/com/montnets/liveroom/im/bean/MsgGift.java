package com.montnets.liveroom.im.bean;

import java.io.Serializable;

/**
 * 消息：礼物
 * Created by songlei on 2018/11/17.
 */
public class MsgGift implements Serializable {
    public int cmd;             //消息命令号 必填
    public String msg;          //消息标题 必填
    public String nickName;     //昵称 必填
    public String imgUrl;       //用户头像url 必填
    public String user_id;      //用户ID 必填
    public String msg_id;       //消息ID
    public long send_time;      //消息发送时间戳
    public String room_id;      //消息所属房间号
    public DataBean data;

    public static class DataBean implements Serializable{
        public String giftName;          //礼物名称
        public int giftNum;              //礼物数量
        public String giftID;            //礼物ID
        public String giftImg;           //礼物图片

        @Override
        public String toString() {
            return "DataBean{" +
                    "giftName='" + giftName + '\'' +
                    ", giftNum=" + giftNum +
                    ", giftID='" + giftID + '\'' +
                    ", giftImg='" + giftImg + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MsgGift{" +
                "cmd=" + cmd +
                ", msg='" + msg + '\'' +
                ", nickName='" + nickName + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", user_id='" + user_id + '\'' +
                ", msg_id='" + msg_id + '\'' +
                ", send_time=" + send_time +
                ", room_id=" + room_id +
                ", data=" + data +
                '}';
    }
}
