package net.hyntech.common.model.entity;

import net.hyntech.baselib.http.BaseResponse;

public class InsuranceCoverangeEntity extends BaseResponse<InsuranceCoverangeEntity> {

    /**
     * insurance_coverange : 车辆购买在一年以内（含一年）赔付限额1200元，车辆购买在一年以上赔付限额600元
     */

    private String insurance_coverange;

    public String getInsurance_coverange() {
        return insurance_coverange;
    }

    public void setInsurance_coverange(String insurance_coverange) {
        this.insurance_coverange = insurance_coverange;
    }
}
