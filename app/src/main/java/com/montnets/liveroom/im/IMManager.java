package com.montnets.liveroom.im;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.montnets.liveroom.RequestConstants;
import com.montnets.liveroom.im.bean.IMGift;
import com.montnets.liveroom.im.bean.IMMessage;
import com.montnets.liveroom.im.bean.IMRoom;
import com.montnets.liveroom.im.bean.MsgCustomize;
import com.montnets.liveroom.im.bean.MsgGift;
import com.montnets.liveroom.im.bean.MsgJoinRoom;
import com.montnets.liveroom.im.bean.MsgLeaveRoom;
import com.montnets.liveroom.im.bean.MsgMessage;
import com.montnets.liveroom.im.bean.MsgNotice;
import com.montnets.liveroom.im.bean.MsgQuestion;
import com.montnets.liveroom.im.bean.MsgSilence;
import com.montnets.liveroom.im.bean.MsgStar;
import com.montnets.liveroom.im.bean.MsgSystemTip;
import com.montnets.liveroom.im.bean.MsgVideoState;
import com.montnets.mwlive.LiveRoom;
import com.montnets.mwlive.base.GsonUtil;
import com.montnets.mwlive.net.NetBusiness;
import com.montnets.mwlive.net.OkRespCallBack;
import com.montnets.mwlive.socket.OnReceivedMsgListener;
import com.montnets.mwlive.socket.OnSocketStateListener;
import com.montnets.mwlive.socket.SocketConstants;
import com.montnets.mwlive.socket.SocketException;
import com.montnets.mwlive.socket.bean.IMUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by songlei on 2018/12/20.
 */
public class IMManager {
    private JSONObject mCommonMsgObj;
    private Gson gson = new Gson();

    private static volatile IMManager mInstance;
    private LiveRoom liveRoom;

    private OnIMStateListener onIMStateListener;
    private List<OnPlayerStateListener> playerListeners;
    private List<OnHandleMsgListener> handleMsgListeners;
    private IMUser user;

    public static IMManager getInstance() {
        if (mInstance == null) {
            synchronized (IMManager.class) {
                if (mInstance == null) {
                    mInstance = new IMManager();
                }
            }
        }
        return mInstance;
    }

    private IMManager(){
        liveRoom = LiveRoom.getInstance();
        playerListeners = new ArrayList<>();
        handleMsgListeners = new ArrayList<>();
    }

    public void login(String videoID, IMUser user, OnIMStateListener onIMStateListener){
        this.user = user;
        this.onIMStateListener = onIMStateListener;
        fetchRoomID(videoID);
    }

    public void logout(){
        liveRoom.logoutRoom();
    }

    private void onMsgHandle(JSONObject mReceivedData) {
        try {
            int cmd = mReceivedData.getInt(SocketConstants.CMD);
            String data = mReceivedData.toString();
            switch (cmd) {
                case SocketConstants.CMD_ROOM_ENTER:
                    MsgJoinRoom msgJoinRoom = gson.fromJson(data, MsgJoinRoom.class);
                    break;
                case SocketConstants.CMD_ROOM_EXIT:
                    MsgLeaveRoom msgLeaveRoom = gson.fromJson(data, MsgLeaveRoom.class);
                    break;
                case IMConstants.CMD_MSG_SYSTEM_TIP:
                    MsgSystemTip msgSystemTip = gson.fromJson(data, MsgSystemTip.class);
                    if (handleMsgListeners != null && handleMsgListeners.size() > 0) {
                        for (int i = 0; i < handleMsgListeners.size(); i++) {
                            handleMsgListeners.get(i).onReceivedSysTip(msgSystemTip);
                        }
                    }
                    break;
                case IMConstants.CMD_MSG_TXT:
                    MsgMessage msgMessage = gson.fromJson(data, MsgMessage.class);
                    if (handleMsgListeners != null && handleMsgListeners.size() > 0) {
                        for (int i = 0; i < handleMsgListeners.size(); i++) {
                            handleMsgListeners.get(i).onReceivedMessage(msgMessage);
                        }
                    }
                    break;
                case IMConstants.CMD_MSG_GIFT:
                    MsgGift msgGift = gson.fromJson(data, MsgGift.class);
                    if (handleMsgListeners != null && handleMsgListeners.size() > 0) {
                        for (int i = 0; i < handleMsgListeners.size(); i++) {
                            handleMsgListeners.get(i).onReceivedGift(msgGift);
                        }
                    }
                    break;
                case IMConstants.CMD_MSG_STAR:
                    MsgStar msgStar = gson.fromJson(data, MsgStar.class);
                    if (handleMsgListeners != null && handleMsgListeners.size() > 0) {
                        for (int i = 0; i < handleMsgListeners.size(); i++) {
                            handleMsgListeners.get(i).onReceivedStar(msgStar);
                        }
                    }
                    break;
                case IMConstants.CMD_NOTIFICATION:
                    MsgNotice msgNotice = gson.fromJson(data, MsgNotice.class);
                    if (handleMsgListeners != null && handleMsgListeners.size() > 0) {
                        for (int i = 0; i < handleMsgListeners.size(); i++) {
                            handleMsgListeners.get(i).onReceivedNotice(msgNotice);
                        }
                    }
                    break;
                case IMConstants.CMD_MSG_CUSTOMIZE:
                    MsgCustomize msgCustomize = gson.fromJson(data, MsgCustomize.class);
                    if (handleMsgListeners != null && handleMsgListeners.size() > 0) {
                        for (int i = 0; i < handleMsgListeners.size(); i++) {
                            handleMsgListeners.get(i).onReceivedCustomMsg(msgCustomize);
                        }
                    }
                    break;
                case IMConstants.CMD_MSG_QUESTION_RECEIVED:
                    MsgQuestion msgQuestion = gson.fromJson(data, MsgQuestion.class);
                    if (handleMsgListeners != null && handleMsgListeners.size() > 0) {
                        for (int i = 0; i < handleMsgListeners.size(); i++) {
                            handleMsgListeners.get(i).onReceivedQuestion(msgQuestion);
                        }
                    }
                    break;
                case IMConstants.CMD_MSG_SILENCE_LIST:
                case IMConstants.CMD_MSG_ADD_SILENCE:
                case IMConstants.CMD_MSG_REMOVE_SILENCE:
                case IMConstants.CMD_MSG_SILENCE_STATE:
                case IMConstants.CMD_MSG_REMOVE_SINGLE:
                case IMConstants.CMD_MSG_SILENCE_ALL:
                    MsgSilence msgSilence = gson.fromJson(data, MsgSilence.class);
                    if (handleMsgListeners != null && handleMsgListeners.size() > 0) {
                        for (int i = 0; i < handleMsgListeners.size(); i++) {
                            handleMsgListeners.get(i).onReceivedSilence(msgSilence);
                        }
                    }
                    break;
                case IMConstants.CMD_MSG_VIDEO_STATE:
                    MsgVideoState mMsgVideoState = gson.fromJson(data, MsgVideoState.class);
                    if (playerListeners != null && playerListeners.size() > 0) {
                        for (int i = 0; i < playerListeners.size(); i++) {
                            playerListeners.get(i).onPlayerState(mMsgVideoState);
                        }
                    }
                    break;
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            if (onIMStateListener != null) {
                onIMStateListener.onError(new IMException(IMException.ERROR_PARSE_MSG_FAIL, "获取消息解析错误"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            if (onIMStateListener != null) {
                onIMStateListener.onError(new IMException(IMException.ERROR_PARSE_MSG_FAIL, "获取消息解析错误"));
            }
        }
    }

    private void sendMsgByCMD(int cmd, Object msgBean) {
        try {
            if (mCommonMsgObj == null) {
                mCommonMsgObj = new JSONObject();
            }
            mCommonMsgObj.put("nickName", user.nickName)
                    .put("imgUrl", user.imgUrl)
                    .put("user_id", user.user_id)
                    .put(SocketConstants.CMD, cmd);
            switch (cmd) {
                case IMConstants.CMD_MSG_TXT:
                    mCommonMsgObj.put(SocketConstants.MSG, "聊天文本");
                    break;
                case IMConstants.CMD_MSG_GIFT:
                    mCommonMsgObj.put(SocketConstants.MSG, "送礼");
                    break;
                case IMConstants.CMD_MSG_STAR:
                    mCommonMsgObj.put(SocketConstants.MSG, "点赞");
                    break;
                case IMConstants.CMD_MSG_CUSTOMIZE:
                    mCommonMsgObj.put(SocketConstants.MSG, "自定义消息");
                    break;
            }
            if (msgBean != null) {
                String json = GsonUtil.createGsonString(msgBean);
                JSONObject mMsgDataObj = new JSONObject(json);
                mCommonMsgObj.put("data", mMsgDataObj);
            }
            liveRoom.sendMsg(mCommonMsgObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送聊天消息
     * @param msg IMessage类型
     *            属性：String类型的msgbody
     */
    public void sendMessage(IMMessage msg){
        sendMsgByCMD(IMConstants.CMD_MSG_TXT, msg);
    }

    /**
     * 发送礼物
     * @param msg IMGift类型
     *            属性：giftName 礼物名称
     *                  giftNum  礼物数量
     *                  giftID   礼物ID
     *                  giftImg  礼物图片
     */
    public void sendGift(IMGift msg){
        sendMsgByCMD(IMConstants.CMD_MSG_GIFT, msg);
    }

    /**
     * 发送点赞
     */
    public void sendStar(){
        sendMsgByCMD(IMConstants.CMD_MSG_STAR, null);
    }

    /**
     * 发送自定义消息
     * @param msg IMessage类型
     *            属性：String类型的msgbody
     */
    public void sendCustomize(IMMessage msg){
        sendMsgByCMD(IMConstants.CMD_MSG_CUSTOMIZE, msg);
    }

    public void registerOnPlayerStateListener(OnPlayerStateListener onPlayerStateListener) {
        playerListeners.add(onPlayerStateListener);
    }

    public void registerOnHandleMsgListener(OnHandleMsgListener onHandleMsgListener){
        handleMsgListeners.add(onHandleMsgListener);
    }

    public void unregister(OnHandleMsgListener onHandleMsgListener){
        handleMsgListeners.remove(onHandleMsgListener);
    }

    private OnReceivedMsgListener onReceivedMsgListener = new OnReceivedMsgListener() {
        @Override
        public void onReceivedMsg(JSONObject msg) {
            onMsgHandle(msg);
        }
    };

    private OnSocketStateListener onSocketStateListener = new OnSocketStateListener() {
        @Override
        public void onSuccess() {
            onIMStateListener.onSuccess();
        }

        @Override
        public void onError(SocketException e) {
            onIMStateListener.onError(new IMException(e.code, e.cause));
        }
    };

    /**
     * 获取直播间信息
     *
     * @param videoID 直播ID / 短视频ID
     */
    private void fetchRoomID(String videoID) {
        Map<String, String> map = new HashMap<>();
        map.put("cmd", "lb");
        map.put("liveID", videoID);
        NetBusiness.getInstance().okhttpPost(RequestConstants.IM_URL, map, new OkRespCallBack<IMRoom>(IMRoom.class) {

            @Override
            public void onSuccess(IMRoom response) {
                String roomIP = response.getData().getOut_room_ServerAddress();
                String roomID = response.getData().getOut_roomID() + "";
                if (!TextUtils.isEmpty(roomIP)) {
                    liveRoom.loginRoom(roomIP, roomID, user, onSocketStateListener, onReceivedMsgListener);
                }
            }

            @Override
            public void onError(int code, String msg) {
                onIMStateListener.onError(new IMException(IMException.ERROR_REQUEST_FAIL, "请求聊天室地址异常"));
            }
        });
    }
}
