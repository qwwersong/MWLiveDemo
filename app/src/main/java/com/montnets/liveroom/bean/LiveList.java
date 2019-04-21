package com.montnets.liveroom.bean;

import java.io.Serializable;
import java.util.List;

public class LiveList implements Serializable {
    /**
     * code : 200
     * msg : 成功
     * obj : {"endRow":12,"firstPage":1,"hasNextPage":true,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":false,"lastPage":7,"list":[{"liveStatus":0,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/265897636668649965.jpeg","mediaId":"97972816833476211","mediaName":"222222","showTime":"2019-01-24 00:09:09","totalViews":4},{"liveStatus":0,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/265897636668649958.jpeg","mediaId":"97972816833476210","mediaName":"1111111111","showTime":"2019-01-09 00:00:00","totalViews":6},{"liveStatus":2,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/265847550987219024.jpeg","mediaId":"97972816833472483","mediaName":"hec1","showTime":"2019-01-05 14:59:59","totalViews":5},{"liveStatus":2,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/265847550987218993.jpeg","mediaId":"97972816833472450","mediaName":"hec123","showTime":"2019-01-05 14:45:45","totalViews":4},{"liveStatus":2,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/265847550987218969.jpeg","mediaId":"97972816833472411","mediaName":"hec测试自动转码开启","showTime":"2019-01-05 14:35:35","totalViews":38},{"liveStatus":2,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/265770268922753149.jpeg","mediaId":"97972816833472307","mediaName":"测试清晰度切换","showTime":"2019-01-31 10:38:38","totalViews":50},{"liveStatus":2,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/265770268922753116.jpeg","mediaId":"97972816833472020","mediaName":"123","showTime":"2019-01-05 10:10:10","totalViews":38},{"liveStatus":2,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/265770268922753041.jpeg","mediaId":"97972816833471972","mediaName":"hec专属名单2","showTime":"2019-01-05 09:28:28","totalViews":6},{"liveStatus":2,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/265770268922753035.jpeg","mediaId":"97972816833471964","mediaName":"hec测试啊","showTime":"2019-01-05 09:24:24","totalViews":6},{"liveStatus":2,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/265770268922753030.jpeg","mediaId":"97972816833471951","mediaName":"hec专属名单导入","showTime":"2019-01-05 09:21:21","totalViews":17},{"liveStatus":2,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/265523721240850465.jpeg","mediaId":"97972816833469207","mediaName":"程辉_双画面直播","showTime":"2019-01-04 17:17:17","totalViews":56},{"liveStatus":2,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/263331930383071320.jpeg","mediaId":"97972816833468911","mediaName":"程辉文档直播","showTime":"2019-01-04 15:55:55","totalViews":14}],"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6,7],"nextPage":2,"orderBy":"","pageNum":1,"pageSize":12,"pages":7,"prePage":0,"size":12,"startRow":1,"total":78}
     */

    private String code;
    private String msg;
    private ObjBean data;

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

    public ObjBean getData() {
        return data;
    }

    public void setData(ObjBean data) {
        this.data = data;
    }

    public static class ObjBean {
        /**
         * endRow : 12
         * firstPage : 1
         * hasNextPage : true
         * hasPreviousPage : false
         * isFirstPage : true
         * isLastPage : false
         * lastPage : 7
         * list : [{"liveStatus":0,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/265897636668649965.jpeg","mediaId":"97972816833476211","mediaName":"222222","showTime":"2019-01-24 00:09:09","totalViews":4},{"liveStatus":0,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/265897636668649958.jpeg","mediaId":"97972816833476210","mediaName":"1111111111","showTime":"2019-01-09 00:00:00","totalViews":6},{"liveStatus":2,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/265847550987219024.jpeg","mediaId":"97972816833472483","mediaName":"hec1","showTime":"2019-01-05 14:59:59","totalViews":5},{"liveStatus":2,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/265847550987218993.jpeg","mediaId":"97972816833472450","mediaName":"hec123","showTime":"2019-01-05 14:45:45","totalViews":4},{"liveStatus":2,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/265847550987218969.jpeg","mediaId":"97972816833472411","mediaName":"hec测试自动转码开启","showTime":"2019-01-05 14:35:35","totalViews":38},{"liveStatus":2,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/265770268922753149.jpeg","mediaId":"97972816833472307","mediaName":"测试清晰度切换","showTime":"2019-01-31 10:38:38","totalViews":50},{"liveStatus":2,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/265770268922753116.jpeg","mediaId":"97972816833472020","mediaName":"123","showTime":"2019-01-05 10:10:10","totalViews":38},{"liveStatus":2,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/265770268922753041.jpeg","mediaId":"97972816833471972","mediaName":"hec专属名单2","showTime":"2019-01-05 09:28:28","totalViews":6},{"liveStatus":2,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/265770268922753035.jpeg","mediaId":"97972816833471964","mediaName":"hec测试啊","showTime":"2019-01-05 09:24:24","totalViews":6},{"liveStatus":2,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/265770268922753030.jpeg","mediaId":"97972816833471951","mediaName":"hec专属名单导入","showTime":"2019-01-05 09:21:21","totalViews":17},{"liveStatus":2,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/265523721240850465.jpeg","mediaId":"97972816833469207","mediaName":"程辉_双画面直播","showTime":"2019-01-04 17:17:17","totalViews":56},{"liveStatus":2,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201901/263331930383071320.jpeg","mediaId":"97972816833468911","mediaName":"程辉文档直播","showTime":"2019-01-04 15:55:55","totalViews":14}]
         * navigatePages : 8
         * navigatepageNums : [1,2,3,4,5,6,7]
         * nextPage : 2
         * orderBy :
         * pageNum : 1
         * pageSize : 12
         * pages : 7
         * prePage : 0
         * size : 12
         * startRow : 1
         * total : 78
         */

        private int endRow;
        private int firstPage;
        private boolean hasNextPage;
        private boolean hasPreviousPage;
        private boolean isFirstPage;
        private boolean isLastPage;
        private int lastPage;
        private int navigatePages;
        private int nextPage;
        private String orderBy;
        private int pageNum;
        private int pageSize;
        private int pages;
        private int prePage;
        private int size;
        private int startRow;
        private int total;
        private List<ListBean> list;
        private List<Integer> navigatepageNums;

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public int getFirstPage() {
            return firstPage;
        }

        public void setFirstPage(int firstPage) {
            this.firstPage = firstPage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public boolean isHasPreviousPage() {
            return hasPreviousPage;
        }

        public void setHasPreviousPage(boolean hasPreviousPage) {
            this.hasPreviousPage = hasPreviousPage;
        }

        public boolean isIsFirstPage() {
            return isFirstPage;
        }

        public void setIsFirstPage(boolean isFirstPage) {
            this.isFirstPage = isFirstPage;
        }

        public boolean isIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public int getLastPage() {
            return lastPage;
        }

        public void setLastPage(int lastPage) {
            this.lastPage = lastPage;
        }

        public int getNavigatePages() {
            return navigatePages;
        }

        public void setNavigatePages(int navigatePages) {
            this.navigatePages = navigatePages;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<Integer> getNavigatepageNums() {
            return navigatepageNums;
        }

        public void setNavigatepageNums(List<Integer> navigatepageNums) {
            this.navigatepageNums = navigatepageNums;
        }

        public static class ListBean {
            /**
             * liveStatus : 0
             * mediaCover : http://nx.facebac.com/java/farmbank-server/upload/201901/265897636668649965.jpeg
             * mediaId : 97972816833476211
             * mediaName : 222222
             * showTime : 2019-01-24 00:09:09
             * totalViews : 4
             */

            private int liveStatus;
            private String mediaCover;
            private String mediaId;
            private String mediaName;
            private String showTime;
            private int totalViews;

            public int getLiveStatus() {
                return liveStatus;
            }

            public void setLiveStatus(int liveStatus) {
                this.liveStatus = liveStatus;
            }

            public String getMediaCover() {
                return mediaCover;
            }

            public void setMediaCover(String mediaCover) {
                this.mediaCover = mediaCover;
            }

            public String getMediaId() {
                return mediaId;
            }

            public void setMediaId(String mediaId) {
                this.mediaId = mediaId;
            }

            public String getMediaName() {
                return mediaName;
            }

            public void setMediaName(String mediaName) {
                this.mediaName = mediaName;
            }

            public String getShowTime() {
                return showTime;
            }

            public void setShowTime(String showTime) {
                this.showTime = showTime;
            }

            public int getTotalViews() {
                return totalViews;
            }

            public void setTotalViews(int totalViews) {
                this.totalViews = totalViews;
            }
        }
    }
}
