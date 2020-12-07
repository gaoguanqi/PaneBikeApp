package net.hyntech.common.model.entity;

import net.hyntech.baselib.http.BaseResponse;

import java.io.Serializable;
import java.util.List;

public class UserInfoEntity extends BaseResponse<UserInfoEntity> {


    /**
     * location_refresh_time : 10
     * user : {"addr":"河南省尉氏县水坡乡齐岗一组","areaName":"","birthdate":"1967-12-13 00:00:00","birthdateBegin":"","birthdateEnd":"","city":"","company":"","country":"","county":"","createId":"2019CaOrNh2wQu","createIdFlag":0,"createName":"","createTime":"2020-12-02 15:07:11","ebikeId":"","ebikeNo":"","ebikeOrgId":"","ebikeState":"","editFlag":"","email":"","headimgurl":"","idAddr":"河南省尉氏县水坡乡齐岗一组","idNo":"410223196712136512","idNoPic1":"http://hyntech-private.oss-cn-beijing.aliyuncs.com/appUpload/20201202/2018LENoOyAYmq/3008fb6c422344e2bf8b12a7aeb8d4c2.jpg?Expires=1607050101&OSSAccessKeyId=LTAIh24F0deeCk5J&Signature=a0WgSfEntiahcGQ2ytzBSKFiBcM%3D","idNoPic2":"http://hyntech-private.oss-cn-beijing.aliyuncs.com/appUpload/20201202/2018LENoOyAYmq/647c91124a2146dd99b7e4db371546be.jpg?Expires=1607050101&OSSAccessKeyId=LTAIh24F0deeCk5J&Signature=KBclEOQHPfNHkdGRpJsAYR3UW4I%3D","idType":"普通用户","insuranceState":"","locatorNo":"","locatorUuid":"","loginTime":"2020-12-04 09:48:18","loginTimeBegin":"","loginTimeEnd":"","loginUserId":"","name":"郭小路","name2":"","nickname":"","num":0,"openid":"","orgId":"2018LENoOyAYmq","orgName":"尉氏县","p1":"","p2":"","p3":"","p4":"","phone":"15664284736","phone2":"","policeName":"水坡派出所","policeOfficeId":"2018LEOhmYAJb4","privince":"","pwd":"2368c097c8c79b3a754e86ea9553803d","pwdSalt":"7fdb0","remark":"","roleRange":"usual","sex":1,"state":1,"updateId":"2019CaOrNh2wQu","updateIdFlag":0,"updateTime":"2020-12-04 09:48:18","userId":"a58c3eba6c8f4ab58935345bfeeda009","userName":"","usetType":"0"}
     * ebike_list : [{"alarmId":"","beginTimeInsurance":"2020-12-03","buyTime":"2020-12-01 00:00:00","buyTimeBegin":"","buyTimeEnd":"","createId":"2019CaOrNh2wQu","createIdFlag":0,"createTime":"2020-12-02 15:07:14","delFlag":0,"ebikeColor":"绿色","ebikeId":"17551c388e1a47539bf654a416530f63","ebikeNo":"WS113651","ebikePic1":"http://oss-public.hyntech.net/appUpload/20201202/2018LENoOyAYmq/f6dfa0aad1dc4672b2725b55546f147e.jpg","ebikePic2":"http://oss-public.hyntech.net/appUpload/20201202/2018LENoOyAYmq/41fd054c25ea40ec80b621215117d0b7.jpg","ebikePic3":"","ebikePic4":"","ebikePic5":"","ebikePic6":"","ebikePics":"","ebikeType":"金彭","ebikeTypeNum":"","enclosureId":"","endTimeInsurance":"2022-12-03","engineNo":"无","frameNo":"无","idNo":"","idNoPic1":"","idNoPic2":"","installState":1,"insuranceCoverage":120000,"insurancePrice":8000,"insuranceProductName":"电动车防盗保障服务D","insuranceState":"working","insuranceTime":"","invoicePic":"http://oss-public.hyntech.net/appUpload/20201202/2018LENoOyAYmq/7f6dbad84fbe4c498f147f53468edb63.jpg","isWlList":0,"keyword":"","lastDirection":"西出","lastLat":34.537885,"lastLng":114.271627,"locatorNo":"35831367","locatorPic":"http://oss-public.hyntech.net/appUpload/20201202/2018LENoOyAYmq/6ab148ac98f34e738122aa8e38b9947a.jpg","locatorUuid":"a80eabba3f834d28bf154d0aa5de1f40","lockFlag":0,"loginUserId":"","orderId":"201202150713330216222623","orgId":"2018LENoOyAYmq","orgName":"","payState":0,"phone":"","policeName":"","policeOfficeId":"2018LEOhmYAJb4","price":500000,"remark":"","ruleType":"","servicePackageOrgId":"","state":"normal","termRange":2,"type":2,"typeName":"电动三轮车","updateId":"a58c3eba6c8f4ab58935345bfeeda009","updateIdFlag":0,"updateTime":"2020-12-03 00:00:07","userAddress":"","userBirthDay":"","userId":"a58c3eba6c8f4ab58935345bfeeda009","userIdType":"","userName":"","userSex":0}]
     */

    private String location_refresh_time;
    private UserBean user;
    private List<EbikeListBean> ebike_list;

    public String getLocation_refresh_time() {
        return location_refresh_time;
    }

    public void setLocation_refresh_time(String location_refresh_time) {
        this.location_refresh_time = location_refresh_time;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<EbikeListBean> getEbike_list() {
        return ebike_list;
    }

    public void setEbike_list(List<EbikeListBean> ebike_list) {
        this.ebike_list = ebike_list;
    }

    public static class UserBean {
        /**
         * addr : 河南省尉氏县水坡乡齐岗一组
         * areaName :
         * birthdate : 1967-12-13 00:00:00
         * birthdateBegin :
         * birthdateEnd :
         * city :
         * company :
         * country :
         * county :
         * createId : 2019CaOrNh2wQu
         * createIdFlag : 0
         * createName :
         * createTime : 2020-12-02 15:07:11
         * ebikeId :
         * ebikeNo :
         * ebikeOrgId :
         * ebikeState :
         * editFlag :
         * email :
         * headimgurl :
         * idAddr : 河南省尉氏县水坡乡齐岗一组
         * idNo : 410223196712136512
         * idNoPic1 : http://hyntech-private.oss-cn-beijing.aliyuncs.com/appUpload/20201202/2018LENoOyAYmq/3008fb6c422344e2bf8b12a7aeb8d4c2.jpg?Expires=1607050101&OSSAccessKeyId=LTAIh24F0deeCk5J&Signature=a0WgSfEntiahcGQ2ytzBSKFiBcM%3D
         * idNoPic2 : http://hyntech-private.oss-cn-beijing.aliyuncs.com/appUpload/20201202/2018LENoOyAYmq/647c91124a2146dd99b7e4db371546be.jpg?Expires=1607050101&OSSAccessKeyId=LTAIh24F0deeCk5J&Signature=KBclEOQHPfNHkdGRpJsAYR3UW4I%3D
         * idType : 普通用户
         * insuranceState :
         * locatorNo :
         * locatorUuid :
         * loginTime : 2020-12-04 09:48:18
         * loginTimeBegin :
         * loginTimeEnd :
         * loginUserId :
         * name : 郭小路
         * name2 :
         * nickname :
         * num : 0
         * openid :
         * orgId : 2018LENoOyAYmq
         * orgName : 尉氏县
         * p1 :
         * p2 :
         * p3 :
         * p4 :
         * phone : 15664284736
         * phone2 :
         * policeName : 水坡派出所
         * policeOfficeId : 2018LEOhmYAJb4
         * privince :
         * pwd : 2368c097c8c79b3a754e86ea9553803d
         * pwdSalt : 7fdb0
         * remark :
         * roleRange : usual
         * sex : 1
         * state : 1
         * updateId : 2019CaOrNh2wQu
         * updateIdFlag : 0
         * updateTime : 2020-12-04 09:48:18
         * userId : a58c3eba6c8f4ab58935345bfeeda009
         * userName :
         * usetType : 0
         */

        private String addr;
        private String areaName;
        private String birthdate;
        private String birthdateBegin;
        private String birthdateEnd;
        private String city;
        private String company;
        private String country;
        private String county;
        private String createId;
        private int createIdFlag;
        private String createName;
        private String createTime;
        private String ebikeId;
        private String ebikeNo;
        private String ebikeOrgId;
        private String ebikeState;
        private String editFlag;
        private String email;
        private String headimgurl;
        private String idAddr;
        private String idNo;
        private String idNoPic1;
        private String idNoPic2;
        private String idType;
        private String insuranceState;
        private String locatorNo;
        private String locatorUuid;
        private String loginTime;
        private String loginTimeBegin;
        private String loginTimeEnd;
        private String loginUserId;
        private String name;
        private String name2;
        private String nickname;
        private int num;
        private String openid;
        private String orgId;
        private String orgName;
        private String p1;
        private String p2;
        private String p3;
        private String p4;
        private String phone;
        private String phone2;
        private String policeName;
        private String policeOfficeId;
        private String privince;
        private String pwd;
        private String pwdSalt;
        private String remark;
        private String roleRange;
        private int sex;
        private int state;
        private String updateId;
        private int updateIdFlag;
        private String updateTime;
        private String userId;
        private String userName;
        private String usetType;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getBirthdate() {
            return birthdate;
        }

        public void setBirthdate(String birthdate) {
            this.birthdate = birthdate;
        }

        public String getBirthdateBegin() {
            return birthdateBegin;
        }

        public void setBirthdateBegin(String birthdateBegin) {
            this.birthdateBegin = birthdateBegin;
        }

        public String getBirthdateEnd() {
            return birthdateEnd;
        }

        public void setBirthdateEnd(String birthdateEnd) {
            this.birthdateEnd = birthdateEnd;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
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

        public String getEbikeOrgId() {
            return ebikeOrgId;
        }

        public void setEbikeOrgId(String ebikeOrgId) {
            this.ebikeOrgId = ebikeOrgId;
        }

        public String getEbikeState() {
            return ebikeState;
        }

        public void setEbikeState(String ebikeState) {
            this.ebikeState = ebikeState;
        }

        public String getEditFlag() {
            return editFlag;
        }

        public void setEditFlag(String editFlag) {
            this.editFlag = editFlag;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
        }

        public String getIdAddr() {
            return idAddr;
        }

        public void setIdAddr(String idAddr) {
            this.idAddr = idAddr;
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

        public String getIdType() {
            return idType;
        }

        public void setIdType(String idType) {
            this.idType = idType;
        }

        public String getInsuranceState() {
            return insuranceState;
        }

        public void setInsuranceState(String insuranceState) {
            this.insuranceState = insuranceState;
        }

        public String getLocatorNo() {
            return locatorNo;
        }

        public void setLocatorNo(String locatorNo) {
            this.locatorNo = locatorNo;
        }

        public String getLocatorUuid() {
            return locatorUuid;
        }

        public void setLocatorUuid(String locatorUuid) {
            this.locatorUuid = locatorUuid;
        }

        public String getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(String loginTime) {
            this.loginTime = loginTime;
        }

        public String getLoginTimeBegin() {
            return loginTimeBegin;
        }

        public void setLoginTimeBegin(String loginTimeBegin) {
            this.loginTimeBegin = loginTimeBegin;
        }

        public String getLoginTimeEnd() {
            return loginTimeEnd;
        }

        public void setLoginTimeEnd(String loginTimeEnd) {
            this.loginTimeEnd = loginTimeEnd;
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

        public String getName2() {
            return name2;
        }

        public void setName2(String name2) {
            this.name2 = name2;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
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

        public String getP1() {
            return p1;
        }

        public void setP1(String p1) {
            this.p1 = p1;
        }

        public String getP2() {
            return p2;
        }

        public void setP2(String p2) {
            this.p2 = p2;
        }

        public String getP3() {
            return p3;
        }

        public void setP3(String p3) {
            this.p3 = p3;
        }

        public String getP4() {
            return p4;
        }

        public void setP4(String p4) {
            this.p4 = p4;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhone2() {
            return phone2;
        }

        public void setPhone2(String phone2) {
            this.phone2 = phone2;
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

        public String getPrivince() {
            return privince;
        }

        public void setPrivince(String privince) {
            this.privince = privince;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getPwdSalt() {
            return pwdSalt;
        }

        public void setPwdSalt(String pwdSalt) {
            this.pwdSalt = pwdSalt;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRoleRange() {
            return roleRange;
        }

        public void setRoleRange(String roleRange) {
            this.roleRange = roleRange;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
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

        public String getUsetType() {
            return usetType;
        }

        public void setUsetType(String usetType) {
            this.usetType = usetType;
        }
    }

    public static class EbikeListBean implements Serializable {
        /**
         * alarmId :
         * beginTimeInsurance : 2020-12-03
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
         * endTimeInsurance : 2022-12-03
         * engineNo : 无
         * frameNo : 无
         * idNo :
         * idNoPic1 :
         * idNoPic2 :
         * installState : 1
         * insuranceCoverage : 120000
         * insurancePrice : 8000
         * insuranceProductName : 电动车防盗保障服务D
         * insuranceState : working
         * insuranceTime :
         * invoicePic : http://oss-public.hyntech.net/appUpload/20201202/2018LENoOyAYmq/7f6dbad84fbe4c498f147f53468edb63.jpg
         * isWlList : 0
         * keyword :
         * lastDirection : 西出
         * lastLat : 34.537885
         * lastLng : 114.271627
         * locatorNo : 35831367
         * locatorPic : http://oss-public.hyntech.net/appUpload/20201202/2018LENoOyAYmq/6ab148ac98f34e738122aa8e38b9947a.jpg
         * locatorUuid : a80eabba3f834d28bf154d0aa5de1f40
         * lockFlag : 0
         * loginUserId :
         * orderId : 201202150713330216222623
         * orgId : 2018LENoOyAYmq
         * orgName :
         * payState : 0
         * phone :
         * policeName :
         * policeOfficeId : 2018LEOhmYAJb4
         * price : 500000
         * remark :
         * ruleType :
         * servicePackageOrgId :
         * state : normal
         * termRange : 2
         * type : 2
         * typeName : 电动三轮车
         * updateId : a58c3eba6c8f4ab58935345bfeeda009
         * updateIdFlag : 0
         * updateTime : 2020-12-03 00:00:07
         * userAddress :
         * userBirthDay :
         * userId : a58c3eba6c8f4ab58935345bfeeda009
         * userIdType :
         * userName :
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
        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

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
