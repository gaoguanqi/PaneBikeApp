package net.hyntech.common.model.entity;

import net.hyntech.baselib.http.BaseResponse;

import java.util.List;

public class EbikeTrackEntity extends BaseResponse<EbikeTrackEntity> {


    /**
     * ebike : {"alarmId":"","beginTimeInsurance":"","buyTime":"","buyTimeBegin":"","buyTimeEnd":"","createId":"2","createIdFlag":0,"createTime":"2018-03-08 15:30:58","delFlag":0,"ebikeColor":"hong","ebikeId":"12313123213","ebikeNo":"789","ebikePic1":"","ebikePic2":"","ebikePic3":"","ebikePic4":"","ebikePic5":"","ebikePic6":"","ebikePics":"","ebikeType":"123456","enclosureId":"","endTimeInsurance":"","engineNo":"123456","frameNo":"123456","idNo":"","installState":1,"insuranceCoverage":0,"insurancePrice":0,"insuranceProductName":"","insuranceState":"insurance_expired","insuranceTime":"","invoicePic":"","isWlList":0,"keyword":"","lastDirection":"停留","lastLat":39.922436,"lastLng":116.416833,"locatorNo":"1707336E","locatorPic":"","locatorUuid":"2018CEOi2VOjIt","lockFlag":1,"loginUserId":"","orderId":"123","orgId":"2018AeLCKojsjO","orgName":"","payState":0,"phone":"18103795073","policeOfficeId":"","price":2300,"remark":"","ruleType":"","servicePackageOrgId":"","state":"alarm","termRange":0,"type":0,"updateId":"0ec6a8bf48f34a168c9ec635f8e50867","updateIdFlag":0,"updateTime":"2018-03-27 16:54:45","userId":"0ec6a8bf48f34a168c9ec635f8e50867","userIdType":"","userName":"宋康kang","userSex":0}
     * trajectoryList : [{"channelNo":4,"collectorUuid":"2018CEOYwOOzS4","createTime":"2018-04-03 11:15:36","direction":"停留","lat":116.416833,"lng":39.922436,"locationId":"4c248d4a2b5c4ce0af56ac8ea2f930db","locatorUuid":"2018CEOi2VOjIt","loginUserId":"","orgId":"","reportTime":"2018-04-03 11:15:35","reportTimeBegin":"","reportTimeEnd":"","stopFlag":1,"updateTime":"2018-04-03 11:15:36"}]
     */

    private EbikeBean ebike;
    private List<TrajectoryListBean> trajectoryList;

    public EbikeBean getEbike() {
        return ebike;
    }

    public void setEbike(EbikeBean ebike) {
        this.ebike = ebike;
    }

    public List<TrajectoryListBean> getTrajectoryList() {
        return trajectoryList;
    }

    public void setTrajectoryList(List<TrajectoryListBean> trajectoryList) {
        this.trajectoryList = trajectoryList;
    }

    public static class EbikeBean {
        /**
         * alarmId :
         * beginTimeInsurance :
         * buyTime :
         * buyTimeBegin :
         * buyTimeEnd :
         * createId : 2
         * createIdFlag : 0
         * createTime : 2018-03-08 15:30:58
         * delFlag : 0
         * ebikeColor : hong
         * ebikeId : 12313123213
         * ebikeNo : 789
         * ebikePic1 :
         * ebikePic2 :
         * ebikePic3 :
         * ebikePic4 :
         * ebikePic5 :
         * ebikePic6 :
         * ebikePics :
         * ebikeType : 123456
         * enclosureId :
         * endTimeInsurance :
         * engineNo : 123456
         * frameNo : 123456
         * idNo :
         * installState : 1
         * insuranceCoverage : 0
         * insurancePrice : 0
         * insuranceProductName :
         * insuranceState : insurance_expired
         * insuranceTime :
         * invoicePic :
         * isWlList : 0
         * keyword :
         * lastDirection : 停留
         * lastLat : 39.922436
         * lastLng : 116.416833
         * locatorNo : 1707336E
         * locatorPic :
         * locatorUuid : 2018CEOi2VOjIt
         * lockFlag : 1
         * loginUserId :
         * orderId : 123
         * orgId : 2018AeLCKojsjO
         * orgName :
         * payState : 0
         * phone : 18103795073
         * policeOfficeId :
         * price : 2300
         * remark :
         * ruleType :
         * servicePackageOrgId :
         * state : alarm
         * termRange : 0
         * type : 0
         * updateId : 0ec6a8bf48f34a168c9ec635f8e50867
         * updateIdFlag : 0
         * updateTime : 2018-03-27 16:54:45
         * userId : 0ec6a8bf48f34a168c9ec635f8e50867
         * userIdType :
         * userName : 宋康kang
         * userSex : 0
         */

        private String alarmId;
        private String beginTimeInsurance;
        private String buyTime;
        private String buyTimeBegin;
        private String buyTimeEnd;
        private String createId;
        private int createIdFlag;
        private String createTime;
        private int delFlag;
        private String ebikeColor;
        private String ebikeId;
        private String ebikeNo;
        private String ebikePic1;
        private String ebikePic2;
        private String ebikePic3;
        private String ebikePic4;
        private String ebikePic5;
        private String ebikePic6;
        private String ebikePics;
        private String ebikeType;
        private String enclosureId;
        private String endTimeInsurance;
        private String engineNo;
        private String frameNo;
        private String idNo;
        private int installState;
        private int insuranceCoverage;
        private long insurancePrice;
        private String insuranceProductName;
        private String insuranceState;
        private String insuranceTime;
        private String invoicePic;
        private int isWlList;
        private String keyword;
        private String lastDirection;
        private double lastLat;
        private double lastLng;
        private String locatorNo;
        private String locatorPic;
        private String locatorUuid;
        private int lockFlag;
        private String loginUserId;
        private String orderId;
        private String orgId;
        private String orgName;
        private int payState;
        private String phone;
        private String policeOfficeId;
        private long price;
        private String remark;
        private String ruleType;
        private String servicePackageOrgId;
        private String state;
        private int termRange;
        private int type;
        private String updateId;
        private int updateIdFlag;
        private String updateTime;
        private String userId;
        private String userIdType;
        private String userName;
        private int userSex;

        public String getAlarmId() {
            return alarmId;
        }

        public void setAlarmId(String alarmId) {
            this.alarmId = alarmId;
        }

        public String getBeginTimeInsurance() {
            return beginTimeInsurance;
        }

        public void setBeginTimeInsurance(String beginTimeInsurance) {
            this.beginTimeInsurance = beginTimeInsurance;
        }

        public String getBuyTime() {
            return buyTime;
        }

        public void setBuyTime(String buyTime) {
            this.buyTime = buyTime;
        }

        public String getBuyTimeBegin() {
            return buyTimeBegin;
        }

        public void setBuyTimeBegin(String buyTimeBegin) {
            this.buyTimeBegin = buyTimeBegin;
        }

        public String getBuyTimeEnd() {
            return buyTimeEnd;
        }

        public void setBuyTimeEnd(String buyTimeEnd) {
            this.buyTimeEnd = buyTimeEnd;
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

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public String getEbikeColor() {
            return ebikeColor;
        }

        public void setEbikeColor(String ebikeColor) {
            this.ebikeColor = ebikeColor;
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

        public String getEbikePic1() {
            return ebikePic1;
        }

        public void setEbikePic1(String ebikePic1) {
            this.ebikePic1 = ebikePic1;
        }

        public String getEbikePic2() {
            return ebikePic2;
        }

        public void setEbikePic2(String ebikePic2) {
            this.ebikePic2 = ebikePic2;
        }

        public String getEbikePic3() {
            return ebikePic3;
        }

        public void setEbikePic3(String ebikePic3) {
            this.ebikePic3 = ebikePic3;
        }

        public String getEbikePic4() {
            return ebikePic4;
        }

        public void setEbikePic4(String ebikePic4) {
            this.ebikePic4 = ebikePic4;
        }

        public String getEbikePic5() {
            return ebikePic5;
        }

        public void setEbikePic5(String ebikePic5) {
            this.ebikePic5 = ebikePic5;
        }

        public String getEbikePic6() {
            return ebikePic6;
        }

        public void setEbikePic6(String ebikePic6) {
            this.ebikePic6 = ebikePic6;
        }

        public String getEbikePics() {
            return ebikePics;
        }

        public void setEbikePics(String ebikePics) {
            this.ebikePics = ebikePics;
        }

        public String getEbikeType() {
            return ebikeType;
        }

        public void setEbikeType(String ebikeType) {
            this.ebikeType = ebikeType;
        }

        public String getEnclosureId() {
            return enclosureId;
        }

        public void setEnclosureId(String enclosureId) {
            this.enclosureId = enclosureId;
        }

        public String getEndTimeInsurance() {
            return endTimeInsurance;
        }

        public void setEndTimeInsurance(String endTimeInsurance) {
            this.endTimeInsurance = endTimeInsurance;
        }

        public String getEngineNo() {
            return engineNo;
        }

        public void setEngineNo(String engineNo) {
            this.engineNo = engineNo;
        }

        public String getFrameNo() {
            return frameNo;
        }

        public void setFrameNo(String frameNo) {
            this.frameNo = frameNo;
        }

        public String getIdNo() {
            return idNo;
        }

        public void setIdNo(String idNo) {
            this.idNo = idNo;
        }

        public int getInstallState() {
            return installState;
        }

        public void setInstallState(int installState) {
            this.installState = installState;
        }

        public int getInsuranceCoverage() {
            return insuranceCoverage;
        }

        public void setInsuranceCoverage(int insuranceCoverage) {
            this.insuranceCoverage = insuranceCoverage;
        }

        public long getInsurancePrice() {
            return insurancePrice;
        }

        public void setInsurancePrice(long insurancePrice) {
            this.insurancePrice = insurancePrice;
        }

        public String getInsuranceProductName() {
            return insuranceProductName;
        }

        public void setInsuranceProductName(String insuranceProductName) {
            this.insuranceProductName = insuranceProductName;
        }

        public String getInsuranceState() {
            return insuranceState;
        }

        public void setInsuranceState(String insuranceState) {
            this.insuranceState = insuranceState;
        }

        public String getInsuranceTime() {
            return insuranceTime;
        }

        public void setInsuranceTime(String insuranceTime) {
            this.insuranceTime = insuranceTime;
        }

        public String getInvoicePic() {
            return invoicePic;
        }

        public void setInvoicePic(String invoicePic) {
            this.invoicePic = invoicePic;
        }

        public int getIsWlList() {
            return isWlList;
        }

        public void setIsWlList(int isWlList) {
            this.isWlList = isWlList;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getLastDirection() {
            return lastDirection;
        }

        public void setLastDirection(String lastDirection) {
            this.lastDirection = lastDirection;
        }

        public double getLastLat() {
            return lastLat;
        }

        public void setLastLat(double lastLat) {
            this.lastLat = lastLat;
        }

        public double getLastLng() {
            return lastLng;
        }

        public void setLastLng(double lastLng) {
            this.lastLng = lastLng;
        }

        public String getLocatorNo() {
            return locatorNo;
        }

        public void setLocatorNo(String locatorNo) {
            this.locatorNo = locatorNo;
        }

        public String getLocatorPic() {
            return locatorPic;
        }

        public void setLocatorPic(String locatorPic) {
            this.locatorPic = locatorPic;
        }

        public String getLocatorUuid() {
            return locatorUuid;
        }

        public void setLocatorUuid(String locatorUuid) {
            this.locatorUuid = locatorUuid;
        }

        public int getLockFlag() {
            return lockFlag;
        }

        public void setLockFlag(int lockFlag) {
            this.lockFlag = lockFlag;
        }

        public String getLoginUserId() {
            return loginUserId;
        }

        public void setLoginUserId(String loginUserId) {
            this.loginUserId = loginUserId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
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

        public int getPayState() {
            return payState;
        }

        public void setPayState(int payState) {
            this.payState = payState;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPoliceOfficeId() {
            return policeOfficeId;
        }

        public void setPoliceOfficeId(String policeOfficeId) {
            this.policeOfficeId = policeOfficeId;
        }

        public long getPrice() {
            return price;
        }

        public void setPrice(long price) {
            this.price = price;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRuleType() {
            return ruleType;
        }

        public void setRuleType(String ruleType) {
            this.ruleType = ruleType;
        }

        public String getServicePackageOrgId() {
            return servicePackageOrgId;
        }

        public void setServicePackageOrgId(String servicePackageOrgId) {
            this.servicePackageOrgId = servicePackageOrgId;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public int getTermRange() {
            return termRange;
        }

        public void setTermRange(int termRange) {
            this.termRange = termRange;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserIdType() {
            return userIdType;
        }

        public void setUserIdType(String userIdType) {
            this.userIdType = userIdType;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getUserSex() {
            return userSex;
        }

        public void setUserSex(int userSex) {
            this.userSex = userSex;
        }
    }

    public static class TrajectoryListBean {
        /**
         * channelNo : 4
         * collectorUuid : 2018CEOYwOOzS4
         * createTime : 2018-04-03 11:15:36
         * direction : 停留
         * lat : 116.416833
         * lng : 39.922436
         * locationId : 4c248d4a2b5c4ce0af56ac8ea2f930db
         * locatorUuid : 2018CEOi2VOjIt
         * loginUserId :
         * orgId :
         * reportTime : 2018-04-03 11:15:35
         * reportTimeBegin :
         * reportTimeEnd :
         * stopFlag : 1
         * updateTime : 2018-04-03 11:15:36
         */

        private int channelNo;
        private String collectorUuid;
        private String createTime;
        private String direction;
        private double lat;
        private double lng;
        private String locationId;
        private String locatorUuid;
        private String loginUserId;
        private String orgId;
        private String reportTime;
        private String reportTimeBegin;
        private String reportTimeEnd;
        private int stopFlag;
        private String updateTime;

        public int getChannelNo() {
            return channelNo;
        }

        public void setChannelNo(int channelNo) {
            this.channelNo = channelNo;
        }

        public String getCollectorUuid() {
            return collectorUuid;
        }

        public void setCollectorUuid(String collectorUuid) {
            this.collectorUuid = collectorUuid;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
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

        public String getLocationId() {
            return locationId;
        }

        public void setLocationId(String locationId) {
            this.locationId = locationId;
        }

        public String getLocatorUuid() {
            return locatorUuid;
        }

        public void setLocatorUuid(String locatorUuid) {
            this.locatorUuid = locatorUuid;
        }

        public String getLoginUserId() {
            return loginUserId;
        }

        public void setLoginUserId(String loginUserId) {
            this.loginUserId = loginUserId;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
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

        public int getStopFlag() {
            return stopFlag;
        }

        public void setStopFlag(int stopFlag) {
            this.stopFlag = stopFlag;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
