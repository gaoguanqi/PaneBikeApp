package net.hyntech.common.model.entity;


import net.hyntech.baselib.http.BaseResponse;

public class ServiceDetailEntity extends BaseResponse<ServiceDetailEntity> {

    /**
     * atServicePackageOrg : {"companyId":"2018EBPfeAIaVf","createId":"1","createTime":"2019-08-05 17:30:49","cuserName":"","delFlag":0,"insuranceDesc":"","insuranceId":"2019HEOJxBCB5j","insuranceName":"电动车防盗保障服务E","insurancePic":"http://oss-public.hyntech.net/0/20190805/d813ed5641cc4e7892abea0d3d268e22_20190805_140934.jpg","insurancePrice":9500,"loginUserId":"","orgId":"2018LENoOyAYmq","orgName":"尉氏县","price":0,"priceType":0,"remark":"含电瓶险","servicePackageOrgId":"354891","termRange":2,"termType":"","updateId":"1","updateTime":"2019-08-05 17:30:49","uuserName":""}
     */

    private AtServicePackageOrgBean atServicePackageOrg;

    public AtServicePackageOrgBean getAtServicePackageOrg() {
        return atServicePackageOrg;
    }

    public void setAtServicePackageOrg(AtServicePackageOrgBean atServicePackageOrg) {
        this.atServicePackageOrg = atServicePackageOrg;
    }

    public static class AtServicePackageOrgBean {
        /**
         * companyId : 2018EBPfeAIaVf
         * createId : 1
         * createTime : 2019-08-05 17:30:49
         * cuserName :
         * delFlag : 0
         * insuranceDesc :
         * insuranceId : 2019HEOJxBCB5j
         * insuranceName : 电动车防盗保障服务E
         * insurancePic : http://oss-public.hyntech.net/0/20190805/d813ed5641cc4e7892abea0d3d268e22_20190805_140934.jpg
         * insurancePrice : 9500
         * loginUserId :
         * orgId : 2018LENoOyAYmq
         * orgName : 尉氏县
         * price : 0
         * priceType : 0
         * remark : 含电瓶险
         * servicePackageOrgId : 354891
         * termRange : 2
         * termType :
         * updateId : 1
         * updateTime : 2019-08-05 17:30:49
         * uuserName :
         */

        private String companyId;
        private String createId;
        private String createTime;
        private String cuserName;
        private int delFlag;
        private String insuranceDesc;
        private String insuranceId;
        private String insuranceName;
        private String insurancePic;
        private long insurancePrice;
        private String loginUserId;
        private String orgId;
        private String orgName;
        private long price;
        private int priceType;
        private String remark;
        private String servicePackageOrgId;
        private int termRange;
        private String termType;
        private String updateId;
        private String updateTime;
        private String uuserName;

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
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

        public String getCuserName() {
            return cuserName;
        }

        public void setCuserName(String cuserName) {
            this.cuserName = cuserName;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public String getInsuranceDesc() {
            return insuranceDesc;
        }

        public void setInsuranceDesc(String insuranceDesc) {
            this.insuranceDesc = insuranceDesc;
        }

        public String getInsuranceId() {
            return insuranceId;
        }

        public void setInsuranceId(String insuranceId) {
            this.insuranceId = insuranceId;
        }

        public String getInsuranceName() {
            return insuranceName;
        }

        public void setInsuranceName(String insuranceName) {
            this.insuranceName = insuranceName;
        }

        public String getInsurancePic() {
            return insurancePic;
        }

        public void setInsurancePic(String insurancePic) {
            this.insurancePic = insurancePic;
        }

        public long getInsurancePrice() {
            return insurancePrice;
        }

        public void setInsurancePrice(long insurancePrice) {
            this.insurancePrice = insurancePrice;
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

        public long getPrice() {
            return price;
        }

        public void setPrice(long price) {
            this.price = price;
        }

        public int getPriceType() {
            return priceType;
        }

        public void setPriceType(int priceType) {
            this.priceType = priceType;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getServicePackageOrgId() {
            return servicePackageOrgId;
        }

        public void setServicePackageOrgId(String servicePackageOrgId) {
            this.servicePackageOrgId = servicePackageOrgId;
        }

        public int getTermRange() {
            return termRange;
        }

        public void setTermRange(int termRange) {
            this.termRange = termRange;
        }

        public String getTermType() {
            return termType;
        }

        public void setTermType(String termType) {
            this.termType = termType;
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

        public String getUuserName() {
            return uuserName;
        }

        public void setUuserName(String uuserName) {
            this.uuserName = uuserName;
        }
    }
}
