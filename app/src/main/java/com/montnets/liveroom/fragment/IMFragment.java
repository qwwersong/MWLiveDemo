package com.montnets.liveroom.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.montnets.liveroom.R;
import com.montnets.liveroom.adapter.IMAdapter;
import com.montnets.liveroom.im.IMException;
import com.montnets.liveroom.im.IMManager;
import com.montnets.liveroom.im.OnHandleMsgListener;
import com.montnets.liveroom.im.OnIMStateListener;
import com.montnets.liveroom.im.bean.IMGift;
import com.montnets.liveroom.im.bean.IMMessage;
import com.montnets.liveroom.im.bean.MsgCustomize;
import com.montnets.liveroom.im.bean.MsgGift;
import com.montnets.liveroom.im.bean.MsgMessage;
import com.montnets.liveroom.im.bean.MsgNotice;
import com.montnets.liveroom.im.bean.MsgQuestion;
import com.montnets.liveroom.im.bean.MsgSilence;
import com.montnets.liveroom.im.bean.MsgStar;
import com.montnets.liveroom.im.bean.MsgSystemTip;
import com.montnets.liveroom.utils.InputMethodUtils;
import com.montnets.liveroom.view.DialogFactory;
import com.montnets.mwlive.socket.bean.IMUser;

import java.util.ArrayList;
import java.util.List;

/**
 * 视频观看详情页-聊天tab
 * Created by songlei on 2018/12/10.
 */
public class IMFragment extends Fragment {
    private IMAdapter imAdapter;
    private EditText etMsg;
    private Button btSendMsg;
    private Button btSendStar;
    private Button btSendGift;
    private RelativeLayout rlRoot;
    private RecyclerView lvChat;
    private IMManager imManager;
    private String videoID;

    public static IMFragment getInstance(String videoID) {
        IMFragment imFragment = new IMFragment();
        imFragment.videoID = videoID;
        return imFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_im, container, false);
        etMsg = (EditText) rootView.findViewById(R.id.et_msg);
        btSendMsg = (Button) rootView.findViewById(R.id.bt_send_msg);
        btSendStar = (Button) rootView.findViewById(R.id.bt_send_star);
        btSendGift = (Button) rootView.findViewById(R.id.bt_send_gift);
        rlRoot = (RelativeLayout) rootView.findViewById(R.id.rl_im_root);

        lvChat = (RecyclerView) rootView.findViewById(R.id.lv_chat);
        lvChat.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((DefaultItemAnimator) lvChat.getItemAnimator()).setSupportsChangeAnimations(false);
        List<String> chatList = new ArrayList<>();
        imAdapter = new IMAdapter(getActivity(), chatList);
        lvChat.setAdapter(imAdapter);
        rollToEnd();

        initLiveRoom();
        initListener();
        return rootView;
    }

    private void initLiveRoom() {
        IMUser imUser = new IMUser("", "游客", "");
        imManager = IMManager.getInstance();
        if (!TextUtils.isEmpty(videoID)) {
            imManager.login(videoID, imUser, onIMStateListener);
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
                    imManager.sendMessage(new IMMessage(msg));
                    etMsg.setText("");
                    InputMethodUtils.hideSoftwareKeyboard(getActivity(), etMsg);
                } else {
                    Toast.makeText(getActivity(), "输入内容不能为空！", Toast.LENGTH_LONG).show();
                }
            }
        });

        btSendStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imManager.sendStar();
            }
        });

        btSendGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    private OnHandleMsgListener onHandleMsgListener = new OnHandleMsgListener() {
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
}
