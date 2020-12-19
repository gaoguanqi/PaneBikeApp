package net.hyntech.common.model.entity;

import net.hyntech.baselib.http.BaseResponse;

import java.util.List;

public class AddValServiceEntity extends BaseResponse<AddValServiceEntity> {

    /**
     * valueAddedServiceList : [{"createId":"1","createTime":"2018-09-21 10:30:42","delFlag":0,"loginUserId":"","orgId":"-1","orgName":"","remark":"","serviceDesc":"增值服务","serviceName":"车辆增值服务包12个月","servicePic":"http://oss-public.hyntech.net/0/20180921/4833fc81d0c64e2abaa3fe686e69f007_20180921_103034.jpg","servicePrice":8000,"state":1,"termRange":12,"timeType":"month","updateId":"1","updateTime":"2018-09-21 10:30:42","valueAddedServiceId":"2018IUKepYnvET"},{"createId":"1","createTime":"2018-09-21 10:29:52","delFlag":0,"loginUserId":"","orgId":"-1","orgName":"","remark":"","serviceDesc":"增值服务","serviceName":"车辆增值服务包6个月","servicePic":"http://oss-public.hyntech.net/0/20180921/bf2cb35d637545bfbb7d9fa446e3ff1e_20180921_102942.jpg","servicePrice":5500,"state":1,"termRange":6,"timeType":"month","updateId":"1","updateTime":"2018-09-21 10:29:52","valueAddedServiceId":"2018IUKd0kL6hA"},{"createId":"1","createTime":"2018-09-21 10:29:05","delFlag":0,"loginUserId":"","orgId":"-1","orgName":"","remark":"","serviceDesc":"增值服务","serviceName":"车辆增值服务包3个月","servicePic":"http://oss-public.hyntech.net/0/20180921/f820e23778234f7ea20d54566fb78744_20180921_102851.jpg","servicePrice":3000,"state":1,"termRange":3,"timeType":"month","updateId":"1","updateTime":"2018-09-21 10:29:05","valueAddedServiceId":"2018IUKdErRQgJ"},{"createId":"1","createTime":"2018-07-31 11:55:10","delFlag":0,"loginUserId":"","orgId":"-1","orgName":"","remark":"","serviceDesc":"增值服务","serviceName":"车辆增值服务包1个月","servicePic":"http://oss-public.hyntech.net/0/20180910/a2f5d2dadbc34f8ea786661d69102f2d_20180910_141439.jpg","servicePrice":1500,"state":1,"termRange":1,"timeType":"month","updateId":"1","updateTime":"2018-09-21 10:20:02","valueAddedServiceId":"2018GeL3KEgGbe"}]
     * page : {"firstPage":true,"lastPage":true,"list":[],"nextPage":1,"pageNo":1,"pageSize":10,"prePage":1,"totalCount":4,"totalPage":1}
     * ebikeId : 17551c388e1a47539bf654a416530f63
     */

    private PageBean page;
    private String ebikeId;
    private List<ValueAddedServiceListBean> valueAddedServiceList;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public String getEbikeId() {
        return ebikeId;
    }

    public void setEbikeId(String ebikeId) {
        this.ebikeId = ebikeId;
    }

    public List<ValueAddedServiceListBean> getValueAddedServiceList() {
        return valueAddedServiceList;
    }

    public void setValueAddedServiceList(List<ValueAddedServiceListBean> valueAddedServiceList) {
        this.valueAddedServiceList = valueAddedServiceList;
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

    public static class ValueAddedServiceListBean {
        /**
         * createId : 1
         * createTime : 2018-09-21 10:30:42
         * delFlag : 0
         * loginUserId :
         * orgId : -1
         * orgName :
         * remark :
         * serviceDesc : 增值服务
         * serviceName : 车辆增值服务包12个月
         * servicePic : http://oss-public.hyntech.net/0/20180921/4833fc81d0c64e2abaa3fe686e69f007_20180921_103034.jpg
         * servicePrice : 8000
         * state : 1
         * termRange : 12
         * timeType : month
         * updateId : 1
         * updateTime : 2018-09-21 10:30:42
         * valueAddedServiceId : 2018IUKepYnvET
         */

        private String createId;
        private String createTime;
        private int delFlag;
        private String loginUserId;
        private String orgId;
        private String orgName;
        private String remark;
        private String serviceDesc;
        private String serviceName;
        private String servicePic;
        private long servicePrice;
        private int state;
        private int termRange;
        private String timeType;
        private String updateId;
        private String updateTime;
        private String valueAddedServiceId;

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

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
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

        public String getServiceDesc() {
            return serviceDesc;
        }

        public void setServiceDesc(String serviceDesc) {
            this.serviceDesc = serviceDesc;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getServicePic() {
            return servicePic;
        }

        public void setServicePic(String servicePic) {
            this.servicePic = servicePic;
        }

        public long getServicePrice() {
            return servicePrice;
        }

        public void setServicePrice(long servicePrice) {
            this.servicePrice = servicePrice;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getTermRange() {
            return termRange;
        }

        public void setTermRange(int termRange) {
            this.termRange = termRange;
        }

        public String getTimeType() {
            return timeType;
        }

        public void setTimeType(String timeType) {
            this.timeType = timeType;
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

        public String getValueAddedServiceId() {
            return valueAddedServiceId;
        }

        public void setValueAddedServiceId(String valueAddedServiceId) {
            this.valueAddedServiceId = valueAddedServiceId;
        }
    }
}
