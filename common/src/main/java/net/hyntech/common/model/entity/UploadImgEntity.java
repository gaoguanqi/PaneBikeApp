package net.hyntech.common.model.entity;

import net.hyntech.baselib.http.BaseResponse;

public class UploadImgEntity extends BaseResponse<UploadImgEntity> {
    private String imgUrl;
    private String idNoPic1;
    private String idNoPic2;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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
}
