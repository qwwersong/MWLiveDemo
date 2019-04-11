package com.montnets.liveroom.view.gift;

import com.montnets.liveroom.im.bean.MsgGift;

import java.io.Serializable;

public class TBSContinueGiftsBean implements Serializable {
    public  int unShownCount=0;
    public MsgGift gift;
    public   boolean isShowing=false;

    public TBSContinueGiftsBean(int unShownCount, MsgGift gift) {
        this.unShownCount = unShownCount;
        this.gift = gift;
    }

    public void setUnShownCount(int unShownCount) {
        this.unShownCount = unShownCount;
    }

    public void setGift(MsgGift gift) {
        this.gift = gift;
    }
}
