package com.montnets.liveroom.im.bean;

import java.io.Serializable;

/**
 * 聊天室信息
 */
public class IMRoom implements Serializable {

    /**
     * msg : 请求成功！
     * code : 200
     * data : {"room_online_users":0,"out_roomID":12346,"join_notice":"成功获取到聊天室地址及房间号，准备连接聊天室。","out_room_ServerAddress":"http://www.pihuilong.com:8888"}
     */
    private String msg;
    private String code;
    private DataEntity data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public class DataEntity {
        /**
         * room_online_users : 0
         * out_roomID : 12346
         * join_notice : 成功获取到聊天室地址及房间号，准备连接聊天室。
         * out_room_ServerAddress : http://www.pihuilong.com:8888
         */
        private int room_online_users;          //该房间在线用户数
        private int out_roomID;                 //房间ID
        private String join_notice;             //加入提示语
        private String out_room_ServerAddress;  //聊天室地址

        public void setRoom_online_users(int room_online_users) {
            this.room_online_users = room_online_users;
        }

        public void setOut_roomID(int out_roomID) {
            this.out_roomID = out_roomID;
        }

        public void setJoin_notice(String join_notice) {
            this.join_notice = join_notice;
        }

        public void setOut_room_ServerAddress(String out_room_ServerAddress) {
            this.out_room_ServerAddress = out_room_ServerAddress;
        }

        public int getRoom_online_users() {
            return room_online_users;
        }

        public int getOut_roomID() {
            return out_roomID;
        }

        public String getJoin_notice() {
            return join_notice;
        }

        public String getOut_room_ServerAddress() {
            return out_room_ServerAddress;
        }
    }
}
