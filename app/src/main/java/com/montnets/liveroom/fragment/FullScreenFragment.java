package com.montnets.liveroom.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.montnets.liveroom.R;
import com.montnets.liveroom.activity.VideoActivity;
import com.montnets.liveroom.im.IMManager;
import com.montnets.liveroom.im.OnHandleMsgListener;
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
import com.montnets.mwlive.base.ScreenUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * 全屏覆盖fragment
 * Created by songlei on 2018/12/10.
 */
public class FullScreenFragment extends Fragment {
    private DanmakuView danmakuView;
    private ImageView ivShowInput;
    private RelativeLayout rlInputLayout;
    private EditText etInput;
    private TextView tvSend;

    private boolean isViewCreated;
    private DanmakuContext mDanmuContext;
    private BaseDanmakuParser mParser;
    private VideoActivity videoActivity;

    public static FullScreenFragment getInstance() {
        return new FullScreenFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        videoActivity = (VideoActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_full_screen, container, false);
        danmakuView = (DanmakuView) rootView.findViewById(R.id.danmu_view);
        ivShowInput = (ImageView) rootView.findViewById(R.id.iv_show_input);
        rlInputLayout = (RelativeLayout) rootView.findViewById(R.id.rl_input_view);
        etInput = (EditText) rootView.findViewById(R.id.et_input_msg);
        tvSend = (TextView) rootView.findViewById(R.id.tv_send_msg);
        isViewCreated = true;
        initLiveRoom();
        initDanMu();
        initListener();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (danmakuView != null && danmakuView.isPrepared() && danmakuView.isPaused()) {
            danmakuView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (danmakuView != null && danmakuView.isPrepared()) {
            danmakuView.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        IMManager.getInstance().unregister(onHandleMsgListener);
        if (danmakuView != null) {
            danmakuView.release();
            danmakuView = null;
        }
    }

    private void initListener() {
        ivShowInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rlInputLayout.getVisibility() == View.VISIBLE) {
                    etInput.setFocusable(false);
                    etInput.setFocusableInTouchMode(false);
                    hideFullKeyboard();
                } else {
                    rlInputLayout.setVisibility(View.VISIBLE);
                    etInput.setFocusable(true);
                    etInput.setFocusableInTouchMode(true);
                    etInput.requestFocus();
                    etInput.requestFocusFromTouch();
                    InputMethodUtils.showSoftwareKeyboard(getActivity(), etInput);
                }
            }
        });
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msgContent = etInput.getText().toString();
                if (TextUtils.isEmpty(msgContent)) {
                    Toast.makeText(getActivity(), "输入内容不能为空！", Toast.LENGTH_LONG).show();
                } else {
                    IMManager.getInstance().sendMessage(new IMMessage(msgContent));
                    etInput.setText("");
                    hideFullKeyboard();
                }
            }
        });
    }

    private void initLiveRoom() {
        IMManager.getInstance().registerOnHandleMsgListener(onHandleMsgListener);
    }

    public void hideFullKeyboard() {
        InputMethodUtils.hideSoftwareKeyboard(getActivity(), etInput);
        rlInputLayout.setVisibility(View.GONE);
        videoActivity.showMediaController(true);
    }

    public void showViews(boolean show) {
        if (!isViewCreated) {
            return;
        }
        if (show) {
            ivShowInput.setVisibility(View.VISIBLE);
        } else {
            ivShowInput.setVisibility(View.GONE);
        }
    }

    OnHandleMsgListener onHandleMsgListener = new OnHandleMsgListener() {
        @Override
        public void onReceivedMessage(MsgMessage message) {
            String msg = message.data.msgbody;
            onReceivedDanmu(msg);
        }

        @Override
        public void onReceivedGift(MsgGift gift) {

        }

        @Override
        public void onReceivedNotice(MsgNotice notice) {

        }

        @Override
        public void onReceivedStar(MsgStar star) {

        }

        @Override
        public void onReceivedCustomMsg(MsgCustomize customize) {

        }

        @Override
        public void onReceivedSysTip(MsgSystemTip systemTip) {

        }

        @Override
        public void onReceivedSilence(MsgSilence silence) {

        }

        @Override
        public void onReceivedQuestion(MsgQuestion question) {

        }
    };

    private void onReceivedDanmu(String danMuStr) {
        BaseDanmaku dan = mDanmuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (dan != null && danmakuView != null) {
            dan.text = danMuStr;
            dan.padding = 5;
            dan.priority = 0;  // 可能会被各种过滤器过滤并隐藏显示
            dan.isLive = false;
            dan.setTime(danmakuView.getCurrentTime() + 1000);
            dan.textSize = 15f * mParser.getDisplayer().getDensity();
            dan.textColor = Color.WHITE;
            dan.textShadowColor = Color.BLACK;
            danmakuView.addDanmaku(dan);
        }
    }

    private void initDanMu() {
        HashMap<Integer, Integer> maxLinesPair = new HashMap<>();     // 设置最大显示行数
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5); // 滚动弹幕最大显示5行
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<>(); // 设置是否禁止重叠
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);
        mDanmuContext = DanmakuContext.create();
        mDanmuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 1)
                .setDuplicateMergingEnabled(false)
                .setScrollSpeedFactor(1.0f)
                .setScaleTextSize(1.2f)
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair).setDanmakuMargin(ScreenUtil.dip2px(getActivity(), 5));
        if (danmakuView != null) {
            mParser = createParser();
            danmakuView.start();
            danmakuView.prepare(mParser, mDanmuContext);
            danmakuView.showFPS(false);
            danmakuView.enableDanmakuDrawingCache(true);
        }
    }

    private BaseDanmakuParser createParser() {
        return new BaseDanmakuParser() {
            @Override
            protected Danmakus parse() {
                return new Danmakus();
            }
        };
    }

    public List<View> getExcludeView(){
        List<View> views = new ArrayList<>();
        views.add(etInput);
        views.add(tvSend);
        return views;
    }
}
