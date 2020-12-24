package net.hyntech.police.vm

import androidx.lifecycle.MutableLiveData
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.common.model.entity.EbikeTrackEntity
import net.hyntech.common.model.repository.CommonRepository
import java.util.*

class FindEbikeViewModel:BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    val ebikeInfo: MutableLiveData<EbikeTrackEntity.EbikeBean> = MutableLiveData()

    fun onFindEbike(ebikeNo: String,startTime:String = "",endTime:String = "") {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("ebikeNo",ebikeNo)
            params.put("beginTime",startTime)
            params.put("endTime",endTime)
            repository.findEbike(params)
        }, success = {
            it?.let {data ->
                ebikeInfo.postValue(data.ebike)
            }
        })
    }

    fun commitFindEbike(alarmId: String, address: String, remark: String) {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("alarmId",alarmId)
            params.put("foundAddr",address)
            params.put("foundRemark",remark)
            repository.commitFindEbike(params)
        }, success = {
            it?.let {
                defUI.toastEvent.postValue(it.msg)
            }
        })
    }

}