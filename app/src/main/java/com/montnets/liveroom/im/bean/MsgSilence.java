package com.montnets.liveroom.im.bean;

import java.io.Serializable;

/**
 * 消息：禁言信息
 */
public class MsgSilence implements Serializable {
    public int cmd;                 //消息命令号
    public String msg;              //消息标题
    public DataBean data;

    public static class DataBean implements Serializable{
        //============= 公用字段 =================
        public String user_id;          //用户ID（公用）
        public String nickName;         //用户昵称（公用）
        public String pic;              //用户头像（公用）
        //============= 获取禁言列表 ================
        public String time;             //禁言时间
        //============= 单个禁言/单个解禁 ================
        public String silence_user_id;  //禁言/解禁用户ID
        //============= 查询用户禁言状态 ==============
        public int silence;             //禁言状态  1 被禁言 0 没有被禁言
        //============= 清除单条聊天记录 ===============
        public String msg_id;           //消息id
        //============= 全体禁言请求 =================
        public int silence_all;         //是否开启全体禁言  1开启 0关闭
        public int status;              //成功状态   1-设置成功 0-设置失败

        @Override
        public String toString() {
            return "DataBean{" +
                    "user_id='" + user_id + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", pic='" + pic + '\'' +
                    ", time='" + time + '\'' +
                    ", silence_user_id='" + silence_user_id + '\'' +
                    ", silence=" + silence +
                    ", msg_id='" + msg_id + '\'' +
                    ", silence_all=" + silence_all +
                    ", status=" + status +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MsgSilence{" +
                "cmd=" + cmd +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
