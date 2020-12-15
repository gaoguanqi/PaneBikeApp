package net.hyntech.common.model.entity;

import net.hyntech.baselib.http.BaseResponse;

import java.util.List;

public class MyOrderEntity extends BaseResponse<MyOrderEntity> {

    /**
     * list : [{"balanceId":"","beginTimeInsurance":"2020-12-03","buyTime":"2020-12-01 00:00:00","buyTimeBegin":"","buyTimeEnd":"","companyId":"","companyName":"","createTime":"2020-12-02 15:07:14","ebike":null,"ebikeId":"17551c388e1a47539bf654a416530f63","ebikeNo":"WS113651","ebikePrice":500000,"ebikeType":"金彭","endTimeInsurance":"2022-12-03","frameNo":"无","idNo":"410223196712136512","insuranceBeginTime":"2020-12-03 00:00:00","insuranceBeginTimeBegin":"","insuranceBeginTimeEnd":"","insuranceClass":"","insuranceCoverage":120000,"insuranceEndTime":"2022-12-03 23:59:59","insuranceEndTimeBegin":"","insuranceEndTimeEnd":"","insuranceLimit":0,"insuranceNo":"","insurancePlace":"","insurancePrice":8000,"insuranceProductId":"2018LERABSFlFA","insuranceProductName":"电动车防盗保障服务D","insuranceProductNo":"2018LERABILwoc","insuranceProductOrgId":"472513","locatorNo":"","loginUserId":"","name":"郭小路","orderDetailId":"201202150713382114131745","orderId":"201202150713330216222623","orgId":"2018LENoOyAYmq","orgName":"","over":0,"password":"","phone":"15664284736","realPrice":0,"remark":"","sendTime":"2020-12-02 15:10:03","sendTimeBegin":"","sendTimeEnd":"","servicePackageOrgId":"","state":"working","termRange":"2","termType":0,"toServiceCompany":1,"type":"","updateTime":"2020-12-03 00:00:07","userId":"a58c3eba6c8f4ab58935345bfeeda009"}]
     * page : {"firstPage":true,"lastPage":true,"list":[],"nextPage":1,"pageNo":1,"pageSize":10,"prePage":1,"totalCount":1,"totalPage":1}
     */

    private PageBean page;
    private List<ListBean> list;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
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
         * totalCount : 1
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

    public static class ListBean {
        /**
         * balanceId :
         * beginTimeInsurance : 2020-12-03
         * buyTime : 2020-12-01 00:00:00
         * buyTimeBegin :
         * buyTimeEnd :
         * companyId :
         * companyName :
         * createTime : 2020-12-02 15:07:14
         * ebike : null
         * ebikeId : 17551c388e1a47539bf654a416530f63
         * ebikeNo : WS113651
         * ebikePrice : 500000
         * ebikeType : 金彭
         * endTimeInsurance : 2022-12-03
         * frameNo : 无
         * idNo : 410223196712136512
         * insuranceBeginTime : 2020-12-03 00:00:00
         * insuranceBeginTimeBegin :
         * insuranceBeginTimeEnd :
         * insuranceClass :
         * insuranceCoverage : 120000
         * insuranceEndTime : 2022-12-03 23:59:59
         * insuranceEndTimeBegin :
         * insuranceEndTimeEnd :
         * insuranceLimit : 0
         * insuranceNo :
         * insurancePlace :
         * insurancePrice : 8000
         * insuranceProductId : 2018LERABSFlFA
         * insuranceProductName : 电动车防盗保障服务D
         * insuranceProductNo : 2018LERABILwoc
         * insuranceProductOrgId : 472513
         * locatorNo :
         * loginUserId :
         * name : 郭小路
         * orderDetailId : 201202150713382114131745
         * orderId : 201202150713330216222623
         * orgId : 2018LENoOyAYmq
         * orgName :
         * over : 0
         * password :
         * phone : 15664284736
         * realPrice : 0
         * remark :
         * sendTime : 2020-12-02 15:10:03
         * sendTimeBegin :
         * sendTimeEnd :
         * servicePackageOrgId :
         * state : working
         * termRange : 2
         * termType : 0
         * toServiceCompany : 1
         * type :
         * updateTime : 2020-12-03 00:00:07
         * userId : a58c3eba6c8f4ab58935345bfeeda009
         */

        private String balanceId;
        private String beginTimeInsurance;
        private String buyTime;
        private String buyTimeBegin;
        private String buyTimeEnd;
        private String companyId;
        private String companyName;
        private String createTime;
        private Object ebike;
        private String ebikeId;
        private String ebikeNo;
        private int ebikePrice;
        private String ebikeType;
        private String endTimeInsurance;
        private String frameNo;
        private String idNo;
        private String insuranceBeginTime;
        private String insuranceBeginTimeBegin;
        private String insuranceBeginTimeEnd;
        private String insuranceClass;
        private int insuranceCoverage;
        private String insuranceEndTime;
        private String insuranceEndTimeBegin;
        private String insuranceEndTimeEnd;
        private int insuranceLimit;
        private String insuranceNo;
        private String insurancePlace;
        private long insurancePrice;
        private String insuranceProductId;
        private String insuranceProductName;
        private String insuranceProductNo;
        private String insuranceProductOrgId;
        private String locatorNo;
        private String loginUserId;
        private String name;
        private String orderDetailId;
        private String orderId;
        private String orgId;
        private String orgName;
        private int over;
        private String password;
        private String phone;
        private long realPrice;
        private String remark;
        private String sendTime;
        private String sendTimeBegin;
        private String sendTimeEnd;
        private String servicePackageOrgId;
        private String state;
        private String termRange;
        private int termType;
        private int toServiceCompany;
        private String type;
        private String updateTime;
        private String userId;

        public String getBalanceId() {
            return balanceId;
        }

        public void setBalanceId(String balanceId) {
            this.balanceId = balanceId;
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

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getEbike() {
            return ebike;
        }

        public void setEbike(Object ebike) {
            this.ebike = ebike;
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

        public int getEbikePrice() {
            return ebikePrice;
        }

        public void setEbikePrice(int ebikePrice) {
            this.ebikePrice = ebikePrice;
        }

        public String getEbikeType() {
            return ebikeType;
        }

        public void setEbikeType(String ebikeType) {
            this.ebikeType = ebikeType;
        }

        public String getEndTimeInsurance() {
            return endTimeInsurance;
        }

        public void setEndTimeInsurance(String endTimeInsurance) {
            this.endTimeInsurance = endTimeInsurance;
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

        public String getInsuranceBeginTime() {
            return insuranceBeginTime;
        }

        public void setInsuranceBeginTime(String insuranceBeginTime) {
            this.insuranceBeginTime = insuranceBeginTime;
        }

        public String getInsuranceBeginTimeBegin() {
            return insuranceBeginTimeBegin;
        }

        public void setInsuranceBeginTimeBegin(String insuranceBeginTimeBegin) {
            this.insuranceBeginTimeBegin = insuranceBeginTimeBegin;
        }

        public String getInsuranceBeginTimeEnd() {
            return insuranceBeginTimeEnd;
        }

        public void setInsuranceBeginTimeEnd(String insuranceBeginTimeEnd) {
            this.insuranceBeginTimeEnd = insuranceBeginTimeEnd;
        }

        public String getInsuranceClass() {
            return insuranceClass;
        }

        public void setInsuranceClass(String insuranceClass) {
            this.insuranceClass = insuranceClass;
        }

        public int getInsuranceCoverage() {
            return insuranceCoverage;
        }

        public void setInsuranceCoverage(int insuranceCoverage) {
            this.insuranceCoverage = insuranceCoverage;
        }

        public String getInsuranceEndTime() {
            return insuranceEndTime;
        }

        public void setInsuranceEndTime(String insuranceEndTime) {
            this.insuranceEndTime = insuranceEndTime;
        }

        public String getInsuranceEndTimeBegin() {
            return insuranceEndTimeBegin;
        }

        public void setInsuranceEndTimeBegin(String insuranceEndTimeBegin) {
            this.insuranceEndTimeBegin = insuranceEndTimeBegin;
        }

        public String getInsuranceEndTimeEnd() {
            return insuranceEndTimeEnd;
        }

        public void setInsuranceEndTimeEnd(String insuranceEndTimeEnd) {
            this.insuranceEndTimeEnd = insuranceEndTimeEnd;
        }

        public int getInsuranceLimit() {
            return insuranceLimit;
        }

        public void setInsuranceLimit(int insuranceLimit) {
            this.insuranceLimit = insuranceLimit;
        }

        public String getInsuranceNo() {
            return insuranceNo;
        }

        public void setInsuranceNo(String insuranceNo) {
            this.insuranceNo = insuranceNo;
        }

        public String getInsurancePlace() {
            return insurancePlace;
        }

        public void setInsurancePlace(String insurancePlace) {
            this.insurancePlace = insurancePlace;
        }

        public long getInsurancePrice() {
            return insurancePrice;
        }

        public void setInsurancePrice(long insurancePrice) {
            this.insurancePrice = insurancePrice;
        }

        public String getInsuranceProductId() {
            return insuranceProductId;
        }

        public void setInsuranceProductId(String insuranceProductId) {
            this.insuranceProductId = insuranceProductId;
        }

        public String getInsuranceProductName() {
            return insuranceProductName;
        }

        public void setInsuranceProductName(String insuranceProductName) {
            this.insuranceProductName = insuranceProductName;
        }

        public String getInsuranceProductNo() {
            return insuranceProductNo;
        }

        public void setInsuranceProductNo(String insuranceProductNo) {
            this.insuranceProductNo = insuranceProductNo;
        }

        public String getInsuranceProductOrgId() {
            return insuranceProductOrgId;
        }

        public void setInsuranceProductOrgId(String insuranceProductOrgId) {
            this.insuranceProductOrgId = insuranceProductOrgId;
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

        public String getOrderDetailId() {
            return orderDetailId;
        }

        public void setOrderDetailId(String orderDetailId) {
            this.orderDetailId = orderDetailId;
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

        public int getOver() {
            return over;
        }

        public void setOver(int over) {
            this.over = over;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public long getRealPrice() {
            return realPrice;
        }

        public void setRealPrice(long realPrice) {
            this.realPrice = realPrice;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public String getSendTimeBegin() {
            return sendTimeBegin;
        }

        public void setSendTimeBegin(String sendTimeBegin) {
            this.sendTimeBegin = sendTimeBegin;
        }

        public String getSendTimeEnd() {
            return sendTimeEnd;
        }

        public void setSendTimeEnd(String sendTimeEnd) {
            this.sendTimeEnd = sendTimeEnd;
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

        public String getTermRange() {
            return termRange;
        }

        public void setTermRange(String termRange) {
            this.termRange = termRange;
        }

        public int getTermType() {
            return termType;
        }

        public void setTermType(int termType) {
            this.termType = termType;
        }

        public int getToServiceCompany() {
            return toServiceCompany;
        }

        public void setToServiceCompany(int toServiceCompany) {
            this.toServiceCompany = toServiceCompany;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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
