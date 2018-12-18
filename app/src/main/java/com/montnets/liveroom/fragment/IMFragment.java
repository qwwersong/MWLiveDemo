package com.montnets.liveroom.fragment;

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
import com.montnets.liveroom.utils.InputMethodUtils;
import com.montnets.mwlive.LiveRoom;
import com.montnets.mwlive.socket.IMException;
import com.montnets.mwlive.socket.OnReceivedMsgListener;
import com.montnets.mwlive.socket.OnSocketStateListener;
import com.montnets.mwlive.socket.bean.IMAnswer;
import com.montnets.mwlive.socket.bean.IMGift;
import com.montnets.mwlive.socket.bean.IMMessage;
import com.montnets.mwlive.socket.bean.IMUser;
import com.montnets.mwlive.socket.bean.MsgAnswerResult;
import com.montnets.mwlive.socket.bean.MsgCustomize;
import com.montnets.mwlive.socket.bean.MsgGift;
import com.montnets.mwlive.socket.bean.MsgMessage;
import com.montnets.mwlive.socket.bean.MsgNotice;
import com.montnets.mwlive.socket.bean.MsgQuestionnaire;
import com.montnets.mwlive.socket.bean.MsgSilence;
import com.montnets.mwlive.socket.bean.MsgStar;
import com.montnets.mwlive.socket.bean.MsgSystemTip;

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
    private Button btQuestionResult;
    private Button btSendAnswer;
    private RelativeLayout rlRoot;
    private RecyclerView lvChat;
    private LiveRoom liveRoom;
    private String videoID;
    private MsgQuestionnaire questionnaire;

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
        btQuestionResult = (Button) rootView.findViewById(R.id.bt_question_result);
        btSendAnswer = (Button) rootView.findViewById(R.id.bt_send_answer);
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
        liveRoom = LiveRoom.getInstance();
        if (!TextUtils.isEmpty(videoID)) {
            liveRoom.loginRoom(videoID, imUser, onSocketStateListener, onReceivedMsgListener);
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
                    liveRoom.sendMessage(new IMMessage(msg));
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
                liveRoom.sendStar();
            }
        });

        btSendGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                liveRoom.sendGift(new IMGift("糖", 1, "11", ""));
            }
        });

        btQuestionResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionnaire == null) {
                    Toast.makeText(getActivity(), "没有收到问卷", Toast.LENGTH_SHORT).show();
                    return;
                }
                liveRoom.sendQuestionResult(questionnaire.data.id);
            }
        });

        btSendAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionnaire == null) {
                    Toast.makeText(getActivity(), "没有收到问卷", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<IMAnswer.Answer> answers = new ArrayList<>();
                IMAnswer imAnswer = new IMAnswer();
                imAnswer.questionnaireId = questionnaire.data.id;
                imAnswer.questionnaireTitle = questionnaire.data.questionnaire.title;
                imAnswer.type = questionnaire.data.questionnaire.type;
                imAnswer.userName = "游客";
                imAnswer.userId = "";
                imAnswer.answer = answers;

                List<MsgQuestionnaire.DataBean.Questionnaire.Topic> topics = questionnaire.data.questionnaire.topic;
                for (MsgQuestionnaire.DataBean.Questionnaire.Topic topic : topics) {
                    IMAnswer.Answer answer = new IMAnswer.Answer();
                    answer.topicId = topic._id;
                    answer.number = topic.number;
                    answer.question = topic.question;

                    List<String> options = new ArrayList<>();
                    List<String> optionIds = new ArrayList<>();
                    for (MsgQuestionnaire.DataBean.Questionnaire.Topic.Option contents : topic.options) {
                        options.add(contents.content);
                        optionIds.add(contents._id);
                    }

                    if (questionnaire.data.questionnaire.type.equals("single")) {
                        answer.selectId = topic.options.get(0)._id;
                        answer.selectContent = topic.options.get(0).content;
                    } else if (questionnaire.data.questionnaire.type.equals("multiple")) {
                        answer.selectMultipleId = optionIds;
                        answer.selectMultipleContent = options;
                    }
                    answer.optionSort = options;
                    answers.add(answer);
                }

                liveRoom.sendAnswer(imAnswer);
            }
        });

        rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodUtils.hideSoftwareKeyboard(getActivity(), etMsg);
            }
        });
    }

    OnReceivedMsgListener onReceivedMsgListener = new OnReceivedMsgListener() {
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
        public void onReceivedQuestion(MsgQuestionnaire msgQuestionnaire) {
            questionnaire = msgQuestionnaire;
        }

        @Override
        public void onReceivedAnswerResult(MsgAnswerResult answerResult) {

        }

    };

    OnSocketStateListener onSocketStateListener = new OnSocketStateListener() {
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
