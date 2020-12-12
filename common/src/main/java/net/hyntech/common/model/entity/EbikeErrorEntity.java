package net.hyntech.common.model.entity;


import net.hyntech.baselib.http.BaseResponse;

import java.io.Serializable;
import java.util.List;

public class EbikeErrorEntity extends BaseResponse<EbikeErrorEntity> {


    /**
     * alarmExceptionLogList : [{"addr":"开封市尉氏县水坡镇苏桥村西头村口","alarmLogId":"3ee1838e3ccf4c21a12c6aca9c7938c8","collectorId":"201812201017217","collectorNo":"201812201017217","createTime":"2020-12-02 16:27:14","ebikeId":"17551c388e1a47539bf654a416530f63","ebikeNo":"WS113651","idNo":"410223196712136512","lat":34.530864,"lng":114.27095,"locatorId":"a80eabba3f834d28bf154d0aa5de1f40","locatorNo":"35831367","loginUserId":"","name":"郭小路","orgId":"2018LENoOyAYmq","orgName":"","phone":"15664284736","remark":"","state":0,"updateTime":"2020-12-02 16:27:14","userId":"a58c3eba6c8f4ab58935345bfeeda009"},{"addr":"开封市尉氏县水坡镇齐岗村西头邮政便民服务旁边","alarmLogId":"8be952bb1d984d9c9ddee955ccb0916d","collectorId":"201812201017131","collectorNo":"201812201017131","createTime":"2020-12-02 16:22:22","ebikeId":"17551c388e1a47539bf654a416530f63","ebikeNo":"WS113651","idNo":"410223196712136512","lat":34.537885,"lng":114.271627,"locatorId":"a80eabba3f834d28bf154d0aa5de1f40","locatorNo":"35831367","loginUserId":"","name":"郭小路","orgId":"2018LENoOyAYmq","orgName":"","phone":"15664284736","remark":"","state":0,"updateTime":"2020-12-02 16:22:22","userId":"a58c3eba6c8f4ab58935345bfeeda009"},{"addr":"开封市尉氏县水坡镇齐岗村西头邮政便民服务旁边","alarmLogId":"5697f80bda974138a90ea96b7a2d3d40","collectorId":"201812201017131","collectorNo":"201812201017131","createTime":"2020-12-02 16:16:27","ebikeId":"17551c388e1a47539bf654a416530f63","ebikeNo":"WS113651","idNo":"410223196712136512","lat":34.537885,"lng":114.271627,"locatorId":"a80eabba3f834d28bf154d0aa5de1f40","locatorNo":"35831367","loginUserId":"","name":"郭小路","orgId":"2018LENoOyAYmq","orgName":"","phone":"15664284736","remark":"","state":0,"updateTime":"2020-12-02 16:16:27","userId":"a58c3eba6c8f4ab58935345bfeeda009"}]
     * page : {"firstPage":true,"lastPage":true,"list":[],"nextPage":1,"pageNo":1,"pageSize":10,"prePage":1,"totalCount":3,"totalPage":1}
     */

    private PageBean page;
    private List<AlarmExceptionLogListBean> alarmExceptionLogList;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<AlarmExceptionLogListBean> getAlarmExceptionLogList() {
        return alarmExceptionLogList;
    }

    public void setAlarmExceptionLogList(List<AlarmExceptionLogListBean> alarmExceptionLogList) {
        this.alarmExceptionLogList = alarmExceptionLogList;
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
         * totalCount : 3
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

    public static class AlarmExceptionLogListBean implements Serializable {
        /**
         * addr : 开封市尉氏县水坡镇苏桥村西头村口
         * alarmLogId : 3ee1838e3ccf4c21a12c6aca9c7938c8
         * collectorId : 201812201017217
         * collectorNo : 201812201017217
         * createTime : 2020-12-02 16:27:14
         * ebikeId : 17551c388e1a47539bf654a416530f63
         * ebikeNo : WS113651
         * idNo : 410223196712136512
         * lat : 34.530864
         * lng : 114.27095
         * locatorId : a80eabba3f834d28bf154d0aa5de1f40
         * locatorNo : 35831367
         * loginUserId :
         * name : 郭小路
         * orgId : 2018LENoOyAYmq
         * orgName :
         * phone : 15664284736
         * remark :
         * state : 0
         * updateTime : 2020-12-02 16:27:14
         * userId : a58c3eba6c8f4ab58935345bfeeda009
         */

        private String addr;
        private String alarmLogId;
        private String collectorId;
        private String collectorNo;
        private String createTime;
        private String ebikeId;
        private String ebikeNo;
        private String idNo;
        private double lat;
        private double lng;
        private String locatorId;
        private String locatorNo;
        private String loginUserId;
        private String name;
        private String orgId;
        private String orgName;
        private String phone;
        private String remark;
        private int state;
        private String updateTime;
        private String userId;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getAlarmLogId() {
            return alarmLogId;
        }

        public void setAlarmLogId(String alarmLogId) {
            this.alarmLogId = alarmLogId;
        }

        public String getCollectorId() {
            return collectorId;
        }

        public void setCollectorId(String collectorId) {
            this.collectorId = collectorId;
        }

        public String getCollectorNo() {
            return collectorNo;
        }

        public void setCollectorNo(String collectorNo) {
            this.collectorNo = collectorNo;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getEbikeId() {
            return ebikeId;
        }

        public void setEbikeId(String ebikeId) {
            this.ebikeId = ebikeId;
        }

        public String getEbikeNo() {
            return ebikeNo;
        }

        public void setEbikeNo(String ebikeNo) {
            this.ebikeNo = ebikeNo;
        }

        public String getIdNo() {
            return idNo;
        }

        public void setIdNo(String idNo) {
            this.idNo = idNo;
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

        public String getLocatorId() {
            return locatorId;
        }

        public void setLocatorId(String locatorId) {
            this.locatorId = locatorId;
        }

        public String getLocatorNo() {
            return locatorNo;
        }

        public void setLocatorNo(String locatorNo) {
            this.locatorNo = locatorNo;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
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
    }
}
