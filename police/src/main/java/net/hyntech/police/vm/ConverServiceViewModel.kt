package net.hyntech.police.vm

import androidx.lifecycle.MutableLiveData
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.common.model.entity.AlarmInfoEntity
import net.hyntech.common.model.entity.ServiceLoaderEntity
import net.hyntech.common.model.repository.CommonRepository
import java.util.*

class ConverServiceViewModel:BaseViewModel() {
    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    val loaderList: MutableLiveData<List<ServiceLoaderEntity.UploaderListBean>> = MutableLiveData()

    fun getServiceUploader(){
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            repository.serviceUploader(params)
        }, success = {
            it?.let {data ->
                loaderList.postValue(data.uploaderList)
            }
        },isShowDialog = false)
    }
}