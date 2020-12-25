package net.hyntech.common.model.entity;

import net.hyntech.baselib.http.BaseResponse;

import java.util.List;

public class CollectorListEntity extends BaseResponse<CollectorListEntity> {


    /**
     * atCollectorList : [{"addr":"河南省开封市尉氏县后冯村附近43米","collectorId":"138510B004E0022","collectorIp":"122.97.222.158","collectorNo":"138510B004E0022","collectorNo110":"","collectorPort":"7000","collectorUserName":"","collectorUuid":"47ef0a83a31b433bbf113c798b1030d3","createId":"2018LEK0sABzcu","createIdFlag":1,"createTime":"2020-12-17 17:13:29","installFlag":1,"keyword":"","lat":34.413003,"lng":114.221318,"loginUserId":"","offlineState":1,"orgId":"2018LENoOyAYmq","orgName":"尉氏县","remark":"","reportTime":"2020-12-25 10:30:38","reportTimeBegin":"","reportTimeEnd":"","state":1,"traffic":5313,"updateId":"2018LEK0sABzcu","updateIdFlag":1,"updateTime":"2020-12-25 10:30:38"},{"addr":"河南省开封市尉氏县创维旗舰店西南58米","collectorId":"138510E003D0022","collectorIp":"112.96.162.254","collectorNo":"138510E003D0022","collectorNo110":"","collectorPort":"7000","collectorUserName":"","collectorUuid":"afed4e92bb9541dcbc3e8ba126ffd19e","createId":"2018LEK0sABzcu","createIdFlag":1,"createTime":"2020-12-17 16:46:20","installFlag":1,"keyword":"","lat":34.413468,"lng":114.217267,"loginUserId":"","offlineState":0,"orgId":"2018LENoOyAYmq","orgName":"尉氏县","remark":"","reportTime":"2020-12-23 15:45:10","reportTimeBegin":"","reportTimeEnd":"","state":1,"traffic":1101,"updateId":"2018LEK0sABzcu","updateIdFlag":1,"updateTime":"2020-12-25 10:31:28"}]
     * page : {"firstPage":true,"lastPage":false,"list":[],"nextPage":2,"pageNo":1,"pageSize":2,"prePage":1,"totalCount":828,"totalPage":414}
     */

    private PageBean page;
    private List<AtCollectorListBean> atCollectorList;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<AtCollectorListBean> getAtCollectorList() {
        return atCollectorList;
    }

    public void setAtCollectorList(List<AtCollectorListBean> atCollectorList) {
        this.atCollectorList = atCollectorList;
    }

    public static class PageBean {
        /**
         * firstPage : true
         * lastPage : false
         * list : []
         * nextPage : 2
         * pageNo : 1
         * pageSize : 2
         * prePage : 1
         * totalCount : 828
         * totalPage : 414
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

    public static class AtCollectorListBean {
        /**
         * addr : 河南省开封市尉氏县后冯村附近43米
         * collectorId : 138510B004E0022
         * collectorIp : 122.97.222.158
         * collectorNo : 138510B004E0022
         * collectorNo110 :
         * collectorPort : 7000
         * collectorUserName :
         * collectorUuid : 47ef0a83a31b433bbf113c798b1030d3
         * createId : 2018LEK0sABzcu
         * createIdFlag : 1
         * createTime : 2020-12-17 17:13:29
         * installFlag : 1
         * keyword :
         * lat : 34.413003
         * lng : 114.221318
         * loginUserId :
         * offlineState : 1
         * orgId : 2018LENoOyAYmq
         * orgName : 尉氏县
         * remark :
         * reportTime : 2020-12-25 10:30:38
         * reportTimeBegin :
         * reportTimeEnd :
         * state : 1
         * traffic : 5313
         * updateId : 2018LEK0sABzcu
         * updateIdFlag : 1
         * updateTime : 2020-12-25 10:30:38
         */

        private String addr;
        private String collectorId;
        private String collectorIp;
        private String collectorNo;
        private String collectorNo110;
        private String collectorPort;
        private String collectorUserName;
        private String collectorUuid;
        private String createId;
        private int createIdFlag;
        private String createTime;
        private int installFlag;
        private String keyword;
        private double lat;
        private double lng;
        private String loginUserId;
        private int offlineState;
        private String orgId;
        private String orgName;
        private String remark;
        private String reportTime;
        private String reportTimeBegin;
        private String reportTimeEnd;
        private int state;
        private int traffic;
        private String updateId;
        private int updateIdFlag;
        private String updateTime;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getCollectorId() {
            return collectorId;
        }

        public void setCollectorId(String collectorId) {
            this.collectorId = collectorId;
        }

        public String getCollectorIp() {
            return collectorIp;
        }

        public void setCollectorIp(String collectorIp) {
            this.collectorIp = collectorIp;
        }

        public String getCollectorNo() {
            return collectorNo;
        }

        public void setCollectorNo(String collectorNo) {
            this.collectorNo = collectorNo;
        }

        public String getCollectorNo110() {
            return collectorNo110;
        }

        public void setCollectorNo110(String collectorNo110) {
            this.collectorNo110 = collectorNo110;
        }

        public String getCollectorPort() {
            return collectorPort;
        }

        public void setCollectorPort(String collectorPort) {
            this.collectorPort = collectorPort;
        }

        public String getCollectorUserName() {
            return collectorUserName;
        }

        public void setCollectorUserName(String collectorUserName) {
            this.collectorUserName = collectorUserName;
        }

        public String getCollectorUuid() {
            return collectorUuid;
        }

        public void setCollectorUuid(String collectorUuid) {
            this.collectorUuid = collectorUuid;
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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getInstallFlag() {
            return installFlag;
        }

        public void setInstallFlag(int installFlag) {
            this.installFlag = installFlag;
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

        public int getOfflineState() {
            return offlineState;
        }

        public void setOfflineState(int offlineState) {
            this.offlineState = offlineState;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getReportTime() {
            return reportTime;
        }

        public void setReportTime(String reportTime) {
            this.reportTime = reportTime;
        }

        public String getReportTimeBegin() {
            return reportTimeBegin;
        }

        public void setReportTimeBegin(String reportTimeBegin) {
            this.reportTimeBegin = reportTimeBegin;
        }

        public String getReportTimeEnd() {
            return reportTimeEnd;
        }

        public void setReportTimeEnd(String reportTimeEnd) {
            this.reportTimeEnd = reportTimeEnd;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getTraffic() {
            return traffic;
        }

        public void setTraffic(int traffic) {
            this.traffic = traffic;
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
