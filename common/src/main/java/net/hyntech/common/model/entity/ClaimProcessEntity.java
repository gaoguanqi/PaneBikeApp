package net.hyntech.common.model.entity;

import net.hyntech.baselib.http.BaseResponse;

import java.util.List;

public class ClaimProcessEntity extends BaseResponse<ClaimProcessEntity> {

    private List<ClaimProcessBean> claim_process;

    public List<ClaimProcessBean> getClaim_process() {
        return claim_process;
    }

    public void setClaim_process(List<ClaimProcessBean> claim_process) {
        this.claim_process = claim_process;
    }

    public static class ClaimProcessBean {
        /**
         * name : 报案
         * model : 如发生车辆被盗事故，请及时通过APP或打电话向当地公安机关进行报案，同时向保险公司进行备案，联系电话:95518
         */

        private String name;
        private String model;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }
    }
}
