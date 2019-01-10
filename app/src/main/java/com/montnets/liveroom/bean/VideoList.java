package com.montnets.liveroom.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 短视频列表
 */
public class VideoList implements Serializable {

    /**
     * code : 200
     * msg : 成功
     * obj : {"endRow":8,"firstPage":1,"hasNextPage":false,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":true,"lastPage":1,"list":[{"liveStatus":0,"mediaCover":"http://qvw.facebac.com/images/videoup_cover_img/581daa97b4723c20df9e/beoeglu7dlpgqczlpriu/thb_300.jpg","mediaId":"16310944463348301917","mediaName":"720","showTime":"2019-01-03 10:16:03","totalViews":16},{"liveStatus":0,"mediaCover":"http://qvw.facebac.com/images/videoup_cover_img/581daa97b4723c20df9e/fvctngwpry5xafm5axs9/thb_300.jpg","mediaId":"16310944463348301904","mediaName":"1","showTime":"2018-12-29 11:05:40","totalViews":30},{"liveStatus":0,"mediaCover":"http://qvw.facebac.com/images/videoup_cover_img/581daa97b4723c20df9e/1ltsoavtpn7uqe7cqrww/thb_300.jpg","mediaId":"16310944463348301899","mediaName":"mdVideo","showTime":"2018-12-28 17:38:24","totalViews":31},{"liveStatus":0,"mediaCover":"http://qvw.facebac.com/images/videoup_cover_img/581daa97b4723c20df9e/uo377idoecu95fxpxu4a/thb_300.jpg","mediaId":"16310944463348301829","mediaName":"mdVideo","showTime":"2018-12-23 11:29:01","totalViews":76},{"liveStatus":0,"mediaCover":"http://qvw.facebac.com/images/videoup_cover_img/581daa97b4723c20df9e/xu8qo11u6uvmw7uia5t9/thb_300.jpg","mediaId":"16310944463348301826","mediaName":"mdVideo","showTime":"2018-12-23 11:28:01","totalViews":76},{"liveStatus":0,"mediaCover":"http://qvw.facebac.com/images/videoup_cover_img/581daa97b4723c20df9e/tf9xc1snmcsbjqu6pxu2/thb_300.jpg","mediaId":"16310944463348301825","mediaName":"mdVideo","showTime":"2018-12-23 11:27:54","totalViews":76},{"liveStatus":0,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201812/262978557058232338.jpeg","mediaId":"16310944463348301824","mediaName":"1342134","showTime":"2018-12-23 11:27:45","totalViews":76},{"liveStatus":0,"mediaCover":"http://qvw.facebac.com/images/videoup_cover_img/581daa97b4723c20df9e/79vvqukczbdaaw11vl56/thb_300.jpg","mediaId":"25883798818259023","mediaName":"mdVideo","showTime":"2018-12-10 18:47:09","totalViews":0}],"navigatePages":8,"navigatepageNums":[1],"nextPage":0,"orderBy":"","pageNum":1,"pageSize":12,"pages":1,"prePage":0,"size":8,"startRow":1,"total":8}
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
         * endRow : 8
         * firstPage : 1
         * hasNextPage : false
         * hasPreviousPage : false
         * isFirstPage : true
         * isLastPage : true
         * lastPage : 1
         * list : [{"liveStatus":0,"mediaCover":"http://qvw.facebac.com/images/videoup_cover_img/581daa97b4723c20df9e/beoeglu7dlpgqczlpriu/thb_300.jpg","mediaId":"16310944463348301917","mediaName":"720","showTime":"2019-01-03 10:16:03","totalViews":16},{"liveStatus":0,"mediaCover":"http://qvw.facebac.com/images/videoup_cover_img/581daa97b4723c20df9e/fvctngwpry5xafm5axs9/thb_300.jpg","mediaId":"16310944463348301904","mediaName":"1","showTime":"2018-12-29 11:05:40","totalViews":30},{"liveStatus":0,"mediaCover":"http://qvw.facebac.com/images/videoup_cover_img/581daa97b4723c20df9e/1ltsoavtpn7uqe7cqrww/thb_300.jpg","mediaId":"16310944463348301899","mediaName":"mdVideo","showTime":"2018-12-28 17:38:24","totalViews":31},{"liveStatus":0,"mediaCover":"http://qvw.facebac.com/images/videoup_cover_img/581daa97b4723c20df9e/uo377idoecu95fxpxu4a/thb_300.jpg","mediaId":"16310944463348301829","mediaName":"mdVideo","showTime":"2018-12-23 11:29:01","totalViews":76},{"liveStatus":0,"mediaCover":"http://qvw.facebac.com/images/videoup_cover_img/581daa97b4723c20df9e/xu8qo11u6uvmw7uia5t9/thb_300.jpg","mediaId":"16310944463348301826","mediaName":"mdVideo","showTime":"2018-12-23 11:28:01","totalViews":76},{"liveStatus":0,"mediaCover":"http://qvw.facebac.com/images/videoup_cover_img/581daa97b4723c20df9e/tf9xc1snmcsbjqu6pxu2/thb_300.jpg","mediaId":"16310944463348301825","mediaName":"mdVideo","showTime":"2018-12-23 11:27:54","totalViews":76},{"liveStatus":0,"mediaCover":"http://nx.facebac.com/java/farmbank-server/upload/201812/262978557058232338.jpeg","mediaId":"16310944463348301824","mediaName":"1342134","showTime":"2018-12-23 11:27:45","totalViews":76},{"liveStatus":0,"mediaCover":"http://qvw.facebac.com/images/videoup_cover_img/581daa97b4723c20df9e/79vvqukczbdaaw11vl56/thb_300.jpg","mediaId":"25883798818259023","mediaName":"mdVideo","showTime":"2018-12-10 18:47:09","totalViews":0}]
         * navigatePages : 8
         * navigatepageNums : [1]
         * nextPage : 0
         * orderBy :
         * pageNum : 1
         * pageSize : 12
         * pages : 1
         * prePage : 0
         * size : 8
         * startRow : 1
         * total : 8
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
             * mediaCover : http://qvw.facebac.com/images/videoup_cover_img/581daa97b4723c20df9e/beoeglu7dlpgqczlpriu/thb_300.jpg
             * mediaId : 16310944463348301917
             * mediaName : 720
             * showTime : 2019-01-03 10:16:03
             * totalViews : 16
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
