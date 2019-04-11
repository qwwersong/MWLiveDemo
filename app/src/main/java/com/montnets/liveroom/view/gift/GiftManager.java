package com.montnets.liveroom.view.gift;

import android.support.v4.util.ArrayMap;

import com.montnets.liveroom.im.bean.MsgGift;
import com.montnets.mwlive.base.LogUtil;

import java.util.LinkedList;
import java.util.List;

public class GiftManager {
    private String lastUpUserId = "";
    private String lastUpContinueGiftId;
    private String lastDownUserId = "";
    private String lastDownContinueGiftId;
    private List<String> giftKeys = new LinkedList<>();
    private ArrayMap<String, TBSContinueGiftsBean> giftsMap = new ArrayMap<>();
    private RoomContinueGiftView giftUpView;
    private RoomContinueGiftView giftDownView;

    public GiftManager(RoomContinueGiftView giftUpView, RoomContinueGiftView giftDownView){
        this.giftUpView = giftUpView;
        this.giftDownView = giftDownView;
        giftUpView.setOnGiftAnimListener(false, mOnContinueGiftAnimListener);
        giftDownView.setOnGiftAnimListener(true, mOnContinueGiftAnimListener);
    }

    public void showContinueGift(MsgGift gift) {
        if (!giftUpView.isShowing() && !giftDownView.isShowing()) { //上面和下面的都没显示
            giftDownView.showContinueGift(gift);
            lastDownUserId = gift.user_id;
            lastDownContinueGiftId = gift.data.giftID;
        } else if (!giftUpView.isShowing() && giftDownView.isShowing()) {  //上面没显示 下面的显示
            if (lastDownUserId.equals(gift.user_id) && lastDownContinueGiftId.equals(gift.data.giftID)) { //与上次下面显示的礼物相同
                if (giftDownView.isDismissing()) { //下面正在消失
                    showUpGiftView(gift);
                } else {
                    showDownGiftView(gift);
                }
            } else { //与上次下面显示的礼物不同
                showUpGiftView(gift);
            }
        } else if (giftUpView.isShowing() && !giftDownView.isShowing()) { // 上面的显示 下面没显示
            if (lastUpUserId.equals(gift.user_id) && lastUpContinueGiftId.equals(gift.data.giftID)) { //与上次上面显示的礼物相同
                if (giftUpView.isDismissing()) {
                    showDownGiftView(gift);
                } else {
                    showUpGiftView(gift);
                }
            } else { //与上次上面显示的礼物不同
                showDownGiftView(gift);
            }
        } else if (giftUpView.isShowing() && giftDownView.isShowing()) { //上面和下面的都显示
            if (lastDownUserId.equals(gift.user_id) && lastDownContinueGiftId.equals(gift.data.giftID)) { //与上次下面显示的礼物相同
                if (giftDownView.isDismissing()) { //下面正在消失
                    LogUtil.i("Socket", "giftDownView.isDismissing()  下面正在消失");
                    showUpGiftView(gift);
                } else {
                    showDownGiftView(gift);
                }
            } else if (lastUpUserId.equals(gift.user_id) && lastUpContinueGiftId.equals(gift.data.giftID)) { //与上次上面显示的礼物相同
                if (giftUpView.isDismissing()) {
                    LogUtil.i("Socket", "giftUpView.isDismissing()  上面正在消失");
                    showDownGiftView(gift);
                } else {
                    showUpGiftView(gift);
                }
            } else {  //与上次上面和下面显示的礼物都不同
                onThirdKindGiftReceived(gift);
            }
        }
    }

    private void onThirdKindGiftReceived(MsgGift gift) {
        String tempGiftKey = gift.user_id + gift.data.giftID;
        TBSContinueGiftsBean tempTBSCGiftBean;
        if (giftsMap.containsKey(tempGiftKey)) {   //待显示列表中有此种礼物
            tempTBSCGiftBean = giftsMap.get(tempGiftKey);
            tempTBSCGiftBean.unShownCount = tempTBSCGiftBean.unShownCount + 1;
        } else {     //待显示列表中没有此种礼物
            tempTBSCGiftBean = new TBSContinueGiftsBean(1, gift);
            giftKeys.add(tempGiftKey);
        }
        giftsMap.put(tempGiftKey, tempTBSCGiftBean);
    }

    private void showTBSGifts() {
        TBSContinueGiftsBean tmpTBSCGift = giftsMap.get(giftKeys.get(0));
        if (tmpTBSCGift.isShowing && giftsMap.size() > 1) {
            tmpTBSCGift = giftsMap.get(giftKeys.get(1));
        }
        while (tmpTBSCGift.unShownCount > 0) {
            tmpTBSCGift.isShowing = true;
            tmpTBSCGift.unShownCount--;
            showContinueGift(tmpTBSCGift.gift);
            if (tmpTBSCGift.unShownCount == 0) {
                giftsMap.remove(giftKeys.get(0));
                giftKeys.remove(0);
            }
        }
    }

    private void showDownGiftView(MsgGift gift) {
        lastDownUserId = gift.user_id;
        lastDownContinueGiftId = gift.data.giftID;
        giftDownView.showContinueGift(gift);
    }

    private void showUpGiftView(MsgGift gift) {
        lastUpUserId = gift.user_id;
        lastUpContinueGiftId = gift.data.giftID;
        giftUpView.showContinueGift(gift);
    }

    private OnGiftAnimListener mOnContinueGiftAnimListener = new OnGiftAnimListener() {
        @Override
        public void onAnimEnd(boolean isDownView) {
            if (!giftsMap.isEmpty()) { //待显示礼物不为空
                showTBSGifts();
            }
        }

        @Override
        public void onAnimStart(int giftID) {

        }
    };
}
