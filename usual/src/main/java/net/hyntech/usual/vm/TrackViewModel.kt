package net.hyntech.usual.vm

import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.common.model.repository.CommonRepository

class TrackViewModel:BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    fun locationSearch(ebikeNo: String) {
//        launchOnlyResult({
//            val params: WeakHashMap<String, Any> = WeakHashMap()
//            repository.locationSearch(params)
//        }, success = {
//
//        })
    }
}