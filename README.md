# 接入指引

### 1 概述
#### 1.1 Demo下载
[Demo地址：https://github.com/qwwersong/MWLiveDemo](https://github.com/qwwersong/MWLiveDemo "Demo地址：")

#### 1.2 SDK下载
[SDK地址：https://github.com/qwwersong/MWLiveSDK](https://github.com/qwwersong/MWLiveSDK "SDK地址：")

#### 1.3 Demo指引

>打开Android Studio 导入Demo项目，推荐使用真机调试。


![](http://data.eolinker.com/VAZB7TGa8a8f4884f8f69b2f24a462a865a505898b97fce)

**聊天室协议解析**

聊天室管理类：IMManager
该类封装了SDK的聊天室接口和监听，并发送和接收消息方法进行扩展

发送消息扩展：
```
/**
* 发送聊天消息
* @param msg IMessage类型
*            属性：msgbody 消息内容
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
*            属性：msgbody 消息内容
*/
public void sendCustomize(IMMessage msg){
	sendMsgByCMD(IMConstants.CMD_MSG_CUSTOMIZE, msg);
}
```
IMConstants定义了协议cmd常量
sendMsgByCMD方法根据协议，封装特定一些参数，最后调用SDK中LiveRoom.getInstance().sendMsg(JSONObject msg);
>具体协议内容请参考聊天室协议文档

监听消息扩展：
```
private OnReceivedMsgListener onReceivedMsgListener = new OnReceivedMsgListener() {
        @Override
        public void onReceivedMsg(JSONObject msg) {
            onMsgHandle(msg);
        }
    };
```
onMsgHandle方法是负责解析从SDK接收到的消息
根据cmd区分协议，利用gson将json字符串解析成实体对象，并利用回调方法传递出去。
```
public interface OnHandleMsgListener {
    /**
     * 收到进入聊天室
     */
    void onReceiveEnter(MsgJoinRoom enterRoom);
	
    /**
     * 收到聊天信息
     */
    void onReceivedMessage(MsgMessage message);

    /**
     * 收到礼物信息
     */
    void onReceivedGift(MsgGift gift);

    /**
     * 收到公告信息
     */
    void onReceivedNotice(MsgNotice notice);

    /**
     * 收到点赞信息
     */
    void onReceivedStar(MsgStar star);

    /**
     * 收到自定义信息
     */
    void onReceivedCustomMsg(MsgCustomize customize);

    /**
     * 收到系统提示信息
     */
    void onReceivedSysTip(MsgSystemTip systemTip);

    /**
     * 收到禁言信息
     */
    void onReceivedSilence(MsgSilence silence);

    /**
     * 收到问卷信息
     */
    void onReceivedQuestion(MsgQuestion question);

}
```
实体对象属性根据协议文档进行定义，详情可参考聊天室协议文档，另属性都有注释

>注意：禁言参数是多个协议信息构成（包含2010、2011、2012、2013、2022、2024），根据cmd区分协议。
这些协议中的字段已全部包含在MsgSilence类中，根据协议获取对应属性数据。在该类中也同样有注释，详情页请参考聊天室协议文档。

OnPlayerStateListener：监听流状态回调，分为主流和辅流，通过registerOnPlayerStateListener设置
通过register来设置监听，可设置多个监听，例如OnPlayerStateListener和OnReceivedMsgListener

```
IMManager.getInstance().registerOnPlayerStateListener(new OnPlayerStateListener() {
	@Override
	public void onPlayerState(MsgVideoState msgVideoState) {
		if (isMain) {
			setVideoState(msgVideoState.data.live_status);
		} else {
			setVideoState(msgVideoState.data.slave_status);
		}
	}
});
private void setVideoState(int state) {
	switch (state) {
		case PlayerConstants.LIVE_STATUS_PREVIEW:
			videoCover.showTip("视频未审核");
			break;
		case PlayerConstants.LIVE_STATUS_LIVING:
			videoCover.showTip("正在播放");
			break;
		case PlayerConstants.LIVE_STATUS_END:
			videoCover.showTip("播放结束");
			break;
		case PlayerConstants.LIVE_STATUS_ERROR:
			videoCover.showTip("异常中断");
			break;
		case PlayerConstants.LIVE_STATUS_TIMEOUT:
			videoCover.showTip("直播超时");
			break;
	}
}
```

>注意：该回调是通过聊天室2002协议，来监听流的状态（包括：正在播放、审核状态、异常等）区分主流和辅流
详情请参考聊天室协议文档。


