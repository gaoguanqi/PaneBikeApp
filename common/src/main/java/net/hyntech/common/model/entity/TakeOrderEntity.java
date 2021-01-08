package net.hyntech.common.model.entity;

import net.hyntech.baselib.http.BaseResponse;

public class TakeOrderEntity extends BaseResponse<TakeOrderEntity> {
    private String outTradeNo;
    private String price;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
