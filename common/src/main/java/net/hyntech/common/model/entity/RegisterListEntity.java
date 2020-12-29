package net.hyntech.common.model.entity;

import net.hyntech.baselib.http.BaseResponse;

import java.util.List;

public class RegisterListEntity extends BaseResponse<RegisterListEntity> {


    /**
     * atEbikeList : [{"alarmId":"","beginTimeInsurance":"","buyTime":"","buyTimeBegin":"","buyTimeEnd":"","createId":"","createIdFlag":0,"createTime":"2020-11-26 17:53:41","delFlag":0,"ebikeColor":"","ebikeId":"1d47fddbd0a041a3bb15265cf6e9c290","ebikeNo":"WS120318","ebikePic1":"","ebikePic2":"","ebikePic3":"","ebikePic4":"","ebikePic5":"","ebikePic6":"","ebikePics":"","ebikeType":"","ebikeTypeNum":"","enclosureId":"","endTimeInsurance":"","engineNo":"","frameNo":"","idNo":"410223200003264537","idNoPic1":"","idNoPic2":"","installState":0,"insuranceCoverage":0,"insurancePrice":0,"insuranceProductName":"电动车防盗保障服务E","insuranceState":"","insuranceTime":"","invoicePic":"","isWlList":0,"keyword":"","lastDirection":"","lastLat":0,"lastLng":0,"locatorNo":"","locatorPic":"","locatorUuid":"","lockFlag":0,"loginUserId":"","orderId":"201126175341301308560301","orgId":"","orgName":"","payState":0,"phone":"18537385619","policeName":"","policeOfficeId":"","price":0,"remark":"","ruleType":"","servicePackageOrgId":"","state":"normal","termRange":0,"type":0,"typeName":"","updateId":"","updateIdFlag":0,"updateTime":"","userAddress":"","userBirthDay":"","userId":"","userIdType":"","userName":"??","userSex":0},{"alarmId":"","beginTimeInsurance":"","buyTime":"","buyTimeBegin":"","buyTimeEnd":"","createId":"","createIdFlag":0,"createTime":"2020-11-26 17:13:15","delFlag":0,"ebikeColor":"","ebikeId":"9199d057b4204d1a8886898ee9b9928e","ebikeNo":"WS126368","ebikePic1":"","ebikePic2":"","ebikePic3":"","ebikePic4":"","ebikePic5":"","ebikePic6":"","ebikePics":"","ebikeType":"","ebikeTypeNum":"","enclosureId":"","endTimeInsurance":"","engineNo":"","frameNo":"","idNo":"410223200003264537","idNoPic1":"","idNoPic2":"","installState":0,"insuranceCoverage":0,"insurancePrice":0,"insuranceProductName":"电动车防盗保障服务E","insuranceState":"","insuranceTime":"","invoicePic":"","isWlList":0,"keyword":"","lastDirection":"","lastLat":0,"lastLng":0,"locatorNo":"","locatorPic":"","locatorUuid":"","lockFlag":0,"loginUserId":"","orderId":"201126171315114323073867","orgId":"","orgName":"","payState":0,"phone":"18537385619","policeName":"","policeOfficeId":"","price":0,"remark":"","ruleType":"","servicePackageOrgId":"","state":"normal","termRange":0,"type":0,"typeName":"","updateId":"","updateIdFlag":0,"updateTime":"","userAddress":"","userBirthDay":"","userId":"","userIdType":"","userName":"??","userSex":0},{"alarmId":"","beginTimeInsurance":"","buyTime":"","buyTimeBegin":"","buyTimeEnd":"","createId":"","createIdFlag":0,"createTime":"2020-11-26 16:26:39","delFlag":0,"ebikeColor":"","ebikeId":"7ed6cf4b2e9f4305b972615184db8bb1","ebikeNo":"WS120359","ebikePic1":"","ebikePic2":"","ebikePic3":"","ebikePic4":"","ebikePic5":"","ebikePic6":"","ebikePics":"","ebikeType":"","ebikeTypeNum":"","enclosureId":"","endTimeInsurance":"","engineNo":"","frameNo":"","idNo":"410223200003264537","idNoPic1":"","idNoPic2":"","installState":0,"insuranceCoverage":0,"insurancePrice":0,"insuranceProductName":"电动车防盗保障服务D","insuranceState":"","insuranceTime":"","invoicePic":"","isWlList":0,"keyword":"","lastDirection":"","lastLat":0,"lastLng":0,"locatorNo":"","locatorPic":"","locatorUuid":"","lockFlag":0,"loginUserId":"","orderId":"201126162638503903194945","orgId":"","orgName":"","payState":0,"phone":"18537385619","policeName":"","policeOfficeId":"","price":0,"remark":"","ruleType":"","servicePackageOrgId":"","state":"normal","termRange":0,"type":0,"typeName":"","updateId":"","updateIdFlag":0,"updateTime":"","userAddress":"","userBirthDay":"","userId":"","userIdType":"","userName":"??","userSex":0},{"alarmId":"","beginTimeInsurance":"","buyTime":"","buyTimeBegin":"","buyTimeEnd":"","createId":"","createIdFlag":0,"createTime":"2020-11-26 16:01:58","delFlag":0,"ebikeColor":"","ebikeId":"350bc2acbbb7403880ac0f75ae19e93d","ebikeNo":"WS120358","ebikePic1":"","ebikePic2":"","ebikePic3":"","ebikePic4":"","ebikePic5":"","ebikePic6":"","ebikePics":"","ebikeType":"","ebikeTypeNum":"","enclosureId":"","endTimeInsurance":"","engineNo":"","frameNo":"","idNo":"410223200003264537","idNoPic1":"","idNoPic2":"","installState":0,"insuranceCoverage":0,"insurancePrice":0,"insuranceProductName":"电动车防盗保障服务D","insuranceState":"","insuranceTime":"","invoicePic":"","isWlList":0,"keyword":"","lastDirection":"","lastLat":0,"lastLng":0,"locatorNo":"","locatorPic":"","locatorUuid":"","lockFlag":0,"loginUserId":"","orderId":"201126160158262406283845","orgId":"","orgName":"","payState":0,"phone":"18537385619","policeName":"","policeOfficeId":"","price":0,"remark":"","ruleType":"","servicePackageOrgId":"","state":"normal","termRange":0,"type":0,"typeName":"","updateId":"","updateIdFlag":0,"updateTime":"","userAddress":"","userBirthDay":"","userId":"","userIdType":"","userName":"??","userSex":0}]
     * page : {"firstPage":true,"lastPage":true,"list":[],"nextPage":1,"pageNo":1,"pageSize":10,"prePage":1,"totalCount":4,"totalPage":1}
     */

    private PageBean page;
    private List<AtEbikeListBean> atEbikeList;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<AtEbikeListBean> getAtEbikeList() {
        return atEbikeList;
    }

    public void setAtEbikeList(List<AtEbikeListBean> atEbikeList) {
        this.atEbikeList = atEbikeList;
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
         * totalCount : 4
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

    public static class AtEbikeListBean {
        /**
         * alarmId :
         * beginTimeInsurance :
         * buyTime :
         * buyTimeBegin :
         * buyTimeEnd :
         * createId :
         * createIdFlag : 0
         * createTime : 2020-11-26 17:53:41
         * delFlag : 0
         * ebikeColor :
         * ebikeId : 1d47fddbd0a041a3bb15265cf6e9c290
         * ebikeNo : WS120318
         * ebikePic1 :
         * ebikePic2 :
         * ebikePic3 :
         * ebikePic4 :
         * ebikePic5 :
         * ebikePic6 :
         * ebikePics :
         * ebikeType :
         * ebikeTypeNum :
         * enclosureId :
         * endTimeInsurance :
         * engineNo :
         * frameNo :
         * idNo : 410223200003264537
         * idNoPic1 :
         * idNoPic2 :
         * installState : 0
         * insuranceCoverage : 0
         * insurancePrice : 0
         * insuranceProductName : 电动车防盗保障服务E
         * insuranceState :
         * insuranceTime :
         * invoicePic :
         * isWlList : 0
         * keyword :
         * lastDirection :
         * lastLat : 0
         * lastLng : 0
         * locatorNo :
         * locatorPic :
         * locatorUuid :
         * lockFlag : 0
         * loginUserId :
         * orderId : 201126175341301308560301
         * orgId :
         * orgName :
         * payState : 0
         * phone : 18537385619
         * policeName :
         * policeOfficeId :
         * price : 0
         * remark :
         * ruleType :
         * servicePackageOrgId :
         * state : normal
         * termRange : 0
         * type : 0
         * typeName :
         * updateId :
         * updateIdFlag : 0
         * updateTime :
         * userAddress :
         * userBirthDay :
         * userId :
         * userIdType :
         * userName : ??
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
        private int insurancePrice;
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
        private int price;
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

        public int getInsurancePrice() {
            return insurancePrice;
        }

        public void setInsurancePrice(int insurancePrice) {
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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
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
