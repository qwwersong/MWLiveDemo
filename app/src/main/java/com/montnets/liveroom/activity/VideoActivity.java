package com.montnets.liveroom.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.SlidingTabLayout;
import com.githang.statusbar.StatusBarCompat;
import com.montnets.liveroom.R;
import com.montnets.liveroom.RequestConstants;
import com.montnets.liveroom.VideoConstants;
import com.montnets.liveroom.adapter.DetailFragmentAdapter;
import com.montnets.liveroom.adapter.IMAdapter;
import com.montnets.liveroom.bean.LiveDetail;
import com.montnets.liveroom.bean.VideoDetail;
import com.montnets.liveroom.fragment.FullScreenFragment;
import com.montnets.liveroom.fragment.IMFragment;
import com.montnets.liveroom.fragment.IntroductionFragment;
import com.montnets.liveroom.im.IMManager;
import com.montnets.liveroom.listener.OnHideKeyboardListener;
import com.montnets.liveroom.upnp.DLNAManager;
import com.montnets.liveroom.utils.AudioFocusManager;
import com.montnets.liveroom.utils.DisplayUtil;
import com.montnets.liveroom.utils.OrientationManager;
import com.montnets.liveroom.view.DialogFactory;
import com.montnets.liveroom.view.LockableScrollView;
import com.montnets.liveroom.view.PlayerView;
import com.montnets.mwlive.net.NetBusiness;
import com.montnets.mwlive.net.OkRespCallBack;

import org.fourthline.cling.model.meta.Device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoActivity extends AppCompatActivity {
    private PlayerView playerMain;          //主屏幕
    private PlayerView playerAuxiliary;     //辅屏幕（小屏幕）
    private ImageView ivPreView;
    private RelativeLayout rlHeader;
    private ImageView ivBack;
    private ImageView ivTransTv;           //投屏
    private Dialog dialog;
    private ViewPager viewPager;
    private SlidingTabLayout tabLayout;

    private int orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    private int tag = 0;            //点击切换大小屏标示
    private int type;               //直播间类型
    private String videoID;         //视频ID：直播ID或者短视频ID
    private String videoUrl;        //短视频-原码地址
    private String liveUrl;         //直播-原码地址
    private String liveAfterUrl;    //直播录像-原码地址
    private boolean isAdd;

    private IMFragment imFragment;                      //聊天室Fragment
    private FullScreenFragment fullScreenFragment;      //全屏Fragment
    private ArrayList<String> rateMainList;
    private ArrayList<String> rateAuxList;
    private HashMap<String, String> rateMainMap;
    private HashMap<String, String> rateAuxMap;
    private AudioFocusManager audioFocusManager;
    private DLNAManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initDLNA();
        initData();
        initView();
        initListener();
        getData();
    }

    private void initDLNA() {
        manager = new DLNAManager();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        playerMain.resumePlay();
        playerAuxiliary.resumePlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        playerMain.pausePlay();
        playerAuxiliary.pausePlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        audioFocusManager.releaseTheAudioFocus();
        playerMain.stopPlay();
        playerAuxiliary.stopPlay();
        IMManager.getInstance().logout();
        imFragment = null;
        fullScreenFragment = null;
        OrientationManager.getInstance().removeRotateListener();
        OrientationManager.getInstance().onDetach();
        manager.stop();
    }

    @Override
    public void onBackPressed() {
        if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            super.onBackPressed();
        } else {
            switchScreen();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        orientation = newConfig.orientation;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ||
                newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
            StatusBarCompat.setTranslucent(getWindow(), true);
            rlHeader.setPadding(0, DisplayUtil.getStateBarHeight(this), 0, 0);
            imFragment.hideIMKeyboard();
            if (isAdd) {
                ft.show(fullScreenFragment);
            } else {
                fullScreenFragment = FullScreenFragment.getInstance();
                ft.add(R.id.full_screen_cover, fullScreenFragment);
                isAdd = true;
            }
        } else {
            rlHeader.setPadding(0, 0, 0, 0);
            StatusBarCompat.setTranslucent(getWindow(), false);
            fullScreenFragment.hideFullKeyboard();
            ft.hide(fullScreenFragment);
        }
        ft.commitAllowingStateLoss();
        super.onConfigurationChanged(newConfig);
    }

    public void showMediaController(boolean show) {
        playerMain.showMediaController(show);
        playerAuxiliary.showMediaController(show);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        List<View> excludeViews = new ArrayList<>();
        if (fullScreenFragment != null) {
            excludeViews.addAll(fullScreenFragment.getExcludeView());
        }
        if (imFragment != null) {
            excludeViews.addAll(imFragment.getExcludeView());
        }
        DisplayUtil.hideInputWhenTouchOtherView(this, ev, excludeViews,
                new OnHideKeyboardListener() {
                    @Override
                    public void onHideKeyboard() {
                        if (fullScreenFragment != null) {
                            fullScreenFragment.hideFullKeyboard();
                        }
                    }
                });
        return super.dispatchTouchEvent(ev);
    }

    private void initData() {
        audioFocusManager = new AudioFocusManager();
        audioFocusManager.requestTheAudioFocus();
        Intent intent = getIntent();
        Uri uri = intent.getData();
        if (uri != null) {  //H5跳转APP
            type = Integer.valueOf(uri.getQueryParameter("status"));
            videoID = uri.getQueryParameter("ID");
        } else {
            type = intent.getIntExtra(VideoConstants.TYPE, -1);
            if (type == VideoConstants.TYPE_LIVE) {
                videoID = intent.getStringExtra(VideoConstants.VIDEO_ID);
            } else if (type == VideoConstants.TYPE_VIDEO) {
                videoID = intent.getStringExtra(VideoConstants.VIDEO_ID);
            }
        }
    }

    private void initView() {
        playerMain = (PlayerView) findViewById(R.id.player_view_main);
        playerAuxiliary = (PlayerView) findViewById(R.id.player_view_auxiliary);

        if (type == VideoConstants.TYPE_LIVE) {
            playerMain.enableShowHeader(false);
            playerAuxiliary.enableShowHeader(false);
        } else if (type == VideoConstants.TYPE_VIDEO) {
            playerMain.enableShowHeader(true);
            playerAuxiliary.enableShowHeader(true);
        }
        ivPreView = (ImageView) findViewById(R.id.iv_preview);
        tabLayout = (SlidingTabLayout) findViewById(R.id.tab_indicator);
        viewPager = (ViewPager) findViewById(R.id.vp_container);
        LockableScrollView scrollView = (LockableScrollView) findViewById(R.id.lsv_player_root);
        scrollView.setScrollingEnabled(false);
        rlHeader = (RelativeLayout) findViewById(R.id.rl_header);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivTransTv = (ImageView) findViewById(R.id.iv_trans_tv);

        rateMainList = new ArrayList<>();
        rateAuxList = new ArrayList<>();
        rateMainMap = new HashMap<>();
        rateAuxMap = new HashMap<>();
    }

    private void initFragment(){
        List<Fragment> fragments = new ArrayList<>();
        imFragment = IMFragment.getInstance(videoID, type);
        IntroductionFragment introductionFragment = IntroductionFragment.getInstance();
        fragments.add(imFragment);
        fragments.add(introductionFragment);
        DetailFragmentAdapter fragmentAdapter = new DetailFragmentAdapter(getSupportFragmentManager(), new String[]{"聊天", "简介"}, fragments);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(0);
        tabLayout.setViewPager(viewPager);
        tabLayout.setCurrentTab(0);
        tabLayout.notifyDataSetChanged();
    }

    private void getData() {
        if (type == VideoConstants.TYPE_LIVE) {
            getLiveDetailData(videoID, "");
        } else if (type == VideoConstants.TYPE_VIDEO) {
            getVideoDetailData(videoID);
        }
    }

    private void initListener() {
        playerMain.setOnPlayerViewListener(new PlayerView.OnPlayerViewListener() {
            @Override
            public void onClick() {
                switchVideo();
            }

            @Override
            public void showFullScreen() {
                switchScreen();
            }

            @Override
            public void showHeader() {
                showHeaderViews();
            }

            @Override
            public void hideHeader() {
                hideHeaderViews();
            }
        });
        playerAuxiliary.setOnPlayerViewListener(new PlayerView.OnPlayerViewListener() {
            @Override
            public void onClick() {
                switchVideo();
            }

            @Override
            public void showFullScreen() {
                switchScreen();
            }

            @Override
            public void showHeader() {
                showHeaderViews();
            }

            @Override
            public void hideHeader() {
                hideHeaderViews();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ivTransTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<Device> devices = manager.getDevices();
                List<String> listStr = new ArrayList<>();
                for (Device device : devices) {
                    listStr.add(device.getDetails().getFriendlyName());
                }
                dialog = DialogFactory.createDialog(VideoActivity.this, listStr, new IMAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (type == VideoConstants.TYPE_LIVE) {
                            if (TextUtils.isEmpty(liveUrl)) {
                                Toast.makeText(VideoActivity.this, "直播地址为空！", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            manager.transToTV(liveUrl, devices.get(position));
                        } else if (type == VideoConstants.TYPE_LIVE_RECORD) {
                            if (TextUtils.isEmpty(liveAfterUrl)) {
                                Toast.makeText(VideoActivity.this, "视频地址为空！", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            manager.transToTV(liveAfterUrl, devices.get(position));
                        } else if (type == VideoConstants.TYPE_VIDEO) {
                            if (TextUtils.isEmpty(videoUrl)) {
                                Toast.makeText(VideoActivity.this, "视频地址为空！", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            manager.transToTV(videoUrl, devices.get(position));
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    private void showHeaderViews() {
        rlHeader.setVisibility(View.VISIBLE);
        ivTransTv.setVisibility(View.VISIBLE);
        if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ||
                orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
            fullScreenFragment.showViews(true);
        }
    }

    private void hideHeaderViews() {
        rlHeader.setVisibility(View.GONE);
        ivTransTv.setVisibility(View.GONE);
        if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ||
                orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
            fullScreenFragment.showViews(false);
        }
    }

    //小屏全屏切换
    private void switchScreen() {
        OrientationManager.getInstance().isClickedZoom = true;
        if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    //切换主副屏
    private void switchVideo() {
        if (playerAuxiliary.getVisibility() != View.VISIBLE) {
            return;
        }

        if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            if (tag == 0) {
                playerMain.showSmallView();
                playerMain.bringToFront();
                playerAuxiliary.showBigView();
                tag = 1;
            } else {
                playerMain.showBigView();
                playerAuxiliary.showSmallView();
                playerAuxiliary.bringToFront();
                tag = 0;
            }
        } else {
            if (tag == 0) {
                playerMain.showSmallView();
                playerMain.bringToFront();
                playerAuxiliary.showFullView();
                tag = 1;
            } else {
                playerMain.showFullView();
                playerAuxiliary.showSmallView();
                playerAuxiliary.bringToFront();
                tag = 0;
            }
        }
    }

    /**
     * 请求短视频详情数据
     */
    private void getVideoDetailData(String videoID) {
        Map<String, String> map = new HashMap<>();
        map.put("shortVideoId", videoID);
        NetBusiness.getInstance().okhttpPost(RequestConstants.VIDEO_DETAIL_URL, map, new OkRespCallBack<VideoDetail>(VideoDetail.class) {

            @Override
            public void onSuccess(VideoDetail response) {
                final VideoDetail.ObjEntity entity = response.getData();
                if (entity != null) {
                    videoUrl = entity.getVideoSource().getPlayUrl();
                    type = VideoConstants.TYPE_VIDEO;
                    handleVideo(entity);
                } else {
                    Toast.makeText(VideoActivity.this, "数据返回异常", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(int code, String msg) {
                Toast.makeText(VideoActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleVideo(VideoDetail.ObjEntity entity) {
        initFragment();

        playerMain.initConfig(PlayerView.TYPE_VIDEO);
        playerAuxiliary.initConfig(PlayerView.TYPE_VIDEO);
        playerMain.enableShowHeader(true);
        playerAuxiliary.enableShowHeader(true);

        String videoUrl480 = entity.getVideoSource().getPlayUrl480();
        String videoUrl720 = entity.getVideoSource().getPlayUrl720();
        if (!TextUtils.isEmpty(videoUrl)) {
            rateMainList.add(VideoConstants.MODEL_ORIGINAL);
            rateMainMap.put(VideoConstants.MODEL_ORIGINAL, videoUrl);
        }
        if (!TextUtils.isEmpty(videoUrl480)) {
            rateMainList.add(VideoConstants.MODEL_480P);
            rateMainMap.put(VideoConstants.MODEL_480P, videoUrl480);
        }
        if (!TextUtils.isEmpty(videoUrl720)) {
            rateMainList.add(VideoConstants.MODEL_720P);
            rateMainMap.put(VideoConstants.MODEL_720P, videoUrl720);
        }
        playerMain.setRateList(rateMainList);
        playerMain.setRateMap(rateMainMap);
        playerMain.startPlayVideo();
    }

    /**
     * 请求直播详情数据
     */
    private void getLiveDetailData(String liveId, String userId) {
        Map<String, String> map = new HashMap<>();
        map.put("liveId", liveId);
        map.put("userId", userId);
        NetBusiness.getInstance().okhttpPost(RequestConstants.LIVE_DETAIL_URL, map, new OkRespCallBack<LiveDetail>(LiveDetail.class) {

            @Override
            public void onSuccess(LiveDetail response) {
                LiveDetail.ObjBean entity = response.getData();
                if (entity != null) {
                    int liveState = entity.getLiveStatus();
                    switch (liveState) {
                        case 0: //预告
                            String liveCover = entity.getLiveCover();
                            Glide.with(VideoActivity.this)
                                    .load(liveCover)
                                    .placeholder(R.mipmap.ic_launcher)
                                    .into(ivPreView);
                            playerMain.setVisibility(View.INVISIBLE);
                            playerAuxiliary.setVisibility(View.INVISIBLE);
                            ivTransTv.setVisibility(View.INVISIBLE);
                            break;
                        case 1: //直播中
                            type = VideoConstants.TYPE_LIVE;
                            handleLiving(entity);
                            break;
                        case 2: //直播结束，观看直播录像
                            type = VideoConstants.TYPE_LIVE_RECORD;
                            handleLiveEnd(entity);
                            break;
                    }
                    playerMain.setCopyRight(entity.getCopyrightContent());
                    playerAuxiliary.setCopyRight(entity.getCopyrightContent());
                } else {
                    Toast.makeText(VideoActivity.this, "数据返回异常", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(int code, String msg) {
                Toast.makeText(VideoActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleLiving(LiveDetail.ObjBean entity) {
        initFragment();

        playerMain.initConfig(PlayerView.TYPE_LIVE);
        playerAuxiliary.initConfig(PlayerView.TYPE_LIVE);
        playerMain.enableShowHeader(false);
        playerAuxiliary.enableShowHeader(false);

        //================= 主屏播放地址 ===================
        liveUrl = entity.getPlayUrl();
        String liveUrl480 = entity.getPlayUrl480();
        String liveUrl720 = entity.getPlayUrl720();

        if (!TextUtils.isEmpty(liveUrl)) {
            rateMainList.add(VideoConstants.MODEL_ORIGINAL);
            rateMainMap.put(VideoConstants.MODEL_ORIGINAL, liveUrl);
        }
        if (!TextUtils.isEmpty(liveUrl480)) {
            rateMainList.add(VideoConstants.MODEL_480P);
            rateMainMap.put(VideoConstants.MODEL_480P, liveUrl480);
        }
        if (!TextUtils.isEmpty(liveUrl720)) {
            rateMainList.add(VideoConstants.MODEL_720P);
            rateMainMap.put(VideoConstants.MODEL_720P, liveUrl720);
        }
        playerMain.setRateList(rateMainList);
        playerMain.setRateMap(rateMainMap);
        playerMain.startPlayLive();

        //================= 辅屏播放地址 ===================
        LiveDetail.ObjBean.SlaveLiveInfoBean slaveLiveInfoBean = entity.getSlaveLiveInfo();
        if (slaveLiveInfoBean != null) {
            String slaveUrl = entity.getSlaveLiveInfo().getPlayUrl();
            String slaveUrl480 = entity.getSlaveLiveInfo().getPlayUrl480();
            String slaveUrl720 = entity.getSlaveLiveInfo().getPlayUrl720();
            if (!TextUtils.isEmpty(slaveUrl)) {
                rateAuxList.add(VideoConstants.MODEL_ORIGINAL);
                rateAuxMap.put(VideoConstants.MODEL_ORIGINAL, slaveUrl);
            }
            if (!TextUtils.isEmpty(slaveUrl480)) {
                rateAuxList.add(VideoConstants.MODEL_ORIGINAL);
                rateAuxMap.put(VideoConstants.MODEL_ORIGINAL, slaveUrl480);
            }
            if (!TextUtils.isEmpty(slaveUrl720)) {
                rateAuxList.add(VideoConstants.MODEL_ORIGINAL);
                rateAuxMap.put(VideoConstants.MODEL_ORIGINAL, slaveUrl720);
            }
            playerAuxiliary.setRateList(rateAuxList);
            playerAuxiliary.setRateMap(rateAuxMap);
            if (!TextUtils.isEmpty(slaveUrl)) {
                playerAuxiliary.setVisibility(View.VISIBLE);
                playerAuxiliary.startPlayLive();
            }
        }
    }

    private void handleLiveEnd(LiveDetail.ObjBean entity) {
        initFragment();

        playerMain.initConfig(PlayerView.TYPE_VIDEO);
        playerAuxiliary.initConfig(PlayerView.TYPE_VIDEO);
        playerMain.enableShowHeader(true);
        playerAuxiliary.enableShowHeader(true);
		rateMainList.clear();
        rateAuxList.clear();
        rateMainMap.clear();
        rateAuxMap.clear();

        //设置主屏

        liveAfterUrl = "http://v.facebac.com/live/5a0bb571341f519f5e4d/332c348c267ce5c4e86e/base/20190415144024/332c348c267ce5c4e86e-20190415144024.m3u8?xstToken=92f0a7f4";
        String liveUrl480 ="http://testv.facebac.com/live/581daa97b4723c20df9e/d7369b9a9faea2616a4f/base/20190306140524/d7369b9a9faea2616a4f-20190306140524.m3u8?xstToken=276f8109";
        String liveUrl720 ="http://testv.facebac.com/live/581daa97b4723c20df9e/4068935ff2878d7c02a6/base/20190108111340/4068935ff2878d7c02a6-20190108111340.m3u8?xstToken=66ffffb8";

//        liveAfterUrl = entity.getLiveAfterUrl();
        rateMainList.add(VideoConstants.MODEL_ORIGINAL);
        rateMainMap.put(VideoConstants.MODEL_ORIGINAL, liveAfterUrl);
        if (!TextUtils.isEmpty(liveUrl480)) {
            rateMainList.add(VideoConstants.MODEL_480P);
            rateMainMap.put(VideoConstants.MODEL_480P, liveUrl480);
        }
        if (!TextUtils.isEmpty(liveUrl720)) {
            rateMainList.add(VideoConstants.MODEL_720P);
            rateMainMap.put(VideoConstants.MODEL_720P, liveUrl720);
        }

        playerMain.setRateList(rateMainList);
        playerMain.setRateMap(rateMainMap);
        playerMain.startPlayVideo();

        //设置辅屏
        
//        if (entity.getSlaveLiveInfo() == null) {
//            return;
//        }
//        String slaveLiveAfter = entity.getSlaveLiveInfo().getLiveAfterUrl();
        String slaveLiveAfter = "http://v.facebac.com/live/5a0bb571341f519f5e4d/332c348c267ce5c4e86e/base/20190415144024/332c348c267ce5c4e86e-20190415144024.m3u8?xstToken=92f0a7f4";
        if (TextUtils.isEmpty(slaveLiveAfter)) {
            playerAuxiliary.setVisibility(View.GONE);
        } else {
            rateAuxList.add(VideoConstants.MODEL_ORIGINAL);
            rateAuxMap.put(VideoConstants.MODEL_ORIGINAL, slaveLiveAfter);

            if (!TextUtils.isEmpty(liveUrl480)) {
                rateAuxList.add(VideoConstants.MODEL_480P);
                rateAuxMap.put(VideoConstants.MODEL_480P, liveUrl480);
            }
            if (!TextUtils.isEmpty(liveUrl720)) {
                rateAuxList.add(VideoConstants.MODEL_720P);
                rateAuxMap.put(VideoConstants.MODEL_720P, liveUrl720);
            }
            playerAuxiliary.setRateList(rateAuxList);
            playerAuxiliary.setRateMap(rateAuxMap);
            playerAuxiliary.setVisibility(View.VISIBLE);
            playerAuxiliary.startPlayVideo();
        }
    }

}
