package net.hyntech.common.model.entity;

import net.hyntech.baselib.http.BaseResponse;

import java.util.List;

public class ServiceLoaderEntity extends BaseResponse<ServiceLoaderEntity> {


    private List<UploaderListBean> uploaderList;

    public List<UploaderListBean> getUploaderList() {
        return uploaderList;
    }

    public void setUploaderList(List<UploaderListBean> uploaderList) {
        this.uploaderList = uploaderList;
    }

    public static class UploaderListBean {
        /**
         * createId : 2019FYO6dbhfni
         * createName : 李鹏
         */

        private String createId;
        private String createName;
        private boolean isSelected = false;

        public String getCreateId() {
            return createId;
        }

        public void setCreateId(String createId) {
            this.createId = createId;
        }

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
