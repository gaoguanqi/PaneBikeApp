package net.hyntech.police.vm

import androidx.lifecycle.MutableLiveData
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.common.model.entity.DeviceInfoEntity
import net.hyntech.common.model.repository.CommonRepository
import java.util.*

class DeviceInfoViewModel:BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    val deviceList: MutableLiveData<List<DeviceInfoEntity.AtCollectorListBean>> = MutableLiveData()

    //搜索采集器
    fun searchDecive(id: String) {
        //201811131014741
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
//            params.put("collectorId",id)
            params.put("collectorId","201811131014741")
            repository.searchDecive(params)
        }, success = {
            it?.let {data ->
                deviceList.postValue(data.atCollectorList)
            }
        })
    }

}