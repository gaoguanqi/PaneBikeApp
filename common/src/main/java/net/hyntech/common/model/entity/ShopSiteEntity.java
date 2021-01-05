package net.hyntech.common.model.entity;

import net.hyntech.baselib.http.BaseResponse;

public class ShopSiteEntity extends BaseResponse<ShopSiteEntity> {


    /**
     * atServiceShop : {"addr":"开封市尉氏县","createId":"2019FYO6dbhfni","createIdFlag":0,"createName":"","createTime":"2020-06-27 11:44:34","delFlag":1,"distance":0,"keyword":"","lat":34.3741294,"lng":114.0133395,"loginUserId":"","name":"","orgId":"2018LENoOyAYmq","orgName":"","phone":"13837865119","relevantPic":"http://oss-public.hyntech.net/appUpload/20200627/2018LENoOyAYmq/c0501b2dbc464ff0be809c43e68b68e1.JPEG","remark":"","serviceShopId":"2020FaLshJWvTh","shopName":"李鹏车行","shopType":"1,2,3","state":1,"updateId":"2019FYO6dbhfni","updateIdFlag":0,"updateTime":"2020-07-16 15:25:17"}
     */

    private AtServiceShopBean atServiceShop;

    public AtServiceShopBean getAtServiceShop() {
        return atServiceShop;
    }

    public void setAtServiceShop(AtServiceShopBean atServiceShop) {
        this.atServiceShop = atServiceShop;
    }

    public static class AtServiceShopBean {
        /**
         * addr : 开封市尉氏县
         * createId : 2019FYO6dbhfni
         * createIdFlag : 0
         * createName :
         * createTime : 2020-06-27 11:44:34
         * delFlag : 1
         * distance : 0
         * keyword :
         * lat : 34.3741294
         * lng : 114.0133395
         * loginUserId :
         * name :
         * orgId : 2018LENoOyAYmq
         * orgName :
         * phone : 13837865119
         * relevantPic : http://oss-public.hyntech.net/appUpload/20200627/2018LENoOyAYmq/c0501b2dbc464ff0be809c43e68b68e1.JPEG
         * remark :
         * serviceShopId : 2020FaLshJWvTh
         * shopName : 李鹏车行
         * shopType : 1,2,3
         * state : 1
         * updateId : 2019FYO6dbhfni
         * updateIdFlag : 0
         * updateTime : 2020-07-16 15:25:17
         */

        private String addr;
        private String createId;
        private int createIdFlag;
        private String createName;
        private String createTime;
        private int delFlag;
        private int distance;
        private String keyword;
        private double lat;
        private double lng;
        private String loginUserId;
        private String name;
        private String orgId;
        private String orgName;
        private String phone;
        private String relevantPic;
        private String remark;
        private String serviceShopId;
        private String shopName;
        private String shopType;
        private int state;
        private String updateId;
        private int updateIdFlag;
        private String updateTime;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
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

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
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

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRelevantPic() {
            return relevantPic;
        }

        public void setRelevantPic(String relevantPic) {
            this.relevantPic = relevantPic;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getServiceShopId() {
            return serviceShopId;
        }

        public void setServiceShopId(String serviceShopId) {
            this.serviceShopId = serviceShopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopType() {
            return shopType;
        }

        public void setShopType(String shopType) {
            this.shopType = shopType;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
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
