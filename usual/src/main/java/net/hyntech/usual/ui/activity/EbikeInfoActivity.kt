package net.hyntech.usual.ui.activity

import android.os.Bundle
import net.hyntech.baselib.utils.Event
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.common.base.BaseActivity
import net.hyntech.common.global.EventCode
import net.hyntech.common.model.entity.UserInfoEntity
import net.hyntech.usual.R

class EbikeInfoActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_ebike_info

    override fun hasUsedEventBus(): Boolean = true
    override fun initData(savedInstanceState: Bundle?) {

    }


    override fun <T> onStickyEventBusDispense(event: Event<T>) {
        super.onStickyEventBusDispense(event)
        when(event.code){
            EventCode.EVENT_CODE_EBIKES ->{
                LogUtils.logGGQ("--event code:--${event.code}")
                val ebikeList = event.data as List<UserInfoEntity.EbikeListBean>
                ebikeList?.forEach {
                    LogUtils.logGGQ("车辆信息->>${it.ebikeNo}")
                }
            }
        }
    }
}