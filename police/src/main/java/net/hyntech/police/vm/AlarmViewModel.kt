package net.hyntech.police.vm

import androidx.lifecycle.MutableLiveData
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.common.model.entity.AddValServiceEntity
import net.hyntech.common.model.entity.AlarmInfoEntity
import net.hyntech.common.model.repository.CommonRepository
import java.util.*

class AlarmViewModel:BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    private var pageNo: Int = 1
    private var pageSize: Int = 10
    var lastPage: Boolean = true

    val alarmInfoList: MutableLiveData<List<AlarmInfoEntity.AlarmInfoListBean>> = MutableLiveData()
    val alarmInfoRefresh: MutableLiveData<List<AlarmInfoEntity.AlarmInfoListBean>> = MutableLiveData()
    val alarmInfoLoadMore: MutableLiveData<List<AlarmInfoEntity.AlarmInfoListBean>> = MutableLiveData()



    fun getAlarmInfoList() {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getAlarmInfoList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(data.alarmInfoList.isNullOrEmpty()){
                    defUI.emptyEvent.call()
                    defUI.toastEvent.postValue("暂无数据！")
                }else{
                    alarmInfoList.postValue(data.alarmInfoList)
                }
            }
        })
    }

    fun onAlarmInfoRefresh() {
        pageNo = 1
        lastPage = true
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getAlarmInfoList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(!data.alarmInfoList.isNullOrEmpty()){
                    alarmInfoRefresh.postValue(data.alarmInfoList)
                }else{
                    defUI.emptyEvent.call()
                }
            }
        },isShowDialog = false,isShowToast = false)
    }

    fun onAlarmInfoLoadMore() {
        pageNo +=1
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getAlarmInfoList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                alarmInfoLoadMore.postValue(data.alarmInfoList)
            }
        },isShowDialog = false,isShowToast = false)
    }

}