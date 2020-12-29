package net.hyntech.common.model.entity;


import net.hyntech.baselib.http.BaseResponse;

public class EbikeRegEntity extends BaseResponse<EbikeRegEntity> {

    private String ebikeId;

    public String getEbikeId() {
        return ebikeId;
    }

    public void setEbikeId(String ebikeId) {
        this.ebikeId = ebikeId;
    }
}
