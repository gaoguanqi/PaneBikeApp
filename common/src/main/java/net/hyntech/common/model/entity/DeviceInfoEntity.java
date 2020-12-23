package net.hyntech.common.model.entity;

import net.hyntech.baselib.http.BaseResponse;

import java.util.List;

public class DeviceInfoEntity extends BaseResponse<DeviceInfoEntity> {

    private List<AtCollectorListBean> atCollectorList;

    public List<AtCollectorListBean> getAtCollectorList() {
        return atCollectorList;
    }

    public void setAtCollectorList(List<AtCollectorListBean> atCollectorList) {
        this.atCollectorList = atCollectorList;
    }

    public static class AtCollectorListBean {
        /**
         * addr : 郑州国际会展中心-测试点
         * collectorId : 201811131014741
         * collectorIp : 122.97.174.49
         * collectorNo : 201811131014741
         * collectorNo110 :
         * collectorPort : 7000
         * collectorUserName :
         * collectorUuid : 37bc72837f1548bfab9cce3b03315cdd
         * createId : 1
         * createIdFlag : 1
         * createTime : 2020-12-10 09:43:40
         * installFlag : 1
         * keyword :
         * lat : 34.78011
         * lng : 113.732245
         * loginUserId :
         * offlineState : 0
         * orgId : 2018LENoOyAYmq
         * orgName :
         * remark : 慕然云测试
         * reportTime : 2020-12-23 16:30:00
         * reportTimeBegin :
         * reportTimeEnd :
         * state : 1
         * traffic : 458
         * updateId : 1
         * updateIdFlag : 1
         * updateTime : 2020-12-23 17:30:14
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
