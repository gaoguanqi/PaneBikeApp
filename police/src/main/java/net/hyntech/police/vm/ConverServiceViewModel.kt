package net.hyntech.police.vm

import androidx.lifecycle.MutableLiveData
import net.hyntech.baselib.app.manager.SingleLiveEvent
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.common.model.entity.ConverServiceEntity
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


    //---------便民服务------------------
    val deleteEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    fun deleteServiceShop(serviceShopId:String){
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("serviceShopId",serviceShopId)
            repository.deleteServiceShop(params)
        }, success = {
            deleteEvent.call()
        })
    }

    private var pageNo:Int = 1
    private var pageSize:Int = 10
    var lastPage:Boolean = true

    var serviceShopList: MutableLiveData<List<ConverServiceEntity.AtServiceShopListBean>> = MutableLiveData()
    var serviceShopRefresh: MutableLiveData<List<ConverServiceEntity.AtServiceShopListBean>> = MutableLiveData()
    var serviceShopLoadMore: MutableLiveData<List<ConverServiceEntity.AtServiceShopListBean>> = MutableLiveData()

    fun getServiceList(keyword:String,shopType:String,createId:String) {
        pageNo = 1
        lastPage = true
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("keyword",keyword)
            params.put("shopType",shopType)
            params.put("createId",createId)
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getServiceShopList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(data.atServiceShopList.isNullOrEmpty()){
                    defUI.emptyEvent.call()
                    defUI.toastEvent.postValue("暂无数据！")
                }else{
                    serviceShopList.postValue(data.atServiceShopList)
                }
            }
        })
    }

    fun onServiceListRefresh(keyword:String,shopType:String,createId:String) {
        pageNo = 1
        lastPage = true
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("keyword",keyword)
            params.put("shopType",shopType)
            params.put("createId",createId)
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getServiceShopList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(!data.atServiceShopList.isNullOrEmpty()){
                    serviceShopRefresh.postValue(data.atServiceShopList)
                }else{
                    defUI.emptyEvent.call()
                }
            }
        },isShowDialog = false,isShowToast = false)
    }

    fun onServiceListLoadMore(keyword:String,shopType:String,createId:String) {
        pageNo +=1
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("keyword",keyword)
            params.put("shopType",shopType)
            params.put("createId",createId)
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getServiceShopList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                serviceShopLoadMore.postValue(data.atServiceShopList)
            }
        },isShowDialog = false,isShowToast = false)
    }

}