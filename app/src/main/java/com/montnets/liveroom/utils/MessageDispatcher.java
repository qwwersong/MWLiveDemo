package com.montnets.liveroom.utils;

/**
 * Created by songlei on 2018/12/11.
 */
public class MessageDispatcher {

    public MessageDispatcher() {

    }

//    public OnReceivedMsgListener onReceivedMsgListener = new OnReceivedMsgListener() {
//        @Override
//        public void onReceivedMessage(MsgMessage message) {
//            LogUtil.e("pp","IMFragment onReceivedMessage");
//            if (handleReceived == null) {
//                return;
//            }
//            handleReceived.onReceivedMessage(message);
//            String msg = message.data.msgbody;
//            imAdapter.addItem(message.nickName + " 说：" + msg);
//            rollToEnd();
//        }
//
//        @Override
//        public void onReceivedGift(MsgGift gift) {
//            LogUtil.e("pp","IMFragment onReceivedGift");
//            if (handleReceived == null) {
//                return;
//            }
//            handleReceived.onReceivedGift(gift);
//            String giftName = gift.data.giftName;
//            int giftNum = gift.data.giftNum;
//            imAdapter.addItem(gift.nickName + " 送了：" + giftNum + "个" + giftName);
//            rollToEnd();
//        }
//
//        @Override
//        public void onReceivedNotice(MsgNotice notice) {
//            if (handleReceived == null) {
//                return;
//            }
//            handleReceived.onReceivedNotice(notice);
//            imAdapter.addItem("系统公告：" + notice.data.msgbody);
//        }
//
//        @Override
//        public void onReceivedStar(MsgStar star) {
//            if (handleReceived == null) {
//                return;
//            }
//            handleReceived.onReceivedStar(star);
//            imAdapter.addItem(star.nickName + " 点赞");
//            rollToEnd();
//        }
//
//        @Override
//        public void onReceivedCustomMsg(MsgCustomize customize) {
//            if (handleReceived == null) {
//                return;
//            }
//            handleReceived.onReceivedCustomMsg(customize);
//            imAdapter.addItem(customize.nickName + " 发送自定义消息：" + customize.data.toString());
//        }
//
//        @Override
//        public void onReceivedSysTip(MsgSystemTip systemTip) {
//            if (handleReceived == null) {
//                return;
//            }
//            handleReceived.onReceivedSysTip(systemTip);
//            imAdapter.addItem("系统提示：" + systemTip.msg);
//        }
//
//        @Override
//        public void onReceivedSilence(MsgSilence silence) {
//            if (handleReceived == null) {
//                return;
//            }
//            handleReceived.onReceivedSilence(silence);
//        }
//
//        @Override
//        public void onReceivedPaper(MsgQuestionnaire questionnaire) {
//            if (handleReceived == null) {
//                return;
//            }
//            handleReceived.onReceivedPaper(questionnaire);
//        }
//    };
//
//    public OnSocketStateListener onSocketStateListener = new OnSocketStateListener() {
//        @Override
//        public void onSuccess() {
//            if (handleIMState == null) {
//                return;
//            }
//            handleIMState.onSuccess();
//            imAdapter.addItem("聊天室登录成功");
//        }
//
//        @Override
//        public void onError(IMException e) {
//            if (handleIMState == null) {
//                return;
//            }
//            handleIMState.onError(e);
//            switch (e.code) {
//                case IMException.ERROR_CONNECT:
//                    imAdapter.addItem("聊天室登录失败... 重连中.....");
//                    break;
//                case IMException.ERROR_CONNECT_TIME_OUT:
//                    imAdapter.addItem("聊天室连接超时... 请检查网络....");
//                    break;
//                case IMException.ERROR_REQUEST_FAIL:
//                    imAdapter.addItem("聊天室登录失败... 请检查网络....");
//                    break;
//                case IMException.ERROR_RECEIVE_MSG_FAIL:
//                    imAdapter.addItem("接收消息失败........");
//                    break;
//                case IMException.ERROR_PARSE_MSG_FAIL:
//                    imAdapter.addItem("消息解析异常........");
//                    break;
//            }
//        }
}
