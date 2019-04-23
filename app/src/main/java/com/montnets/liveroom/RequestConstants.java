package com.montnets.liveroom;

public class RequestConstants {
    //测试域名
    private static final String TEST_DOMAIN = "https://www.zj96596.com.cn:50080";
    private static final String DOMAIN = TEST_DOMAIN;
    //直播列表接口
    public static final String LIVE_LIST_URL = DOMAIN + "/farmbank-server/liveInfo/getLiveInfoList.action";
    //直播详情接口
    public static final String LIVE_DETAIL_URL = DOMAIN + "/farmbank-server/liveInfo/getLiveInfo.action";
    //短视频列表接口
    public static final String VIDEO_LIST_URL = DOMAIN + "/farmbank-server/shortVideo/getShortVideoList.action";
    //短视频详情接口
    public static final String VIDEO_DETAIL_URL = DOMAIN + "/farmbank-server/shortVideo/getShortVideo.action";


    //请求聊天室IP以及ID接口
//    private static final String TEST_SOCKET_DOMAIN = "http://nxtrtest.facebac.com:9882";
    private static final String TEST_SOCKET_DOMAIN = "http://nxwwtest.mvaas.cn";
//    private static final String TEST_SOCKET_DOMAIN = "http://218.25.208.125:9882";
    public static final String IM_URL = TEST_SOCKET_DOMAIN + "/dispatcher.action";

}
