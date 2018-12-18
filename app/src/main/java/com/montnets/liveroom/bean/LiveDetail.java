package com.montnets.liveroom.bean;

import java.io.Serializable;

/**
 * 直播详情bean
 */
public class LiveDetail implements Serializable {
    /**
     * code : 200
     * msg : 成功
     * obj : {"appointNum":0,"beginTime":"1970-01-01 08:00:00","copyrightContent":"","createTime":"2018-11-29 09:04:21","currentTime":"2018-11-29 09:20:39","del":0,"endTime":"1970-01-01 08:00:00","id":"169956480122945153","isrecord":1,"istranscode":0,"liveAfter":"","liveAfterType":1,"liveCover":"","liveName":"双画面直播h","liveStatus":0,"liveSwitch":1,"liveTextImgs":"","liveWay":2,"m3u8Url":"http://testhls.facebac.com/live/afb98a0a9b79573235eb/playlist.m3u8?token=22fd832373133c8d64ff&secret=bcc68a45e15c23ede1bb63824b8f3231&ckey=f4a05ce01b8410f2464f2044e632e56c&xstToken=ae8bcdf8","m3u8Url480":"","m3u8Url720":"","masterId":"169956480122945153","planTime":"1970-01-01 08:00:00","playUrl":"rtmp://testdown.facebac.com/live/afb98a0a9b79573235eb?token=22fd832373133c8d64ff&secret=bcc68a45e15c23ede1bb63824b8f3231&ckey=f4a05ce01b8410f2464f2044e632e56c&xstToken=ae8bcdf8","playUrl480":"","playUrl720":"","preventRecordScreen":0,"pushUrl":"rtmp://testpush.facebac.com/live/afb98a0a9b79573235eb?token=22fd832373133c8d64ff&secret=bcc68a45e15c23ede1bb63824b8f3231&push_code=ae8bcdf8&ckey=f4a05ce01b8410f2464f2044e632e56c","shareUrl":"http://nx.facebac.com/H5/vLive.html?liveId=169956480122945153","shiftTime":"1970-01-01 08:00:00","slaveLiveInfo":{"m3u8Url":"http://testhls.facebac.com/live/b78c603c23d5b60ef213/playlist.m3u8?token=22fd832373133c8d64ff&secret=32de807e282e8d1728d05817a0890906&ckey=f4a05ce01b8410f2464f2044e632e56c&xstToken=cf1dddb5","m3u8Url480":"","m3u8Url720":"","playUrl":"rtmp://testdown.facebac.com/live/b78c603c23d5b60ef213?token=22fd832373133c8d64ff&secret=32de807e282e8d1728d05817a0890906&ckey=f4a05ce01b8410f2464f2044e632e56c&xstToken=cf1dddb5","playUrl480":"","playUrl720":"","pushUrl":"rtmp://testpush.facebac.com/live/b78c603c23d5b60ef213?token=22fd832373133c8d64ff&secret=32de807e282e8d1728d05817a0890906&push_code=cf1dddb5&ckey=f4a05ce01b8410f2464f2044e632e56c","liveAfter":""},"slavePushUrl":"","thumbsUp":0,"totalViews":0,"updateTime":"2018-11-29 09:04:21","userId":"45"}
     */

    private String code;
    private String msg;
    private ObjBean obj;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * appointNum : 0
         * beginTime : 1970-01-01 08:00:00
         * copyrightContent :
         * createTime : 2018-11-29 09:04:21
         * currentTime : 2018-11-29 09:20:39
         * del : 0
         * endTime : 1970-01-01 08:00:00
         * id : 169956480122945153
         * isrecord : 1
         * istranscode : 0
         * liveAfter :
         * liveAfterType : 1
         * liveCover :
         * liveName : 双画面直播h
         * liveStatus : 0
         * liveSwitch : 1
         * liveTextImgs :
         * liveWay : 2
         * m3u8Url : http://testhls.facebac.com/live/afb98a0a9b79573235eb/playlist.m3u8?token=22fd832373133c8d64ff&secret=bcc68a45e15c23ede1bb63824b8f3231&ckey=f4a05ce01b8410f2464f2044e632e56c&xstToken=ae8bcdf8
         * m3u8Url480 :
         * m3u8Url720 :
         * masterId : 169956480122945153
         * planTime : 1970-01-01 08:00:00
         * playUrl : rtmp://testdown.facebac.com/live/afb98a0a9b79573235eb?token=22fd832373133c8d64ff&secret=bcc68a45e15c23ede1bb63824b8f3231&ckey=f4a05ce01b8410f2464f2044e632e56c&xstToken=ae8bcdf8
         * playUrl480 :
         * playUrl720 :
         * preventRecordScreen : 0
         * pushUrl : rtmp://testpush.facebac.com/live/afb98a0a9b79573235eb?token=22fd832373133c8d64ff&secret=bcc68a45e15c23ede1bb63824b8f3231&push_code=ae8bcdf8&ckey=f4a05ce01b8410f2464f2044e632e56c
         * shareUrl : http://nx.facebac.com/H5/vLive.html?liveId=169956480122945153
         * shiftTime : 1970-01-01 08:00:00
         * slaveLiveInfo : {"m3u8Url":"http://testhls.facebac.com/live/b78c603c23d5b60ef213/playlist.m3u8?token=22fd832373133c8d64ff&secret=32de807e282e8d1728d05817a0890906&ckey=f4a05ce01b8410f2464f2044e632e56c&xstToken=cf1dddb5","m3u8Url480":"","m3u8Url720":"","playUrl":"rtmp://testdown.facebac.com/live/b78c603c23d5b60ef213?token=22fd832373133c8d64ff&secret=32de807e282e8d1728d05817a0890906&ckey=f4a05ce01b8410f2464f2044e632e56c&xstToken=cf1dddb5","playUrl480":"","playUrl720":"","pushUrl":"rtmp://testpush.facebac.com/live/b78c603c23d5b60ef213?token=22fd832373133c8d64ff&secret=32de807e282e8d1728d05817a0890906&push_code=cf1dddb5&ckey=f4a05ce01b8410f2464f2044e632e56c","liveAfter":""}
         * slavePushUrl :
         * thumbsUp : 0
         * totalViews : 0
         * updateTime : 2018-11-29 09:04:21
         * userId : 45
         */

        private int appointNum;
        private String beginTime;
        private String copyrightContent;
        private String createTime;
        private String currentTime;
        private int del;
        private String endTime;
        private String id;
        private int isrecord;
        private int istranscode;
        private String liveAfter;
        private int liveAfterType;
        private String liveCover;
        private String liveName;
        private int liveStatus;
        private int liveSwitch;
        private String liveTextImgs;
        private int liveWay;
        private String m3u8Url;
        private String m3u8Url480;
        private String m3u8Url720;
        private String masterId;
        private String planTime;
        private String playUrl;
        private String playUrl480;
        private String playUrl720;
        private int preventRecordScreen;
        private String pushUrl;
        private String shareUrl;
        private String shiftTime;
        private SlaveLiveInfoBean slaveLiveInfo;
        private String slavePushUrl;
        private int thumbsUp;
        private int totalViews;
        private String updateTime;
        private String userId;

        public int getAppointNum() {
            return appointNum;
        }

        public void setAppointNum(int appointNum) {
            this.appointNum = appointNum;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getCopyrightContent() {
            return copyrightContent;
        }

        public void setCopyrightContent(String copyrightContent) {
            this.copyrightContent = copyrightContent;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCurrentTime() {
            return currentTime;
        }

        public void setCurrentTime(String currentTime) {
            this.currentTime = currentTime;
        }

        public int getDel() {
            return del;
        }

        public void setDel(int del) {
            this.del = del;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIsrecord() {
            return isrecord;
        }

        public void setIsrecord(int isrecord) {
            this.isrecord = isrecord;
        }

        public int getIstranscode() {
            return istranscode;
        }

        public void setIstranscode(int istranscode) {
            this.istranscode = istranscode;
        }

        public String getLiveAfter() {
            return liveAfter;
        }

        public void setLiveAfter(String liveAfter) {
            this.liveAfter = liveAfter;
        }

        public int getLiveAfterType() {
            return liveAfterType;
        }

        public void setLiveAfterType(int liveAfterType) {
            this.liveAfterType = liveAfterType;
        }

        public String getLiveCover() {
            return liveCover;
        }

        public void setLiveCover(String liveCover) {
            this.liveCover = liveCover;
        }

        public String getLiveName() {
            return liveName;
        }

        public void setLiveName(String liveName) {
            this.liveName = liveName;
        }

        public int getLiveStatus() {
            return liveStatus;
        }

        public void setLiveStatus(int liveStatus) {
            this.liveStatus = liveStatus;
        }

        public int getLiveSwitch() {
            return liveSwitch;
        }

        public void setLiveSwitch(int liveSwitch) {
            this.liveSwitch = liveSwitch;
        }

        public String getLiveTextImgs() {
            return liveTextImgs;
        }

        public void setLiveTextImgs(String liveTextImgs) {
            this.liveTextImgs = liveTextImgs;
        }

        public int getLiveWay() {
            return liveWay;
        }

        public void setLiveWay(int liveWay) {
            this.liveWay = liveWay;
        }

        public String getM3u8Url() {
            return m3u8Url;
        }

        public void setM3u8Url(String m3u8Url) {
            this.m3u8Url = m3u8Url;
        }

        public String getM3u8Url480() {
            return m3u8Url480;
        }

        public void setM3u8Url480(String m3u8Url480) {
            this.m3u8Url480 = m3u8Url480;
        }

        public String getM3u8Url720() {
            return m3u8Url720;
        }

        public void setM3u8Url720(String m3u8Url720) {
            this.m3u8Url720 = m3u8Url720;
        }

        public String getMasterId() {
            return masterId;
        }

        public void setMasterId(String masterId) {
            this.masterId = masterId;
        }

        public String getPlanTime() {
            return planTime;
        }

        public void setPlanTime(String planTime) {
            this.planTime = planTime;
        }

        public String getPlayUrl() {
            return playUrl;
        }

        public void setPlayUrl(String playUrl) {
            this.playUrl = playUrl;
        }

        public String getPlayUrl480() {
            return playUrl480;
        }

        public void setPlayUrl480(String playUrl480) {
            this.playUrl480 = playUrl480;
        }

        public String getPlayUrl720() {
            return playUrl720;
        }

        public void setPlayUrl720(String playUrl720) {
            this.playUrl720 = playUrl720;
        }

        public int getPreventRecordScreen() {
            return preventRecordScreen;
        }

        public void setPreventRecordScreen(int preventRecordScreen) {
            this.preventRecordScreen = preventRecordScreen;
        }

        public String getPushUrl() {
            return pushUrl;
        }

        public void setPushUrl(String pushUrl) {
            this.pushUrl = pushUrl;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public String getShiftTime() {
            return shiftTime;
        }

        public void setShiftTime(String shiftTime) {
            this.shiftTime = shiftTime;
        }

        public SlaveLiveInfoBean getSlaveLiveInfo() {
            return slaveLiveInfo;
        }

        public void setSlaveLiveInfo(SlaveLiveInfoBean slaveLiveInfo) {
            this.slaveLiveInfo = slaveLiveInfo;
        }

        public String getSlavePushUrl() {
            return slavePushUrl;
        }

        public void setSlavePushUrl(String slavePushUrl) {
            this.slavePushUrl = slavePushUrl;
        }

        public int getThumbsUp() {
            return thumbsUp;
        }

        public void setThumbsUp(int thumbsUp) {
            this.thumbsUp = thumbsUp;
        }

        public int getTotalViews() {
            return totalViews;
        }

        public void setTotalViews(int totalViews) {
            this.totalViews = totalViews;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public static class SlaveLiveInfoBean {
            /**
             * m3u8Url : http://testhls.facebac.com/live/b78c603c23d5b60ef213/playlist.m3u8?token=22fd832373133c8d64ff&secret=32de807e282e8d1728d05817a0890906&ckey=f4a05ce01b8410f2464f2044e632e56c&xstToken=cf1dddb5
             * m3u8Url480 :
             * m3u8Url720 :
             * playUrl : rtmp://testdown.facebac.com/live/b78c603c23d5b60ef213?token=22fd832373133c8d64ff&secret=32de807e282e8d1728d05817a0890906&ckey=f4a05ce01b8410f2464f2044e632e56c&xstToken=cf1dddb5
             * playUrl480 :
             * playUrl720 :
             * pushUrl : rtmp://testpush.facebac.com/live/b78c603c23d5b60ef213?token=22fd832373133c8d64ff&secret=32de807e282e8d1728d05817a0890906&push_code=cf1dddb5&ckey=f4a05ce01b8410f2464f2044e632e56c
             * liveAfter :
             */

            private String m3u8Url;
            private String m3u8Url480;
            private String m3u8Url720;
            private String playUrl;
            private String playUrl480;
            private String playUrl720;
            private String pushUrl;
            private String liveAfter;

            public String getM3u8Url() {
                return m3u8Url;
            }

            public void setM3u8Url(String m3u8Url) {
                this.m3u8Url = m3u8Url;
            }

            public String getM3u8Url480() {
                return m3u8Url480;
            }

            public void setM3u8Url480(String m3u8Url480) {
                this.m3u8Url480 = m3u8Url480;
            }

            public String getM3u8Url720() {
                return m3u8Url720;
            }

            public void setM3u8Url720(String m3u8Url720) {
                this.m3u8Url720 = m3u8Url720;
            }

            public String getPlayUrl() {
                return playUrl;
            }

            public void setPlayUrl(String playUrl) {
                this.playUrl = playUrl;
            }

            public String getPlayUrl480() {
                return playUrl480;
            }

            public void setPlayUrl480(String playUrl480) {
                this.playUrl480 = playUrl480;
            }

            public String getPlayUrl720() {
                return playUrl720;
            }

            public void setPlayUrl720(String playUrl720) {
                this.playUrl720 = playUrl720;
            }

            public String getPushUrl() {
                return pushUrl;
            }

            public void setPushUrl(String pushUrl) {
                this.pushUrl = pushUrl;
            }

            public String getLiveAfter() {
                return liveAfter;
            }

            public void setLiveAfter(String liveAfter) {
                this.liveAfter = liveAfter;
            }
        }
    }
}
