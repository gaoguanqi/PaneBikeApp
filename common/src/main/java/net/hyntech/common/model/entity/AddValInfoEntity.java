package net.hyntech.common.model.entity;

import net.hyntech.baselib.http.BaseResponse;

public class AddValInfoEntity extends BaseResponse<AddValInfoEntity> {

    /**
     * valueAddedServiceOrder : {"beginTime":"2020-12-19 00:00:00","beginTimeBegin":"","beginTimeEnd":"","createId":"","createTime":"","ebikeId":"","ebikeNo":"","endTime":"2021-12-19 00:00:00","endTimeBegin":"","endTimeEnd":"","idNo":"","loginUserId":"","orderId":"","orgId":"","orgName":"","payChannel":"","payMoney":8000,"payTime":"","payTimeBegin":"","payTimeEnd":"","remark":"","state":"","tradeNo":"","updateId":"","updateTime":"","userId":"","userName":"","userPhone":"","valueAddedServiceId":"2018IUKepYnvET","valueAddedServiceName":"车辆增值服务包12个月"}
     * ebike : {"alarmId":"2020LNShALpxbm","beginTimeInsurance":"","buyTime":"2020-12-01 00:00:00","buyTimeBegin":"","buyTimeEnd":"","createId":"2019CaOrNh2wQu","createIdFlag":0,"createTime":"2020-12-02 15:07:14","delFlag":0,"ebikeColor":"绿色","ebikeId":"17551c388e1a47539bf654a416530f63","ebikeNo":"WS113651","ebikePic1":"http://oss-public.hyntech.net/appUpload/20201202/2018LENoOyAYmq/f6dfa0aad1dc4672b2725b55546f147e.jpg","ebikePic2":"http://oss-public.hyntech.net/appUpload/20201202/2018LENoOyAYmq/41fd054c25ea40ec80b621215117d0b7.jpg","ebikePic3":"","ebikePic4":"","ebikePic5":"","ebikePic6":"","ebikePics":"","ebikeType":"金彭","ebikeTypeNum":"","enclosureId":"","endTimeInsurance":"","engineNo":"无","frameNo":"无","idNo":"410223196712136512","idNoPic1":"","idNoPic2":"","installState":1,"insuranceCoverage":0,"insurancePrice":0,"insuranceProductName":"","insuranceState":"working","insuranceTime":"","invoicePic":"http://oss-public.hyntech.net/appUpload/20201202/2018LENoOyAYmq/7f6dbad84fbe4c498f147f53468edb63.jpg","isWlList":0,"keyword":"","lastDirection":"","lastLat":0,"lastLng":0,"locatorNo":"35831367","locatorPic":"http://oss-public.hyntech.net/appUpload/20201202/2018LENoOyAYmq/6ab148ac98f34e738122aa8e38b9947a.jpg","locatorUuid":"a80eabba3f834d28bf154d0aa5de1f40","lockFlag":0,"loginUserId":"","orderId":"201202150713330216222623","orgId":"2018LENoOyAYmq","orgName":"","payState":0,"phone":"15664284736","policeName":"","policeOfficeId":"2018LEOhmYAJb4","price":500000,"remark":"","ruleType":"","servicePackageOrgId":"","state":"alarm_confrim","termRange":0,"type":2,"typeName":"电动三轮车","updateId":"1","updateIdFlag":1,"updateTime":"2020-12-16 15:04:23","userAddress":"","userBirthDay":"","userId":"a58c3eba6c8f4ab58935345bfeeda009","userIdType":"","userName":"???","userSex":0}
     */

    private ValueAddedServiceOrderBean valueAddedServiceOrder;
    private EbikeBean ebike;

    public ValueAddedServiceOrderBean getValueAddedServiceOrder() {
        return valueAddedServiceOrder;
    }

    public void setValueAddedServiceOrder(ValueAddedServiceOrderBean valueAddedServiceOrder) {
        this.valueAddedServiceOrder = valueAddedServiceOrder;
    }

    public EbikeBean getEbike() {
        return ebike;
    }

    public void setEbike(EbikeBean ebike) {
        this.ebike = ebike;
    }

    public static class ValueAddedServiceOrderBean {
        /**
         * beginTime : 2020-12-19 00:00:00
         * beginTimeBegin :
         * beginTimeEnd :
         * createId :
         * createTime :
         * ebikeId :
         * ebikeNo :
         * endTime : 2021-12-19 00:00:00
         * endTimeBegin :
         * endTimeEnd :
         * idNo :
         * loginUserId :
         * orderId :
         * orgId :
         * orgName :
         * payChannel :
         * payMoney : 8000
         * payTime :
         * payTimeBegin :
         * payTimeEnd :
         * remark :
         * state :
         * tradeNo :
         * updateId :
         * updateTime :
         * userId :
         * userName :
         * userPhone :
         * valueAddedServiceId : 2018IUKepYnvET
         * valueAddedServiceName : 车辆增值服务包12个月
         */

        private String beginTime;
        private String beginTimeBegin;
        private String beginTimeEnd;
        private String createId;
        private String createTime;
        private String ebikeId;
        private String ebikeNo;
        private String endTime;
        private String endTimeBegin;
        private String endTimeEnd;
        private String idNo;
        private String loginUserId;
        private String orderId;
        private String orgId;
        private String orgName;
        private String payChannel;
        private long payMoney;
        private String payTime;
        private String payTimeBegin;
        private String payTimeEnd;
        private String remark;
        private String state;
        private String tradeNo;
        private String updateId;
        private String updateTime;
        private String userId;
        private String userName;
        private String userPhone;
        private String valueAddedServiceId;
        private String valueAddedServiceName;

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getBeginTimeBegin() {
            return beginTimeBegin;
        }

        public void setBeginTimeBegin(String beginTimeBegin) {
            this.beginTimeBegin = beginTimeBegin;
        }

        public String getBeginTimeEnd() {
            return beginTimeEnd;
        }

        public void setBeginTimeEnd(String beginTimeEnd) {
            this.beginTimeEnd = beginTimeEnd;
        }

        public String getCreateId() {
            return createId;
        }

        public void setCreateId(String createId) {
            this.createId = createId;
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

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getEndTimeBegin() {
            return endTimeBegin;
        }

        public void setEndTimeBegin(String endTimeBegin) {
            this.endTimeBegin = endTimeBegin;
        }

        public String getEndTimeEnd() {
            return endTimeEnd;
        }

        public void setEndTimeEnd(String endTimeEnd) {
            this.endTimeEnd = endTimeEnd;
        }

        public String getIdNo() {
            return idNo;
        }

        public void setIdNo(String idNo) {
            this.idNo = idNo;
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

        public String getPayChannel() {
            return payChannel;
        }

        public void setPayChannel(String payChannel) {
            this.payChannel = payChannel;
        }

        public long getPayMoney() {
            return payMoney;
        }

        public void setPayMoney(long payMoney) {
            this.payMoney = payMoney;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getPayTimeBegin() {
            return payTimeBegin;
        }

        public void setPayTimeBegin(String payTimeBegin) {
            this.payTimeBegin = payTimeBegin;
        }

        public String getPayTimeEnd() {
            return payTimeEnd;
        }

        public void setPayTimeEnd(String payTimeEnd) {
            this.payTimeEnd = payTimeEnd;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getTradeNo() {
            return tradeNo;
        }

        public void setTradeNo(String tradeNo) {
            this.tradeNo = tradeNo;
        }

        public String getUpdateId() {
            return updateId;
        }

        public void setUpdateId(String updateId) {
            this.updateId = updateId;
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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getValueAddedServiceId() {
            return valueAddedServiceId;
        }

        public void setValueAddedServiceId(String valueAddedServiceId) {
            this.valueAddedServiceId = valueAddedServiceId;
        }

        public String getValueAddedServiceName() {
            return valueAddedServiceName;
        }

        public void setValueAddedServiceName(String valueAddedServiceName) {
            this.valueAddedServiceName = valueAddedServiceName;
        }
    }

    public static class EbikeBean {
        /**
         * alarmId : 2020LNShALpxbm
         * beginTimeInsurance :
         * buyTime : 2020-12-01 00:00:00
         * buyTimeBegin :
         * buyTimeEnd :
         * createId : 2019CaOrNh2wQu
         * createIdFlag : 0
         * createTime : 2020-12-02 15:07:14
         * delFlag : 0
         * ebikeColor : 绿色
         * ebikeId : 17551c388e1a47539bf654a416530f63
         * ebikeNo : WS113651
         * ebikePic1 : http://oss-public.hyntech.net/appUpload/20201202/2018LENoOyAYmq/f6dfa0aad1dc4672b2725b55546f147e.jpg
         * ebikePic2 : http://oss-public.hyntech.net/appUpload/20201202/2018LENoOyAYmq/41fd054c25ea40ec80b621215117d0b7.jpg
         * ebikePic3 :
         * ebikePic4 :
         * ebikePic5 :
         * ebikePic6 :
         * ebikePics :
         * ebikeType : 金彭
         * ebikeTypeNum :
         * enclosureId :
         * endTimeInsurance :
         * engineNo : 无
         * frameNo : 无
         * idNo : 410223196712136512
         * idNoPic1 :
         * idNoPic2 :
         * installState : 1
         * insuranceCoverage : 0
         * insurancePrice : 0
         * insuranceProductName :
         * insuranceState : working
         * insuranceTime :
         * invoicePic : http://oss-public.hyntech.net/appUpload/20201202/2018LENoOyAYmq/7f6dbad84fbe4c498f147f53468edb63.jpg
         * isWlList : 0
         * keyword :
         * lastDirection :
         * lastLat : 0
         * lastLng : 0
         * locatorNo : 35831367
         * locatorPic : http://oss-public.hyntech.net/appUpload/20201202/2018LENoOyAYmq/6ab148ac98f34e738122aa8e38b9947a.jpg
         * locatorUuid : a80eabba3f834d28bf154d0aa5de1f40
         * lockFlag : 0
         * loginUserId :
         * orderId : 201202150713330216222623
         * orgId : 2018LENoOyAYmq
         * orgName :
         * payState : 0
         * phone : 15664284736
         * policeName :
         * policeOfficeId : 2018LEOhmYAJb4
         * price : 500000
         * remark :
         * ruleType :
         * servicePackageOrgId :
         * state : alarm_confrim
         * termRange : 0
         * type : 2
         * typeName : 电动三轮车
         * updateId : 1
         * updateIdFlag : 1
         * updateTime : 2020-12-16 15:04:23
         * userAddress :
         * userBirthDay :
         * userId : a58c3eba6c8f4ab58935345bfeeda009
         * userIdType :
         * userName : ???
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
        private String ebikeTypeNum;
        private String enclosureId;
        private String endTimeInsurance;
        private String engineNo;
        private String frameNo;
        private String idNo;
        private String idNoPic1;
        private String idNoPic2;
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
        private int lastLat;
        private int lastLng;
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
        private String policeName;
        private String policeOfficeId;
        private long price;
        private String remark;
        private String ruleType;
        private String servicePackageOrgId;
        private String state;
        private int termRange;
        private int type;
        private String typeName;
        private String updateId;
        private int updateIdFlag;
        private String updateTime;
        private String userAddress;
        private String userBirthDay;
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

        public String getEbikeTypeNum() {
            return ebikeTypeNum;
        }

        public void setEbikeTypeNum(String ebikeTypeNum) {
            this.ebikeTypeNum = ebikeTypeNum;
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

        public String getIdNoPic1() {
            return idNoPic1;
        }

        public void setIdNoPic1(String idNoPic1) {
            this.idNoPic1 = idNoPic1;
        }

        public String getIdNoPic2() {
            return idNoPic2;
        }

        public void setIdNoPic2(String idNoPic2) {
            this.idNoPic2 = idNoPic2;
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

        public int getLastLat() {
            return lastLat;
        }

        public void setLastLat(int lastLat) {
            this.lastLat = lastLat;
        }

        public int getLastLng() {
            return lastLng;
        }

        public void setLastLng(int lastLng) {
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

        public String getPoliceName() {
            return policeName;
        }

        public void setPoliceName(String policeName) {
            this.policeName = policeName;
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

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
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

        public String getUserAddress() {
            return userAddress;
        }

        public void setUserAddress(String userAddress) {
            this.userAddress = userAddress;
        }

        public String getUserBirthDay() {
            return userBirthDay;
        }

        public void setUserBirthDay(String userBirthDay) {
            this.userBirthDay = userBirthDay;
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
}
