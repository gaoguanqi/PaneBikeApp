package net.hyntech.common.model.entity;

import net.hyntech.baselib.http.BaseResponse;

public class PaymentConfirmEntity extends BaseResponse<PaymentConfirmEntity> {
    private String PWD001;

    public String getPWD001() {
        return PWD001;
    }

    public void setPWD001(String PWD001) {
        this.PWD001 = PWD001;
    }
}
