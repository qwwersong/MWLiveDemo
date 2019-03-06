package com.montnets.liveroom.im.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by songlei on 2019/03/06.
 */
public class MsgMulti implements Serializable {
    public int cmd;             //消息命令号 必填
    public String msg;          //消息标题 必填
    public AllMsg data;

    public class AllMsg implements Serializable {
        public List<Object> allsms;
    }
}
