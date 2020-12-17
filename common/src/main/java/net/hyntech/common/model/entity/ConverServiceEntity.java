package net.hyntech.common.model.entity;


import net.hyntech.baselib.http.BaseResponse;

import java.util.List;

public class ConverServiceEntity extends BaseResponse<ConverServiceEntity> {


    /**
     * atServiceShopList : [{"addr":"开封市尉氏县","createId":"2019FYO6dbhfni","createIdFlag":0,"createName":"李鹏","createTime":"2020-06-27 11:44:34","delFlag":1,"distance":0,"keyword":"","lat":34.3741294,"lng":114.0133395,"loginUserId":"","name":"","orgId":"2018LENoOyAYmq","orgName":"","phone":"13837865119","relevantPic":"http://oss-public.hyntech.net/appUpload/20200627/2018LENoOyAYmq/c0501b2dbc464ff0be809c43e68b68e1.JPEG","remark":"","serviceShopId":"2020FaLshJWvTh","shopName":"李鹏车行","shopType":"销售门店,维修站,充电站","state":1,"updateId":"2019FYO6dbhfni","updateIdFlag":0,"updateTime":"2020-07-16 15:25:17"},{"addr":"河南省开封市尉氏县X023","createId":"2019HLMiZZ5AGV","createIdFlag":0,"createName":"李昭运","createTime":"2019-09-02 05:57:21","delFlag":1,"distance":7.78,"keyword":"","lat":34.3711052,"lng":113.9287371,"loginUserId":"","name":"","orgId":"2018LENoOyAYmq","orgName":"","phone":"15937843661","relevantPic":"http://oss-public.hyntech.net/appUpload/20190902/2018LENoOyAYmq/1a018a9e3c2e4de0aa3c59e9a6216907.jpg,http://oss-public.hyntech.net/appUpload/20190902/2018LENoOyAYmq/3b146ca6bdf542dbb5f50a952ab865a9.jpg,http://oss-public.hyntech.net/appUpload/20190902/2018LENoOyAYmq/a6b684d60956489b88d53554a60b75de.jpg","remark":"","serviceShopId":"2019IBF5VXvdvN","shopName":"昭运车业","shopType":"销售门店,维修站,充电站","state":1,"updateId":"2019HLMiZZ5AGV","updateIdFlag":0,"updateTime":"2020-05-19 06:31:07"}]
     * page : {"firstPage":true,"lastPage":true,"list":[],"nextPage":1,"pageNo":1,"pageSize":10,"prePage":1,"totalCount":2,"totalPage":1}
     */

    private PageBean page;
    private List<AtServiceShopListBean> atServiceShopList;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<AtServiceShopListBean> getAtServiceShopList() {
        return atServiceShopList;
    }

    public void setAtServiceShopList(List<AtServiceShopListBean> atServiceShopList) {
        this.atServiceShopList = atServiceShopList;
    }

    public static class PageBean {
        /**
         * firstPage : true
         * lastPage : true
         * list : []
         * nextPage : 1
         * pageNo : 1
         * pageSize : 10
         * prePage : 1
         * totalCount : 2
         * totalPage : 1
         */

        private boolean firstPage;
        private boolean lastPage;
        private int nextPage;
        private int pageNo;
        private int pageSize;
        private int prePage;
        private int totalCount;
        private int totalPage;
        private List<?> list;

        public boolean isFirstPage() {
            return firstPage;
        }

        public void setFirstPage(boolean firstPage) {
            this.firstPage = firstPage;
        }

        public boolean isLastPage() {
            return lastPage;
        }

        public void setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<?> getList() {
            return list;
        }

        public void setList(List<?> list) {
            this.list = list;
        }
    }

    public static class AtServiceShopListBean {
        /**
         * addr : 开封市尉氏县
         * createId : 2019FYO6dbhfni
         * createIdFlag : 0
         * createName : 李鹏
         * createTime : 2020-06-27 11:44:34
         * delFlag : 1
         * distance : 0
         * keyword :
         * lat : 34.3741294
         * lng : 114.0133395
         * loginUserId :
         * name :
         * orgId : 2018LENoOyAYmq
         * orgName :
         * phone : 13837865119
         * relevantPic : http://oss-public.hyntech.net/appUpload/20200627/2018LENoOyAYmq/c0501b2dbc464ff0be809c43e68b68e1.JPEG
         * remark :
         * serviceShopId : 2020FaLshJWvTh
         * shopName : 李鹏车行
         * shopType : 销售门店,维修站,充电站
         * state : 1
         * updateId : 2019FYO6dbhfni
         * updateIdFlag : 0
         * updateTime : 2020-07-16 15:25:17
         */

        private String addr;
        private String createId;
        private int createIdFlag;
        private String createName;
        private String createTime;
        private int delFlag;
        private double distance;
        private String keyword;
        private double lat;
        private double lng;
        private String loginUserId;
        private String name;
        private String orgId;
        private String orgName;
        private String phone;
        private String relevantPic;
        private String remark;
        private String serviceShopId;
        private String shopName;
        private String shopType;
        private int state;
        private String updateId;
        private int updateIdFlag;
        private String updateTime;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getCreateId() {
            return createId;
        }

        public void setCreateId(String createId) {
            this.createId = createId;
        }

        public int getCreateIdFlag() {
            return createIdFlag;
        }

        public void setCreateIdFlag(int createIdFlag) {
            this.createIdFlag = createIdFlag;
        }

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public String getLoginUserId() {
            return loginUserId;
        }

        public void setLoginUserId(String loginUserId) {
            this.loginUserId = loginUserId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRelevantPic() {
            return relevantPic;
        }

        public void setRelevantPic(String relevantPic) {
            this.relevantPic = relevantPic;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getServiceShopId() {
            return serviceShopId;
        }

        public void setServiceShopId(String serviceShopId) {
            this.serviceShopId = serviceShopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopType() {
            return shopType;
        }

        public void setShopType(String shopType) {
            this.shopType = shopType;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getUpdateId() {
            return updateId;
        }

        public void setUpdateId(String updateId) {
            this.updateId = updateId;
        }

        public int getUpdateIdFlag() {
            return updateIdFlag;
        }

        public void setUpdateIdFlag(int updateIdFlag) {
            this.updateIdFlag = updateIdFlag;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
