package net.hyntech.common.model.entity;

import net.hyntech.baselib.http.BaseResponse;

import java.util.List;

public class MyAddValServiceEntity extends BaseResponse<MyAddValServiceEntity> {


    /**
     * page : {"firstPage":true,"lastPage":true,"list":[],"nextPage":1,"pageNo":1,"pageSize":10,"prePage":1,"totalCount":1,"totalPage":1}
     * list : [{"beginTime":"2019-03-14 00:00:00","beginTimeBegin":"","beginTimeEnd":"","createId":"a7bf53268f9847dd9426d42772282cdc","createTime":"2019-03-14 16:02:06","ebikeId":"74547bd483d24b08ac87459cee9c5c85","ebikeNo":"祥018073","endTime":"2019-06-14 00:00:00","endTimeBegin":"","endTimeEnd":"","idNo":"410224198710010073","loginUserId":"","orderId":"190314160205081630524301","orgId":"2018IFLND3hAkE","orgName":"","payChannel":"weixin","payMoney":3000,"payTime":"2019-03-14 16:05:13","payTimeBegin":"","payTimeEnd":"","remark":"","state":"expired","tradeNo":"","updateId":"a7bf53268f9847dd9426d42772282cdc","updateTime":"2019-03-14 16:05:13","userId":"a7bf53268f9847dd9426d42772282cdc","userName":"黄舒炫","userPhone":"13393830066","valueAddedServiceId":"2018IUKdErRQgJ","valueAddedServiceName":"车辆增值服务包3个月"}]
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
         * beginTime : 2019-03-14 00:00:00
         * beginTimeBegin :
         * beginTimeEnd :
         * createId : a7bf53268f9847dd9426d42772282cdc
         * createTime : 2019-03-14 16:02:06
         * ebikeId : 74547bd483d24b08ac87459cee9c5c85
         * ebikeNo : 祥018073
         * endTime : 2019-06-14 00:00:00
         * endTimeBegin :
         * endTimeEnd :
         * idNo : 410224198710010073
         * loginUserId :
         * orderId : 190314160205081630524301
         * orgId : 2018IFLND3hAkE
         * orgName :
         * payChannel : weixin
         * payMoney : 3000
         * payTime : 2019-03-14 16:05:13
         * payTimeBegin :
         * payTimeEnd :
         * remark :
         * state : expired
         * tradeNo :
         * updateId : a7bf53268f9847dd9426d42772282cdc
         * updateTime : 2019-03-14 16:05:13
         * userId : a7bf53268f9847dd9426d42772282cdc
         * userName : 黄舒炫
         * userPhone : 13393830066
         * valueAddedServiceId : 2018IUKdErRQgJ
         * valueAddedServiceName : 车辆增值服务包3个月
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
}
