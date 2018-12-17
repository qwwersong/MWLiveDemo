package com.montnets.liveroom.bean;

import java.io.Serializable;
import java.util.List;

public class LiveList implements Serializable {

    /**
     * msg : 成功
     * code : 200
     * obj : [{"planTime":"2018-11-22 09:22:22","liveName":"h1","id":"169956480122917046","liveCover":"","liveStatus":1},{"planTime":"1970-01-01 08:00:00","liveName":"hec","id":"169956480122919108","liveCover":"","liveStatus":0},{"planTime":"2018-11-22 09:22:22","liveName":"何创测试","id":"169956480122918433","liveCover":"","liveStatus":0},{"planTime":"2018-11-22 09:22:22","liveName":"何创测试","id":"169956480122917238","liveCover":"","liveStatus":0},{"planTime":"2018-11-22 09:22:22","liveName":"hec2223","id":"169956480122917182","liveCover":"","liveStatus":0},{"planTime":"2018-11-22 09:22:22","liveName":"h1","id":"169956480122917102","liveCover":"","liveStatus":0},{"planTime":"2018-11-22 09:22:22","liveName":"h1","id":"169956480122917101","liveCover":"","liveStatus":0},{"planTime":"1970-01-01 08:00:00","liveName":"2","id":"169956480122915480","liveCover":"2","liveStatus":0},{"planTime":"2018-11-20 08:00:00","liveName":"测试直播编辑","id":"169956480122915479","liveCover":"http://pic188.nipic.com/file/20181030/20020614_151903767037_2.jpg","liveStatus":0},{"planTime":"1970-01-01 08:00:00","liveName":"测试直播编辑","id":"169956480122915478","liveCover":"http://pic188.nipic.com/file/20181030/20020614_151903767037_2.jpg","liveStatus":0},{"planTime":"2018-12-25 08:00:00","liveName":"来一场说开就开的直播","id":"169956480122915474","liveCover":"http://nx.facebac.com/java/farmbank-server/upload/201811/249844542256197634.png","liveStatus":0},{"planTime":"1970-01-01 08:00:00","liveName":"风景好","id":"169956480122915471","liveCover":"","liveStatus":0},{"planTime":"1970-01-01 08:00:00","liveName":"","id":"169956480122915492","liveCover":"","liveStatus":0},{"planTime":"2018-11-22 09:22:22","liveName":"何创测试","id":"169956480122917218","liveCover":"","liveStatus":2}]
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
         * planTime : 2018-11-22 09:22:22
         * liveName : h1
         * id : 169956480122917046
         * liveCover :
         * liveStatus : 1
         */
        private String planTime;
        private String liveName;
        private String id;
        private String liveCover;
        private int liveStatus;

        public void setPlanTime(String planTime) {
            this.planTime = planTime;
        }

        public void setLiveName(String liveName) {
            this.liveName = liveName;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setLiveCover(String liveCover) {
            this.liveCover = liveCover;
        }

        public void setLiveStatus(int liveStatus) {
            this.liveStatus = liveStatus;
        }

        public String getPlanTime() {
            return planTime;
        }

        public String getLiveName() {
            return liveName;
        }

        public String getId() {
            return id;
        }

        public String getLiveCover() {
            return liveCover;
        }

        public int getLiveStatus() {
            return liveStatus;
        }
    }
}
