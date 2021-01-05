package net.hyntech.police.vm

import androidx.lifecycle.MutableLiveData
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.common.model.entity.PositionData

class ShopSiteViewModel:BaseViewModel() {

    val position: MutableLiveData<PositionData> = MutableLiveData()



}