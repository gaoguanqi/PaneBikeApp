package net.hyntech.common.model.vo;

import net.hyntech.common.model.entity.EbikeRegInfoEntity;

import java.io.Serializable;
import java.util.List;

public class BundleUserVo implements Serializable {
    public String userId = "";
    public String name = "";
    public String phone = "";
    public String address = "";
    public String userType = "";
    public String orgName = "";
    public String idCardAPath = "";
    public String idCardBPath = "";

    public List<EbikeRegInfoEntity.UserIdTypeBean> userIdType;

    @Override
    public String toString() {
        return "BundleUserVo{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", userType='" + userType + '\'' +
                ", orgName='" + orgName + '\'' +
                ", idCardAPath='" + idCardAPath + '\'' +
                ", idCardBPath='" + idCardBPath + '\'' +
                ", userIdType=" + userIdType +
                '}';
    }
}
