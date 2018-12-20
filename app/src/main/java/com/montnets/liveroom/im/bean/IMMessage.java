package com.montnets.liveroom.im.bean;

import java.io.Serializable;

public class IMMessage implements Serializable {
    public String msgbody;

    public IMMessage(String msgbody) {
        this.msgbody = msgbody;
    }
}
