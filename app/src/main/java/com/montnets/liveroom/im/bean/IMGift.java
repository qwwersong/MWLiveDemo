package com.montnets.liveroom.im.bean;

import java.io.Serializable;

/**
 * 发送的礼物信息
 */
public class IMGift implements Serializable {
    public String giftName;          //礼物名称
    public int giftNum;              //礼物数量
    public String giftID;            //礼物ID
    public String giftImg;           //礼物图片

    public IMGift(String giftName, int giftNum, String giftID, String giftImg) {
        this.giftName = giftName;
        this.giftNum = giftNum;
        this.giftID = giftID;
        this.giftImg = giftImg;
    }
}
