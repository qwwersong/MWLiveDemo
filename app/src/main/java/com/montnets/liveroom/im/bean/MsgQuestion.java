package com.montnets.liveroom.im.bean;

import java.io.Serializable;

/**
 * Created by songlei on 2018/12/26.
 */
public class MsgQuestion implements Serializable {
    public int cmd;
    public String msg;
    public DataBean data;

    public static class DataBean implements Serializable{
        public String id;
        public int status;  //1-发送成功 0-发送失败
        public String url;  //问卷URL

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", status=" + status +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MsgQuestion{" +
                "cmd=" + cmd +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
