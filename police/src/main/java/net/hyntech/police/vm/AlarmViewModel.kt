package net.hyntech.police.vm

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import net.hyntech.baselib.app.manager.SingleLiveEvent
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.common.model.entity.AddValServiceEntity
import net.hyntech.common.model.entity.AlarmInfoEntity
import net.hyntech.common.model.entity.UserInfoEntity
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


    val notifyPosition: MutableLiveData<Int> = MutableLiveData()
    val markRead: MutableLiveData<AlarmInfoEntity.AlarmInfoListBean> = MutableLiveData()

    fun onMarkRead(pos:Int,entity:AlarmInfoEntity.AlarmInfoListBean){
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("messageId",entity.messageId)
            params.put("messageType",entity.messageType)
            repository.alarmMarkRead(params)
        }, success = {
            entity.state = 1
            notifyPosition.postValue(pos)
        },complete = {
            markRead.postValue(entity)
        })
    }


    fun getAlarmInfoList(keyword:String) {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            if(!TextUtils.isEmpty(keyword)){
                params.put("keyword",keyword)
            }
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

    fun onAlarmInfoRefresh(keyword:String) {
        pageNo = 1
        lastPage = true
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            if(!TextUtils.isEmpty(keyword)){
                params.put("keyword",keyword)
            }
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

    fun onAlarmInfoLoadMore(keyword:String) {
        pageNo +=1
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            if(!TextUtils.isEmpty(keyword)){
                params.put("keyword",keyword)
            }
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