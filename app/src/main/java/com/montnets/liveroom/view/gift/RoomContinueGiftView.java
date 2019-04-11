package com.montnets.liveroom.view.gift;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.montnets.liveroom.R;
import com.montnets.liveroom.im.bean.MsgGift;
import com.montnets.liveroom.utils.GlideUtils;
import com.montnets.mwlive.base.LogUtil;
import com.nineoldandroids.animation.Animator;

import java.util.LinkedList;
import java.util.List;

public class RoomContinueGiftView extends RelativeLayout {
    private static final int CONTINUE_GIFT_DISMISS = 0;
    private static final int CONTINUE_GIFT_NUM_ADD = 1;
    private static final int CONTINUE_GIFT_NEW_COMING = 2;
    private static final int CONTINUE_GIFT_IMG_ENTER = 3;
    private TextView tv_sender;
    private TextView tv_gift_name;
    private ImageView iv_sender_head;
    private ImageView img_gift_icon;
    private MagicTextView txt_times;
    private View view;
    private Context context;

    private List<MsgGift> gifts = new LinkedList<MsgGift>();
    private String lastSenderID;
    private String lastGiftID;
    private int curContinueNum = 0;
    private boolean isPreDismiss = false;
    private boolean isDismissing = false;
    private OnGiftAnimListener listener;

    public RoomContinueGiftView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        view = View.inflate(context, R.layout.room_continue_gift, this);
        tv_sender = (TextView) view.findViewById(R.id.tv_sender);
        tv_gift_name = (TextView) view.findViewById(R.id.tv_gift_name);
        iv_sender_head = (ImageView) view.findViewById(R.id.iv_sender_head);
        img_gift_icon = (ImageView) view.findViewById(R.id.img_gift_icon);
        txt_times = (MagicTextView) view.findViewById(R.id.txt_times);
    }

    private boolean isDownView = true;

    public void setOnGiftAnimListener(boolean isDownView, OnGiftAnimListener listener) {
        this.isDownView = isDownView;
        this.listener = listener;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CONTINUE_GIFT_DISMISS:
                    YoYo.with(Techniques.FadeOutUp).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            LogUtil.d("RoomContinueGiftView", "CONTINUE_GIFT_DISMISS  onAnimationStart");
                            isDismissing = true;
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isPreDismiss = false;
                            isPreNewGiftShowing = false;
                            RoomContinueGiftView.this.setVisibility(INVISIBLE);
                            img_gift_icon.setVisibility(INVISIBLE);
                            if (gifts.size() > 0) {
                                showNewGiftComing(gifts.get(0));
                            } else {
                                if (listener != null) {
                                    listener.onAnimEnd(isDownView);
                                }
                            }
                            isDismissing = false;
                            LogUtil.d("RoomContinueGiftView", "CONTINUE_GIFT_DISMISS  onAnimationEn  ");
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    }).duration(500).playOn(RoomContinueGiftView.this);
                    break;

                case CONTINUE_GIFT_NUM_ADD:
                    YoYo.with(new BounceInBigAnimator())
                            .withListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    LogUtil.w("RoomContinueGiftView", "onAnimationStart:curContinueNum:" +
                                            curContinueNum);
                                    if (curContinueNum >= 1) { //连续第二次及以上执行
                                        if (gifts.size() > 0) {
                                            gifts.remove(0);
                                        }
                                    }
                                    curContinueNum++;
                                    txt_times.setText("x" + curContinueNum);
                                    txt_times.setVisibility(VISIBLE);
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    if (gifts.size() > 0) {
//                                        curContinueNum++;
                                        if (lastSenderID.equals(gifts.get(0).user_id) && lastGiftID.equals(gifts.get(0).data.giftID)) {
                                            mHandler.removeMessages(CONTINUE_GIFT_DISMISS);
                                            mHandler.sendEmptyMessage(CONTINUE_GIFT_NUM_ADD);
                                        } else {
                                            mHandler.sendEmptyMessageDelayed(CONTINUE_GIFT_DISMISS, 1500);
                                        }
                                    } else {
                                        isPreDismiss = true;
                                        mHandler.sendEmptyMessageDelayed(CONTINUE_GIFT_DISMISS, 1500);
                                    }
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {
                                }
                            })
                            .duration(500)
                            .playOn(txt_times);

                    break;
                case CONTINUE_GIFT_NEW_COMING:
                    mHandler.sendEmptyMessageDelayed(CONTINUE_GIFT_NUM_ADD, 500);
                    mHandler.sendEmptyMessageDelayed(CONTINUE_GIFT_IMG_ENTER, 550);
                    YoYo.with(Techniques.SlideInLeft).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            if (gifts.size() > 0) {
                                gifts.remove(0);
                            }
                            isPreDismiss = false;
                            RoomContinueGiftView.this.setVisibility(VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).duration(500).playOn(RoomContinueGiftView.this);
                    break;
                case CONTINUE_GIFT_IMG_ENTER:
                    YoYo.with(Techniques.SlideInLeft).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            img_gift_icon.setVisibility(VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    }).duration(300).playOn(img_gift_icon);
                    break;
            }
        }
    };


    public void addSingGift(MsgGift msg) {
        gifts.add(msg);
    }

    private boolean isPreNewGiftShowing = false;

    public void showContinueGift(MsgGift msg) {
        gifts.add(msg);
        if (isPreNewGiftShowing || VISIBLE == getVisibility()) {  //正在显示
            LogUtil.d("RoomContinueGiftView", "正在显示");
            if (lastSenderID.equals(msg.user_id) && lastGiftID.equals(gifts.get(0).data.giftID)) {  //与上次显示的礼物一样
                LogUtil.d("RoomContinueGiftView", "与上次显示的礼物一样");
                if (isPreDismiss) {  //正在取消礼物显示 取消动画已开始
                    LogUtil.d("RoomContinueGiftView", "正在取消礼物显示 取消动画已开始");
                    isPreDismiss = false;
                    mHandler.removeMessages(CONTINUE_GIFT_DISMISS);
                    mHandler.sendEmptyMessage(CONTINUE_GIFT_NUM_ADD);
                } else { //正在显示礼物动画 或者正处于取消动画之前的延时阶段
                    LogUtil.d("RoomContinueGiftView", "或者正处于取消动画之前的延时阶段");
                }
            } else {  //与上次显示的礼物不一样

                LogUtil.w("RoomContinueGiftView", "与上次显示的礼物不一样.");
            }
        } else { //没有显示
            isPreNewGiftShowing = true;
            LogUtil.w("RoomContinueGiftView", "没有显示.");
            showNewGiftComing(msg);
        }
    }

    private void showNewGiftComing(MsgGift msg) {
        curContinueNum = 0;
        GlideUtils.displayHeadImg(context, msg.imgUrl, iv_sender_head);
        GlideUtils.displayGiftImg(context, msg.data.giftImg, img_gift_icon);
        tv_sender.setText(msg.nickName + "");
        tv_gift_name.setText(String.format("送一个%1$s", msg.data.giftName));
        lastSenderID = msg.user_id;
        lastGiftID = msg.data.giftID;
        txt_times.setVisibility(GONE);
        mHandler.sendEmptyMessage(CONTINUE_GIFT_NEW_COMING);
    }

    public int getLeftGiftNum() {
        return gifts.size();
    }

    public boolean isShowing() {
        return getVisibility() == VISIBLE;
    }

    public boolean isDismissing() {
        return isDismissing;
    }

    public void destroyView() {
        mHandler.removeCallbacksAndMessages(null);
        view = null;
    }
}
