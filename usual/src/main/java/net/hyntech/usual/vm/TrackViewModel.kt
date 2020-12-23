package net.hyntech.usual.vm

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import net.hyntech.baselib.app.manager.SingleLiveEvent
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.common.model.entity.AddValServiceEntity
import net.hyntech.common.model.entity.EbikeTrackEntity
import net.hyntech.common.model.repository.CommonRepository
import java.util.*

class TrackViewModel:BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    //未购买增值服务
    val notBuyServiceEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val ebikeTrack: MutableLiveData<EbikeTrackEntity> = MutableLiveData()


    fun locationSearch(ebikeNo: String,startTime:String,endTime:String) {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("ebikeNo",ebikeNo)
            params.put("beginTime",startTime)
            params.put("endTime",endTime)
            repository.locationSearch(params)
        }, success = {
            it?.let {data ->
                ebikeTrack.postValue(data)
            }
        },error = {
            if(!TextUtils.isEmpty(it.code) && TextUtils.equals("VALUEADDEDSERVICE",it.code)){
                notBuyServiceEvent.call()
            }else{
                if(!TextUtils.isEmpty(it.errMsg)){
                    defUI.toastEvent.postValue(it.errMsg)
                }
            }
        })
    }
}