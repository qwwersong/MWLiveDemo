package com.montnets.liveroom.bean;

import java.io.Serializable;

public class VideoDetail implements Serializable {
    /**
     * msg : 成功
     * code : 200
     * obj : {"del":0,"updateTime":"1970-01-01 08:00:00","userId":"1","videoSource":{"duration":0,"playUrl720":"www","size":0,"playUrl480":"www","playUrl":"www"},"createTime":"2018-11-21 09:00:00","videoName":"hc测试标签4","videoCover":"","totalViews":0,"id":"4","videoText":"","labelName":"标签3，标签1","thumbsUp":0,"videoSourceId":"1","videoStates":2}
     */
    private String msg;
    private String code;
    private ObjEntity obj;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setObj(ObjEntity obj) {
        this.obj = obj;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public ObjEntity getObj() {
        return obj;
    }

    public class ObjEntity {
        /**
         * del : 0
         * updateTime : 1970-01-01 08:00:00
         * userId : 1
         * videoSource : {"duration":0,"playUrl720":"www","size":0,"playUrl480":"www","playUrl":"www"}
         * createTime : 2018-11-21 09:00:00
         * videoName : hc测试标签4
         * videoCover :
         * totalViews : 0
         * id : 4
         * videoText :
         * labelName : 标签3，标签1
         * thumbsUp : 0
         * videoSourceId : 1
         * videoStates : 2
         */
        private int del;
        private String updateTime;
        private String userId;
        private VideoSourceEntity videoSource;
        private String createTime;
        private String videoName;
        private String videoCover;
        private int totalViews;
        private String id;
        private String videoText;
        private String labelName;
        private int thumbsUp;
        private String videoSourceId;
        private int videoStates;

        public void setDel(int del) {
            this.del = del;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setVideoSource(VideoSourceEntity videoSource) {
            this.videoSource = videoSource;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }

        public void setVideoCover(String videoCover) {
            this.videoCover = videoCover;
        }

        public void setTotalViews(int totalViews) {
            this.totalViews = totalViews;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setVideoText(String videoText) {
            this.videoText = videoText;
        }

        public void setLabelName(String labelName) {
            this.labelName = labelName;
        }

        public void setThumbsUp(int thumbsUp) {
            this.thumbsUp = thumbsUp;
        }

        public void setVideoSourceId(String videoSourceId) {
            this.videoSourceId = videoSourceId;
        }

        public void setVideoStates(int videoStates) {
            this.videoStates = videoStates;
        }

        public int getDel() {
            return del;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public String getUserId() {
            return userId;
        }

        public VideoSourceEntity getVideoSource() {
            return videoSource;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getVideoName() {
            return videoName;
        }

        public String getVideoCover() {
            return videoCover;
        }

        public int getTotalViews() {
            return totalViews;
        }

        public String getId() {
            return id;
        }

        public String getVideoText() {
            return videoText;
        }

        public String getLabelName() {
            return labelName;
        }

        public int getThumbsUp() {
            return thumbsUp;
        }

        public String getVideoSourceId() {
            return videoSourceId;
        }

        public int getVideoStates() {
            return videoStates;
        }

        public class VideoSourceEntity {
            /**
             * duration : 0
             * playUrl720 : www
             * size : 0
             * playUrl480 : www
             * playUrl : www
             */
            private float duration;
            private String playUrl720;
            private float size;
            private String playUrl480;
            private String playUrl;

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public void setPlayUrl720(String playUrl720) {
                this.playUrl720 = playUrl720;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public void setPlayUrl480(String playUrl480) {
                this.playUrl480 = playUrl480;
            }

            public void setPlayUrl(String playUrl) {
                this.playUrl = playUrl;
            }

            public float getDuration() {
                return duration;
            }

            public String getPlayUrl720() {
                return playUrl720;
            }

            public float getSize() {
                return size;
            }

            public String getPlayUrl480() {
                return playUrl480;
            }

            public String getPlayUrl() {
                return playUrl;
            }
        }
    }
}
