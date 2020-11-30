package net.hyntech.common.model.entity;

import net.hyntech.baselib.http.BaseResponse;

import java.util.List;

public class CenterEntity extends BaseResponse<CenterEntity>  {

    private AppUpdateUsualBean app_update_usual;
    private AppUpdatePoliceBean app_update_police;
    private List<OrgListBean> org_list;

    public static class AppUpdateUsualBean {

        private AppBean app_ios;
        private AppBean app_android;

        public AppBean getApp_ios() {
            return app_ios;
        }

        public void setApp_ios(AppBean app_ios) {
            this.app_ios = app_ios;
        }

        public AppBean getApp_android() {
            return app_android;
        }

        public void setApp_android(AppBean app_android) {
            this.app_android = app_android;
        }
    }

    public static class AppUpdatePoliceBean {

        private AppBean app_ios;
        private AppBean app_android;

        public AppBean getApp_ios() {
            return app_ios;
        }

        public void setApp_ios(AppBean app_ios) {
            this.app_ios = app_ios;
        }

        public AppBean getApp_android() {
            return app_android;
        }

        public void setApp_android(AppBean app_android) {
            this.app_android = app_android;
        }
    }

    public static class OrgListBean {

        private String orgId;
        private String orgName;
        private String api_url;
        private String appweb_url;

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

        public String getApi_url() {
            return api_url;
        }

        public void setApi_url(String api_url) {
            this.api_url = api_url;
        }

        public String getAppweb_url() {
            return appweb_url;
        }

        public void setAppweb_url(String appweb_url) {
            this.appweb_url = appweb_url;
        }


    }

    public static class AppBean{
        private String version;
        private int versionCode;
        private String url;
        private String update_content;
        private String update_time;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUpdate_content() {
            return update_content;
        }

        public void setUpdate_content(String update_content) {
            this.update_content = update_content;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
    }

    public AppUpdateUsualBean getApp_update_usual() {
        return app_update_usual;
    }

    public void setApp_update_usual(AppUpdateUsualBean app_update_usual) {
        this.app_update_usual = app_update_usual;
    }

    public AppUpdatePoliceBean getApp_update_police() {
        return app_update_police;
    }

    public void setApp_update_police(AppUpdatePoliceBean app_update_police) {
        this.app_update_police = app_update_police;
    }

    public List<OrgListBean> getOrg_list() {
        return org_list;
    }

    public void setOrg_list(List<OrgListBean> org_list) {
        this.org_list = org_list;
    }
}
