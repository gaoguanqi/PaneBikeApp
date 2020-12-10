package net.hyntech.common.model.entity;

import net.hyntech.baselib.http.BaseResponse;

public class UploadImgEntity extends BaseResponse<UploadImgEntity> {
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
