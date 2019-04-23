package com.montnets.liveroom.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.montnets.liveroom.R;
import com.montnets.liveroom.VideoConstants;
import com.montnets.liveroom.adapter.IMAdapter;
import com.montnets.liveroom.im.IMException;
import com.montnets.liveroom.im.IMManager;
import com.montnets.liveroom.im.OnHandleMsgListener;
import com.montnets.liveroom.im.OnIMStateListener;
import com.montnets.liveroom.im.bean.IMGift;
import com.montnets.liveroom.im.bean.IMMessage;
import com.montnets.liveroom.im.bean.MsgCustomize;
import com.montnets.liveroom.im.bean.MsgGift;
import com.montnets.liveroom.im.bean.MsgJoinRoom;
import com.montnets.liveroom.im.bean.MsgMessage;
import com.montnets.liveroom.im.bean.MsgNotice;
import com.montnets.liveroom.im.bean.MsgQuestion;
import com.montnets.liveroom.im.bean.MsgSilence;
import com.montnets.liveroom.im.bean.MsgStar;
import com.montnets.liveroom.im.bean.MsgSystemTip;
import com.montnets.liveroom.utils.InputMethodUtils;
import com.montnets.liveroom.view.DialogFactory;
import com.montnets.liveroom.view.gift.GiftManager;
import com.montnets.liveroom.view.gift.RoomContinueGiftView;
import com.montnets.liveroom.view.praise.HiPraiseAnimationView;
import com.montnets.liveroom.view.praise.HiPraiseWithCallback;
import com.montnets.liveroom.view.praise.OnDrawCallback;
import com.montnets.mwlive.base.CommonHandler;
import com.montnets.mwlive.socket.bean.IMUser;

import java.util.ArrayList;
import java.util.List;

/**
 * 视频观看详情页-聊天tab
 * Created by songlei on 2018/12/10.
 */
public class IMFragment extends Fragment implements CommonHandler.HandlerCallBack {
    private static final int HIDE_ENTER_VIEW = 1;

    private IMAdapter imAdapter;
    private EditText etMsg;
    private ImageView btSendMsg;
    private TextView tvSendStar;
    private ImageView btSendGift;
    private RelativeLayout rlRoot;
    private RecyclerView lvChat;
    private LinearLayout enterUserLl;
    private TextView enterUserTv;
    private HiPraiseAnimationView praiseView;

    private IMManager imManager;
    private GiftManager giftManager;
    private String videoID;
    private int type;

    private long lastEnterTime;     //上次进入聊天室时间
    private long delayTime = 1000;  //延时时间

    private CommonHandler mHandler = new CommonHandler(this);

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case HIDE_ENTER_VIEW:
                hideView();
                break;
        }
    }

    public static IMFragment getInstance(String videoID, int type) {
        IMFragment imFragment = new IMFragment();
        imFragment.videoID = videoID;
        imFragment.type = type;
        return imFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_im, container, false);
        etMsg = (EditText) rootView.findViewById(R.id.et_msg);
        btSendMsg = (ImageView) rootView.findViewById(R.id.bt_send_msg);
        tvSendStar = (TextView) rootView.findViewById(R.id.tv_send_star);
        btSendGift = (ImageView) rootView.findViewById(R.id.bt_send_gift);
        rlRoot = (RelativeLayout) rootView.findViewById(R.id.rl_im_root);
        enterUserLl = (LinearLayout) rootView.findViewById(R.id.enter_user_ll);
        enterUserTv = (TextView) rootView.findViewById(R.id.enter_user_tv);
        praiseView = (HiPraiseAnimationView) rootView.findViewById(R.id.praise_view);
        RoomContinueGiftView giftUpView = (RoomContinueGiftView) rootView.findViewById(R.id.continue_gift_up);
        RoomContinueGiftView giftDownView = (RoomContinueGiftView) rootView.findViewById(R.id.continue_gift_down);

        lvChat = (RecyclerView) rootView.findViewById(R.id.lv_chat);
        lvChat.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((DefaultItemAnimator) lvChat.getItemAnimator()).setSupportsChangeAnimations(false);
        List<String> chatList = new ArrayList<>();
        imAdapter = new IMAdapter(getActivity(), chatList);
        lvChat.setAdapter(imAdapter);
        rollToEnd();

        giftManager = new GiftManager(giftUpView, giftDownView);

        initLiveRoom();
        initListener();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        praiseView.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        IMManager.getInstance().unregister(onHandleMsgListener);
    }

    private void initLiveRoom() {
        IMUser imUser = new IMUser("1", "游客", "");
        imManager = IMManager.getInstance();
        if (!TextUtils.isEmpty(videoID)) {
            String videoType = "";
            if (type == VideoConstants.TYPE_LIVE) {
                videoType = "0";
            } else if (type == VideoConstants.TYPE_LIVE_RECORD) {
                videoType = "1";
            } else if (type == VideoConstants.TYPE_VIDEO) {
                videoType = "2";
            }
            imManager.login(videoID, videoType, imUser, onIMStateListener);
            imManager.registerOnHandleMsgListener(onHandleMsgListener);
        } else {
            Toast.makeText(getActivity(), "未获取到视频ID", Toast.LENGTH_LONG).show();
        }
    }

    private void initListener() {
        btSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = etMsg.getText().toString().trim();
                if (!TextUtils.isEmpty(msg)) {
                    if (imManager == null) {
                        return;
                    }
                    imManager.sendMessage(new IMMessage(msg));
                    etMsg.setText("");
                    InputMethodUtils.hideSoftwareKeyboard(getActivity(), etMsg);
                } else {
                    Toast.makeText(getActivity(), "输入内容不能为空！", Toast.LENGTH_LONG).show();
                }
            }
        });

        tvSendStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                praiseView.addPraise(new HiPraiseWithCallback(getHeartBitmap(), new OnDrawCallback() {
                    @Override
                    public void onFinish() {
                        if (imManager == null) {
                            return;
                        }
                        //imManager.sendStar();
                    }
                }));
            }
        });

        btSendGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imManager == null) {
                    return;
                }
                imManager.sendGift(new IMGift("糖", 1, "11", ""));
            }
        });

        rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodUtils.hideSoftwareKeyboard(getActivity(), etMsg);
            }
        });
    }

    private Bitmap getHeartBitmap(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_like);
        return bitmap;
    }

    private OnHandleMsgListener onHandleMsgListener = new OnHandleMsgListener() {
        @Override
        public void onReceiveEnter(MsgJoinRoom enterRoom) {
            imAdapter.addItem(enterRoom.nickName + " 进入了直播间");
            dealUserEnterAction(enterRoom);
        }

        @Override
        public void onReceivedMessage(MsgMessage message) {
            String msg = message.data.msgbody;
            imAdapter.addItem(message.nickName + " 说：" + msg);
            rollToEnd();
        }

        @Override
        public void onReceivedGift(MsgGift gift) {
            String giftName = gift.data.giftName;
            int giftNum = gift.data.giftNum;
            imAdapter.addItem(gift.nickName + " 送了：" + giftNum + "个" + giftName);
            rollToEnd();
            giftManager.showContinueGift(gift);
        }

        @Override
        public void onReceivedNotice(MsgNotice notice) {
            imAdapter.addItem("系统公告：" + notice.data.msgbody);
        }

        @Override
        public void onReceivedStar(MsgStar star) {
            imAdapter.addItem(star.nickName + " 点赞");
            rollToEnd();
        }

        @Override
        public void onReceivedCustomMsg(MsgCustomize customize) {
            imAdapter.addItem(customize.nickName + " 发送自定义消息：" + customize.data.toString());
        }

        @Override
        public void onReceivedSysTip(MsgSystemTip systemTip) {
            imAdapter.addItem("系统提示：" + systemTip.msg);
        }

        @Override
        public void onReceivedSilence(MsgSilence silence) {

        }

        @Override
        public void onReceivedQuestion(MsgQuestion question) {
            if (question.data.status == 1) {
                String url = question.data.url;
                Dialog dialog = DialogFactory.createDialog(getActivity(), url);
                dialog.show();
            }
        }
    };

    OnIMStateListener onIMStateListener = new OnIMStateListener() {
        @Override
        public void onSuccess() {
            imAdapter.addItem("聊天室登录成功");
        }

        @Override
        public void onError(IMException e) {
            switch (e.code) {
                case IMException.ERROR_CONNECT:
                    imAdapter.addItem("聊天室登录失败... 重连中.....");
                    break;
                case IMException.ERROR_CONNECT_TIME_OUT:
                    imAdapter.addItem("聊天室连接超时... 请检查网络....");
                    break;
                case IMException.ERROR_REQUEST_FAIL:
                    imAdapter.addItem("聊天室登录失败... 请检查网络....");
                    break;
                case IMException.ERROR_RECEIVE_MSG_FAIL:
                    imAdapter.addItem("接收消息失败........");
                    break;
                case IMException.ERROR_PARSE_MSG_FAIL:
                    imAdapter.addItem("消息解析异常........");
                    break;
            }
        }
    };

    private void rollToEnd(){
        if (lvChat.getAdapter().getItemCount() > 0) {
            lvChat.smoothScrollToPosition(lvChat.getAdapter().getItemCount());
        }
    }

    public List<View> getExcludeView(){
        List<View> views = new ArrayList<>();
        views.add(etMsg);
        views.add(btSendMsg);
        return views;
    }

    public void hideIMKeyboard(){
        InputMethodUtils.hideSoftwareKeyboard(getActivity(), etMsg);
    }

    private void dealUserEnterAction(MsgJoinRoom msg) {
        long currentTime = System.currentTimeMillis();
        long deltaTime = currentTime - lastEnterTime;
        mHandler.removeMessages(HIDE_ENTER_VIEW);
        if (deltaTime < 1500) {
            //连续进入
            enterUserLl.setVisibility(View.VISIBLE);
            enterUserTv.setText(msg.nickName);
            mHandler.sendEmptyMessageDelayed(HIDE_ENTER_VIEW, delayTime);
        } else {
            //单个进入
            enterUserLl.setVisibility(View.VISIBLE);
            enterUserTv.setText(msg.nickName);
            showSingleEnter();
        }
        lastEnterTime = currentTime;
    }

    private void showSingleEnter() {
        ObjectAnimator enterAnim = ObjectAnimator.ofFloat(enterUserLl, "translationX", -enterUserLl.getWidth(), 0);
        enterAnim.setDuration(1000);
        enterAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        enterAnim.start();
        enterAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mHandler.sendEmptyMessageDelayed(HIDE_ENTER_VIEW, delayTime);// delay时间应该随着变长
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void hideView(){
        ObjectAnimator hideAnim = ObjectAnimator.ofFloat(enterUserLl, "translationX", 0, -enterUserLl.getWidth());
        hideAnim.setDuration(1000);
        hideAnim.start();
        hideAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

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
        });
    }
}
