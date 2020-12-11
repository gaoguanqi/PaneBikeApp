package net.hyntech.usual.vm

import androidx.lifecycle.MutableLiveData
import net.hyntech.baselib.app.manager.SingleLiveEvent
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.common.model.entity.AlarmRecordEntity
import net.hyntech.common.model.entity.CenterEntity
import net.hyntech.common.model.entity.EbikeErrorEntity
import net.hyntech.common.vm.CommonViewModel
import java.util.*

class ControllerViewModel:CommonViewModel() {
    //|PrmPageNo| Integer|否 |	页数（不传默认为1）|1-99999|
    //|PrmItemsPerPage| 	Integer|	是|	每页记录条数（不传默认为10）|1-99999|
    private var pageNo:Int = 1
    private var pageSize:Int = 10
    var lastPage:Boolean = true


    //--------------车辆异常信息------------------
    val ebikeErrorList: MutableLiveData<List<EbikeErrorEntity.AlarmExceptionLogListBean>> = MutableLiveData()
    val ebikeListRefresh: MutableLiveData<List<EbikeErrorEntity.AlarmExceptionLogListBean>> = MutableLiveData()
    val ebikeListLoadMore: MutableLiveData<List<EbikeErrorEntity.AlarmExceptionLogListBean>> = MutableLiveData()

    val ignoreEvent: SingleLiveEvent<Any> = SingleLiveEvent()


    fun getEbikeErrorList() {
        pageNo = 1
        lastPage = true
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getEbikeErrorList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(!data.alarmExceptionLogList.isNullOrEmpty()){
                    ebikeErrorList.postValue(data.alarmExceptionLogList)
                }
            }
        })
    }

    fun onEbikeRefreshData() {
        pageNo = 1
        lastPage = true
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getEbikeErrorList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(!data.alarmExceptionLogList.isNullOrEmpty()){
                    ebikeListRefresh.postValue(data.alarmExceptionLogList)
                }
            }
        },isShowDialog = false,isShowToast = false)
    }

    fun onEbikeLoadMoreData() {
        pageNo +=1
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getEbikeErrorList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(!data.alarmExceptionLogList.isNullOrEmpty()){
                    ebikeListLoadMore.postValue(data.alarmExceptionLogList)
                }
            }
        },isShowDialog = false,isShowToast = false)
    }

    fun ignoreEbikeError(id: String) {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("alarmLogId",id)
            repository.ignoreEbikeError(params)
        }, success = {
            defUI.toastEvent.postValue("操作成功！")
            ignoreEvent.call()
        },isShowToast = false)
    }


    //--------------报警记录------------------

    val alarmRecordList: MutableLiveData<List<AlarmRecordEntity.AtAlarmListBean>> = MutableLiveData()
    val alarmListRefresh: MutableLiveData<List<AlarmRecordEntity.AtAlarmListBean>> = MutableLiveData()
    val alarmListLoadMore: MutableLiveData<List<AlarmRecordEntity.AtAlarmListBean>> = MutableLiveData()

    fun getAlarmRecordList() {
        pageNo = 1
        lastPage = true
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getAlarmRecordList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(!data.atAlarmList.isNullOrEmpty()){
                    alarmRecordList.postValue(data.atAlarmList)
                }
            }
        })
    }

    fun onAlarmRefreshData() {
        pageNo = 1
        lastPage = true
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getAlarmRecordList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(!data.atAlarmList.isNullOrEmpty()){
                    alarmListRefresh.postValue(data.atAlarmList)
                }
            }
        },isShowDialog = false,isShowToast = false)
    }

    fun onAlarmLoadMoreData() {
        pageNo +=1
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getAlarmRecordList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(!data.atAlarmList.isNullOrEmpty()){
                    alarmListLoadMore.postValue(data.atAlarmList)
                }
            }
        },isShowDialog = false,isShowToast = false)
    }
}