package net.hyntech.police.vm

import androidx.lifecycle.MutableLiveData
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.common.model.entity.CollectorListEntity
import net.hyntech.common.model.entity.EbikeErrorEntity
import net.hyntech.common.model.repository.CommonRepository
import java.util.*

class PointManageViewModel:BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }


    private var pageNo:Int = 1
    private var pageSize:Int = 10
    var lastPage:Boolean = true

    val collectorList: MutableLiveData<List<CollectorListEntity.AtCollectorListBean>> = MutableLiveData()
    val collectorListRefresh: MutableLiveData<List<CollectorListEntity.AtCollectorListBean>> = MutableLiveData()
    val collectorListLoadMore: MutableLiveData<List<CollectorListEntity.AtCollectorListBean>> = MutableLiveData()

    fun getCollectorList(keyword:String) {
        pageNo = 1
        lastPage = true
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("keyword",keyword)
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getCollectorList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(data.atCollectorList.isNullOrEmpty()){
                    defUI.emptyEvent.call()
                    defUI.toastEvent.postValue("暂无数据！")
                }else{
                    collectorList.postValue(data.atCollectorList)
                }
            }
        })
    }


    fun onCollectorListRefresh(keyword:String) {
        pageNo = 1
        lastPage = true
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("keyword",keyword)
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getCollectorList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(!data.atCollectorList.isNullOrEmpty()){
                    collectorListRefresh.postValue(data.atCollectorList)
                }else{
                    defUI.emptyEvent.call()
                }
            }
        },isShowDialog = false,isShowToast = false)
    }

    fun onCollectorListLoadMore(keyword:String) {
        pageNo +=1
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("keyword",keyword)
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getCollectorList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                collectorListLoadMore.postValue(data.atCollectorList)
            }
        },isShowDialog = false,isShowToast = false)
    }

}