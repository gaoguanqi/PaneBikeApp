package net.hyntech.common.model.entity;

import net.hyntech.baselib.http.BaseResponse;

import java.util.List;

public class AlarmRecordEntity extends BaseResponse<AlarmRecordEntity> {


    /**
     * atAlarmList : [{"alarmId":"2018CMSX5w0qbZ","alarmLevel":0,"alarmName":"宋康kang","alarmPhone":"18103795073","alarmType":0,"alarmUserId":"0ec6a8bf48f34a168c9ec635f8e50867","closeCaseFlag":0,"closeCaseRemark":"","closeCaseTime":"","closeCaseTimeBegin":"","closeCaseTimeEnd":"","closeCaseType":0,"createId":"0ec6a8bf48f34a168c9ec635f8e50867","createIdFlag":0,"createTime":"2018-03-13 18:23:57","ebikeId":"12313123213","ebikeNo":"789","ebikeUserName":"","foundAddr":"","foundCollectorId":"","foundFlag":0,"foundId":"","foundLat":0,"foundLng":0,"foundRemark":"","foundTime":"","foundTimeBegin":"","foundTimeEnd":"","lastAlarmTime":"2018-03-13 18:23:57","lastAlarmTimeBegin":"","lastAlarmTimeEnd":"","lat":"","lng":"","loginUserId":"","orgId":"2018AeLCKojsjO","orgName":"","relevantPic":"http://img.juimg.com/tuku/yulantu/120727/201995-120HG1030762.jpg","remark":"备注信息","state":"alarm","stolenAddr":"河南省郑州市高新区","stolenTime":"","stolenTimeBegin":"","stolenTimeEnd":"","updateId":"0ec6a8bf48f34a168c9ec635f8e50867","updateIdFlag":0,"updateTime":"2018-03-13 18:23:57"},{"alarmId":"2018CMSWT7ayCg","alarmLevel":0,"alarmName":"宋康kang","alarmPhone":"18103795073","alarmType":0,"alarmUserId":"0ec6a8bf48f34a168c9ec635f8e50867","closeCaseFlag":0,"closeCaseRemark":"","closeCaseTime":"","closeCaseTimeBegin":"","closeCaseTimeEnd":"","closeCaseType":0,"createId":"0ec6a8bf48f34a168c9ec635f8e50867","createIdFlag":0,"createTime":"2018-03-13 18:22:19","ebikeId":"12313123213","ebikeNo":"789","ebikeUserName":"","foundAddr":"","foundCollectorId":"","foundFlag":0,"foundId":"","foundLat":0,"foundLng":0,"foundRemark":"","foundTime":"","foundTimeBegin":"","foundTimeEnd":"","lastAlarmTime":"2018-03-13 18:22:19","lastAlarmTimeBegin":"","lastAlarmTimeEnd":"","lat":"","lng":"","loginUserId":"","orgId":"2018AeLCKojsjO","orgName":"","relevantPic":"http://img.juimg.com/tuku/yulantu/120727/201995-120HG1030762.jpg","remark":"备注信息","state":"alarm","stolenAddr":"河南省郑州市高新区","stolenTime":"","stolenTimeBegin":"","stolenTimeEnd":"","updateId":"0ec6a8bf48f34a168c9ec635f8e50867","updateIdFlag":0,"updateTime":"2018-03-13 18:22:19"},{"alarmId":"2018BENkNUzuAq","alarmLevel":0,"alarmName":"宋康kang","alarmPhone":"13460434566","alarmType":0,"alarmUserId":"0ec6a8bf48f34a168c9ec635f8e50867","closeCaseFlag":1,"closeCaseRemark":"","closeCaseTime":"2018-03-06 11:38:09","closeCaseTimeBegin":"","closeCaseTimeEnd":"","closeCaseType":0,"createId":"0ec6a8bf48f34a168c9ec635f8e50867","createIdFlag":0,"createTime":"2018-02-05 13:36:13","ebikeId":"52f2fab1efec4fecb35d33a4731869dd","ebikeNo":"123456123","ebikeUserName":"","foundAddr":"123456123","foundCollectorId":"1","foundFlag":1,"foundId":"2","foundLat":111.5656,"foundLng":326.23,"foundRemark":"987654","foundTime":"2018-02-05 13:45:47","foundTimeBegin":"","foundTimeEnd":"","lastAlarmTime":"2018-02-05 13:36:13","lastAlarmTimeBegin":"","lastAlarmTimeEnd":"","lat":"","lng":"","loginUserId":"","orgId":"2018AeLCKojsjO","orgName":"","relevantPic":"123456123","remark":"accessToken-5b543602fb3745ef9c2e751a7365a85b","state":"close","stolenAddr":"郑州金水区西史赵三期1号楼","stolenTime":"2018-03-06 13:40:08","stolenTimeBegin":"","stolenTimeEnd":"","updateId":"1","updateIdFlag":1,"updateTime":"2018-03-06 11:38:09"}]
     * page : {"firstPage":true,"lastPage":true,"list":[],"nextPage":1,"pageNo":1,"pageSize":15,"prePage":1,"totalCount":3,"totalPage":1}
     */

    private PageBean page;
    private List<AtAlarmListBean> atAlarmList;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<AtAlarmListBean> getAtAlarmList() {
        return atAlarmList;
    }

    public void setAtAlarmList(List<AtAlarmListBean> atAlarmList) {
        this.atAlarmList = atAlarmList;
    }

    public static class PageBean {
        /**
         * firstPage : true
         * lastPage : true
         * list : []
         * nextPage : 1
         * pageNo : 1
         * pageSize : 15
         * prePage : 1
         * totalCount : 3
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

    public static class AtAlarmListBean {
        /**
         * alarmId : 2018CMSX5w0qbZ
         * alarmLevel : 0
         * alarmName : 宋康kang
         * alarmPhone : 18103795073
         * alarmType : 0
         * alarmUserId : 0ec6a8bf48f34a168c9ec635f8e50867
         * closeCaseFlag : 0
         * closeCaseRemark :
         * closeCaseTime :
         * closeCaseTimeBegin :
         * closeCaseTimeEnd :
         * closeCaseType : 0
         * createId : 0ec6a8bf48f34a168c9ec635f8e50867
         * createIdFlag : 0
         * createTime : 2018-03-13 18:23:57
         * ebikeId : 12313123213
         * ebikeNo : 789
         * ebikeUserName :
         * foundAddr :
         * foundCollectorId :
         * foundFlag : 0
         * foundId :
         * foundLat : 0
         * foundLng : 0
         * foundRemark :
         * foundTime :
         * foundTimeBegin :
         * foundTimeEnd :
         * lastAlarmTime : 2018-03-13 18:23:57
         * lastAlarmTimeBegin :
         * lastAlarmTimeEnd :
         * lat :
         * lng :
         * loginUserId :
         * orgId : 2018AeLCKojsjO
         * orgName :
         * relevantPic : http://img.juimg.com/tuku/yulantu/120727/201995-120HG1030762.jpg
         * remark : 备注信息
         * state : alarm
         * stolenAddr : 河南省郑州市高新区
         * stolenTime :
         * stolenTimeBegin :
         * stolenTimeEnd :
         * updateId : 0ec6a8bf48f34a168c9ec635f8e50867
         * updateIdFlag : 0
         * updateTime : 2018-03-13 18:23:57
         */

        private String alarmId;
        private int alarmLevel;
        private String alarmName;
        private String alarmPhone;
        private int alarmType;
        private String alarmUserId;
        private int closeCaseFlag;
        private String closeCaseRemark;
        private String closeCaseTime;
        private String closeCaseTimeBegin;
        private String closeCaseTimeEnd;
        private int closeCaseType;
        private String createId;
        private int createIdFlag;
        private String createTime;
        private String ebikeId;
        private String ebikeNo;
        private String ebikeUserName;
        private String foundAddr;
        private String foundCollectorId;
        private int foundFlag;
        private String foundId;
        private int foundLat;
        private int foundLng;
        private String foundRemark;
        private String foundTime;
        private String foundTimeBegin;
        private String foundTimeEnd;
        private String lastAlarmTime;
        private String lastAlarmTimeBegin;
        private String lastAlarmTimeEnd;
        private String lat;
        private String lng;
        private String loginUserId;
        private String orgId;
        private String orgName;
        private String relevantPic;
        private String remark;
        private String state;
        private String stolenAddr;
        private String stolenTime;
        private String stolenTimeBegin;
        private String stolenTimeEnd;
        private String updateId;
        private int updateIdFlag;
        private String updateTime;

        public String getAlarmId() {
            return alarmId;
        }

        public void setAlarmId(String alarmId) {
            this.alarmId = alarmId;
        }

        public int getAlarmLevel() {
            return alarmLevel;
        }

        public void setAlarmLevel(int alarmLevel) {
            this.alarmLevel = alarmLevel;
        }

        public String getAlarmName() {
            return alarmName;
        }

        public void setAlarmName(String alarmName) {
            this.alarmName = alarmName;
        }

        public String getAlarmPhone() {
            return alarmPhone;
        }

        public void setAlarmPhone(String alarmPhone) {
            this.alarmPhone = alarmPhone;
        }

        public int getAlarmType() {
            return alarmType;
        }

        public void setAlarmType(int alarmType) {
            this.alarmType = alarmType;
        }

        public String getAlarmUserId() {
            return alarmUserId;
        }

        public void setAlarmUserId(String alarmUserId) {
            this.alarmUserId = alarmUserId;
        }

        public int getCloseCaseFlag() {
            return closeCaseFlag;
        }

        public void setCloseCaseFlag(int closeCaseFlag) {
            this.closeCaseFlag = closeCaseFlag;
        }

        public String getCloseCaseRemark() {
            return closeCaseRemark;
        }

        public void setCloseCaseRemark(String closeCaseRemark) {
            this.closeCaseRemark = closeCaseRemark;
        }

        public String getCloseCaseTime() {
            return closeCaseTime;
        }

        public void setCloseCaseTime(String closeCaseTime) {
            this.closeCaseTime = closeCaseTime;
        }

        public String getCloseCaseTimeBegin() {
            return closeCaseTimeBegin;
        }

        public void setCloseCaseTimeBegin(String closeCaseTimeBegin) {
            this.closeCaseTimeBegin = closeCaseTimeBegin;
        }

        public String getCloseCaseTimeEnd() {
            return closeCaseTimeEnd;
        }

        public void setCloseCaseTimeEnd(String closeCaseTimeEnd) {
            this.closeCaseTimeEnd = closeCaseTimeEnd;
        }

        public int getCloseCaseType() {
            return closeCaseType;
        }

        public void setCloseCaseType(int closeCaseType) {
            this.closeCaseType = closeCaseType;
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

        public String getEbikeUserName() {
            return ebikeUserName;
        }

        public void setEbikeUserName(String ebikeUserName) {
            this.ebikeUserName = ebikeUserName;
        }

        public String getFoundAddr() {
            return foundAddr;
        }

        public void setFoundAddr(String foundAddr) {
            this.foundAddr = foundAddr;
        }

        public String getFoundCollectorId() {
            return foundCollectorId;
        }

        public void setFoundCollectorId(String foundCollectorId) {
            this.foundCollectorId = foundCollectorId;
        }

        public int getFoundFlag() {
            return foundFlag;
        }

        public void setFoundFlag(int foundFlag) {
            this.foundFlag = foundFlag;
        }

        public String getFoundId() {
            return foundId;
        }

        public void setFoundId(String foundId) {
            this.foundId = foundId;
        }

        public int getFoundLat() {
            return foundLat;
        }

        public void setFoundLat(int foundLat) {
            this.foundLat = foundLat;
        }

        public int getFoundLng() {
            return foundLng;
        }

        public void setFoundLng(int foundLng) {
            this.foundLng = foundLng;
        }

        public String getFoundRemark() {
            return foundRemark;
        }

        public void setFoundRemark(String foundRemark) {
            this.foundRemark = foundRemark;
        }

        public String getFoundTime() {
            return foundTime;
        }

        public void setFoundTime(String foundTime) {
            this.foundTime = foundTime;
        }

        public String getFoundTimeBegin() {
            return foundTimeBegin;
        }

        public void setFoundTimeBegin(String foundTimeBegin) {
            this.foundTimeBegin = foundTimeBegin;
        }

        public String getFoundTimeEnd() {
            return foundTimeEnd;
        }

        public void setFoundTimeEnd(String foundTimeEnd) {
            this.foundTimeEnd = foundTimeEnd;
        }

        public String getLastAlarmTime() {
            return lastAlarmTime;
        }

        public void setLastAlarmTime(String lastAlarmTime) {
            this.lastAlarmTime = lastAlarmTime;
        }

        public String getLastAlarmTimeBegin() {
            return lastAlarmTimeBegin;
        }

        public void setLastAlarmTimeBegin(String lastAlarmTimeBegin) {
            this.lastAlarmTimeBegin = lastAlarmTimeBegin;
        }

        public String getLastAlarmTimeEnd() {
            return lastAlarmTimeEnd;
        }

        public void setLastAlarmTimeEnd(String lastAlarmTimeEnd) {
            this.lastAlarmTimeEnd = lastAlarmTimeEnd;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
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

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getStolenAddr() {
            return stolenAddr;
        }

        public void setStolenAddr(String stolenAddr) {
            this.stolenAddr = stolenAddr;
        }

        public String getStolenTime() {
            return stolenTime;
        }

        public void setStolenTime(String stolenTime) {
            this.stolenTime = stolenTime;
        }

        public String getStolenTimeBegin() {
            return stolenTimeBegin;
        }

        public void setStolenTimeBegin(String stolenTimeBegin) {
            this.stolenTimeBegin = stolenTimeBegin;
        }

        public String getStolenTimeEnd() {
            return stolenTimeEnd;
        }

        public void setStolenTimeEnd(String stolenTimeEnd) {
            this.stolenTimeEnd = stolenTimeEnd;
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
