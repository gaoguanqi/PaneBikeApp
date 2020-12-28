package net.hyntech.common.model.entity;

import net.hyntech.baselib.http.BaseResponse;

import java.util.List;

public class EbikeBrandEntity extends BaseResponse<EbikeBrandEntity> {

    private List<EbikeTypeListBean> ebikeTypeList;

    public List<EbikeTypeListBean> getEbikeTypeList() {
        return ebikeTypeList;
    }

    public void setEbikeTypeList(List<EbikeTypeListBean> ebikeTypeList) {
        this.ebikeTypeList = ebikeTypeList;
    }

    public static class EbikeTypeListBean {
        /**
         * name : 爱玛
         */

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
