package net.hyntech.common.model.vo;

import net.hyntech.common.model.entity.EbikeRegInfoEntity;
import net.hyntech.common.model.entity.ServiceSafeEntity;
import net.hyntech.common.model.entity.UserInfoEntity;

import java.util.List;

public class BundleEbikeEditVo {
    public int pos;
    public List<String> colorList;
    public List<EbikeRegInfoEntity.TypeBean> typeList;
    public List<ServiceSafeEntity.ServicePackageListBean> serviceList;
    public List<UserInfoEntity.EbikeListBean> ebikeList;

}
