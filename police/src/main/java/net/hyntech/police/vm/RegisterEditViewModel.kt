package net.hyntech.police.vm

import androidx.lifecycle.MutableLiveData
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.common.model.entity.UserInfoEntity
import net.hyntech.common.model.repository.CommonRepository
import java.util.*

class RegisterEditViewModel:BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    val userInfo: MutableLiveData<UserInfoEntity> = MutableLiveData()

    fun getUserInfo(idNo: String) {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("idNo",idNo)
            repository.getUserInfo(params)
        }, success = {
            it?.let {data ->
                userInfo.postValue(data)
            }
        })
    }

}