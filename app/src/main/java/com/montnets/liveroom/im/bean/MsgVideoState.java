package com.montnets.liveroom.im.bean;

import java.io.Serializable;

/**
 * 消息：直播状态通知
 */
public class MsgVideoState implements Serializable {
    public int cmd;
    public int room_id;
    public DataBean data;

    public static class DataBean implements Serializable{
        public String liveID;           //直播ID
        public int live_status;         //直播主流状态    0-直播预告或视频未审核 1-直播中或视频上线 2-直播结束或视频下线 3-直播异常中断或视频审核不通过 4-直播过期
        public int slave_status;        //直播从流状态

        @Override
        public String toString() {
            return "DataBean{" +
                    "liveID='" + liveID + '\'' +
                    ", live_status=" + live_status +
                    ", slave_status=" + slave_status +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MsgVideoState{" +
                "cmd=" + cmd +
                ", room_id='" + room_id + '\'' +
                ", data=" + data +
                '}';
    }
}
