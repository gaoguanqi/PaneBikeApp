package net.hyntech.common.model.entity;

import net.hyntech.baselib.http.BaseResponse;

public class AddValDetailEntity extends BaseResponse<AddValDetailEntity> {


    /**
     * valueAddedService : {"createId":"1","createTime":"2018-09-21 10:30:42","delFlag":0,"loginUserId":"","orgId":"-1","orgName":"","remark":"","serviceDesc":"增值服务","serviceName":"车辆增值服务包","servicePic":"http://oss-public.hyntech.net/0/20180921/4833fc81d0c64e2abaa3fe686e69f007_20180921_103034.jpg","servicePrice":8000,"state":1,"termRange":12,"timeType":"month","updateId":"1","updateTime":"2018-09-21 10:30:42","valueAddedServiceId":"2018IUKepYnvET"}
     * ebikeId : 17551c388e1a47539bf654a416530f63
     */

    private ValueAddedServiceBean valueAddedService;
    private String ebikeId;

    public ValueAddedServiceBean getValueAddedService() {
        return valueAddedService;
    }

    public void setValueAddedService(ValueAddedServiceBean valueAddedService) {
        this.valueAddedService = valueAddedService;
    }

    public String getEbikeId() {
        return ebikeId;
    }

    public void setEbikeId(String ebikeId) {
        this.ebikeId = ebikeId;
    }

    public static class ValueAddedServiceBean {
        /**
         * createId : 1
         * createTime : 2018-09-21 10:30:42
         * delFlag : 0
         * loginUserId :
         * orgId : -1
         * orgName :
         * remark :
         * serviceDesc : 增值服务
         * serviceName : 车辆增值服务包
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
