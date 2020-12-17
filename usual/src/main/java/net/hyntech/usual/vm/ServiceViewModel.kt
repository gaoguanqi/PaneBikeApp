package net.hyntech.usual.vm

import androidx.lifecycle.MutableLiveData
import net.hyntech.baselib.app.manager.SingleLiveEvent
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.common.model.entity.ConverServiceEntity
import net.hyntech.common.model.repository.CommonRepository
import java.util.*

//便民服务Vm
class ServiceViewModel:BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    private var pageNo:Int = 1
    private var pageSize:Int = 10
    var lastPage:Boolean = true

    var serviceList: MutableLiveData<List<ConverServiceEntity.AtServiceShopListBean>> = MutableLiveData()
    var serviceListRefresh: MutableLiveData<List<ConverServiceEntity.AtServiceShopListBean>> = MutableLiveData()
    var serviceListLoadMore: MutableLiveData<List<ConverServiceEntity.AtServiceShopListBean>> = MutableLiveData()

    fun getServiceShopList(id: String, lat: String, lng: String, shopType: String) {
        //ToastUtil.showToast("id->${id}--lat->${lat}--lng->${lng}--shopType->${shopType}")
        pageNo = 1
        lastPage = true
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("orgId",id)
            params.put("lat",lat)
            params.put("lng",lng)
            params.put("shopType",shopType)
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
                    serviceList.postValue(data.atServiceShopList)
                }
            }
        })
    }

    fun onServiceRefreshData(id: String, lat: String, lng: String, shopType: String) {
        pageNo = 1
        lastPage = true
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("orgId",id)
            params.put("lat",lat)
            params.put("lng",lng)
            params.put("shopType",shopType)
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getServiceShopList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(!data.atServiceShopList.isNullOrEmpty()){
                    serviceListRefresh.postValue(data.atServiceShopList)
                }else{
                    defUI.emptyEvent.call()
                }
            }
        },isShowDialog = false,isShowToast = false)
    }

    fun onServiceLoadMoreData(id: String, lat: String, lng: String, shopType: String) {
        pageNo +=1
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("orgId",id)
            params.put("lat",lat)
            params.put("lng",lng)
            params.put("shopType",shopType)
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getServiceShopList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                serviceListLoadMore.postValue(data.atServiceShopList)
            }
        },isShowDialog = false,isShowToast = false)
    }

}