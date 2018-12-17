package com.montnets.liveroom.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 短视频列表
 */
public class VideoList implements Serializable {

    /**
     * msg : 成功
     * code : 200
     * obj : [{"createTime":"2018-11-23 08:00:00","videoName":"hc测试标签5","videoCover":"","totalViews":0,"id":"5"},{"createTime":"2018-11-22 08:00:00","videoName":"hc测试标签","videoCover":"","totalViews":0,"id":"2"},{"createTime":"2018-11-21 09:00:00","videoName":"hc测试标签4","videoCover":"","totalViews":0,"id":"4"},{"createTime":"2018-11-21 07:00:00","videoName":"hc测试标签3","videoCover":"","totalViews":0,"id":"3"},{"createTime":"1970-01-01 08:00:00","videoName":"测试短视频","videoCover":"www.baidu.com","totalViews":200,"id":"1"}]
     */
    private String msg;
    private String code;
    private List<ObjEntity> obj;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setObj(List<ObjEntity> obj) {
        this.obj = obj;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public List<ObjEntity> getObj() {
        return obj;
    }

    public class ObjEntity {
        /**
         * createTime : 2018-11-23 08:00:00
         * videoName : hc测试标签5
         * videoCover :
         * totalViews : 0
         * id : 5
         */
        private String createTime;
        private String videoName;
        private String videoCover;
        private int totalViews;
        private String id;

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
    }
}
