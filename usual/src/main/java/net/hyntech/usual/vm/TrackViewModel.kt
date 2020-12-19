package net.hyntech.usual.vm

import android.text.TextUtils
import net.hyntech.baselib.app.manager.SingleLiveEvent
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.common.model.repository.CommonRepository
import java.util.*

class TrackViewModel:BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    //未购买增值服务
    val notBuyServiceEvent: SingleLiveEvent<Any> = SingleLiveEvent()

    fun locationSearch(ebikeNo: String,startTime:String,endTime:String) {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("ebikeNo",ebikeNo)
            params.put("beginTime",startTime)
            params.put("endTime",endTime)
            repository.locationSearch(params)
        }, success = {

        },error = {
            if(!TextUtils.isEmpty(it.code) && TextUtils.equals("VALUEADDEDSERVICE",it.code)){
                notBuyServiceEvent.call()
            }
        })
    }
}