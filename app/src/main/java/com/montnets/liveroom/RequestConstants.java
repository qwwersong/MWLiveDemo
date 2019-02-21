package com.montnets.liveroom;

public class RequestConstants {
    private static final String TEST_DOMAIN = "http://nxtest.facebac.com";
    private static final String DEVELOP_DOMAIN = "http://nx.facebac.com";
    private static final String DOMAIN = TEST_DOMAIN;
    //直播列表接口
    public static final String LIVE_LIST_URL = DOMAIN + "/farmbank-server/liveInfo/getLiveInfoList.action";
    //直播详情接口
    public static final String LIVE_DETAIL_URL = DOMAIN + "/farmbank-server/liveInfo/getLiveInfo.action";//getLiveInfoByAdmin
    //短视频列表接口
    public static final String VIDEO_LIST_URL = DOMAIN + "/farmbank-server/shortVideo/getShortVideoList.action";
    //短视频详情接口
    public static final String VIDEO_DETAIL_URL = DOMAIN + "/farmbank-server/shortVideo/getShortVideo.action";
    //请求聊天室IP以及ID接口
    private static final String TEST_SOCKET_DOMAIN = "http://nxtrtest.facebac.com:9882";
    private static final String DEVELOP_SOCKET_DOMAIN = "http://nx.facebac.com:9882";
    public static final String IM_URL = TEST_SOCKET_DOMAIN + "/dispatcher.action";

}
