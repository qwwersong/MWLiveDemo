package com.montnets.liveroom;

public class RequestConstants {
    private static final String DOMAIN = "http://nx.facebac.com";
    //直播列表接口
    public static final String LIVE_LIST_URL = DOMAIN + "/farmbank-server/liveInfo/getLiveInfoList.action";
    //直播详情接口
    public static final String LIVE_DETAIL_URL = DOMAIN + "/farmbank-server/liveInfo/getLiveInfoByAdmin.action";//getLiveInfo
    //短视频列表接口
    public static final String VIDEO_LIST_URL = DOMAIN + "/farmbank-server/shortVideo/getShortVideoList.action";
    //短视频详情接口
    public static final String VIDEO_DETAIL_URL = DOMAIN + "/farmbank-server/shortVideo/getShortVideo.action";

}
