package net.hyntech.usual.vm

import android.text.TextUtils
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
    var keyword:String = ""

    //---------全部----------------------
    var serviceAllList: MutableLiveData<List<ConverServiceEntity.AtServiceShopListBean>> = MutableLiveData()
    var serviceAllRefresh: MutableLiveData<List<ConverServiceEntity.AtServiceShopListBean>> = MutableLiveData()
    var serviceAllLoadMore: MutableLiveData<List<ConverServiceEntity.AtServiceShopListBean>> = MutableLiveData()
    val emptyAllEvent by lazy { SingleLiveEvent<Boolean>() }
    fun getServiceAllList(id: String, lat: String, lng: String, shopType: String,search:String) {
        pageNo = 1
        lastPage = true
        this.keyword = search
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("orgId",id)
            params.put("lat",lat)
            params.put("lng",lng)
            params.put("keyword",keyword)
            params.put("shopType",shopType)
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getServiceShopList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(data.atServiceShopList.isNullOrEmpty()){
                    if(TextUtils.isEmpty(search)){
                        emptyAllEvent.call()
                    }
                    defUI.toastEvent.postValue("暂无数据！")
                }else{
                    serviceAllList.postValue(data.atServiceShopList)
                }
            }
        })
    }


    fun onServiceAllRefresh(id: String, lat: String, lng: String, shopType: String) {
        pageNo = 1
        lastPage = true
        this.keyword = ""
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("orgId",id)
            params.put("lat",lat)
            params.put("lng",lng)
            params.put("keyword",keyword)
            params.put("shopType",shopType)
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getServiceShopList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(!data.atServiceShopList.isNullOrEmpty()){
                    serviceAllRefresh.postValue(data.atServiceShopList)
                }else{
                    emptyAllEvent.call()
                }
            }
        },isShowDialog = false,isShowToast = false)
    }

    fun onServiceAllLoadMore(id: String, lat: String, lng: String, shopType: String) {
        pageNo +=1
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("orgId",id)
            params.put("lat",lat)
            params.put("lng",lng)
            params.put("keyword",keyword)
            params.put("shopType",shopType)
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getServiceShopList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                serviceAllLoadMore.postValue(data.atServiceShopList)
            }
        },isShowDialog = false,isShowToast = false)
    }


    //---------销售门店------------------
    var serviceStoreList: MutableLiveData<List<ConverServiceEntity.AtServiceShopListBean>> = MutableLiveData()
    var serviceStoreRefresh: MutableLiveData<List<ConverServiceEntity.AtServiceShopListBean>> = MutableLiveData()
    var serviceStoreLoadMore: MutableLiveData<List<ConverServiceEntity.AtServiceShopListBean>> = MutableLiveData()
    val emptyStoreEvent by lazy { SingleLiveEvent<Boolean>() }

    fun getServiceStoreList(id: String, lat: String, lng: String, shopType: String,search:String) {
        pageNo = 1
        lastPage = true
        this.keyword = search
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("orgId",id)
            params.put("lat",lat)
            params.put("lng",lng)
            params.put("keyword",keyword)
            params.put("shopType",shopType)
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getServiceShopList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(data.atServiceShopList.isNullOrEmpty()){
                    if(TextUtils.isEmpty(search)){
                        emptyStoreEvent.call()
                    }
                    defUI.toastEvent.postValue("暂无数据！")
                }else{
                    serviceStoreList.postValue(data.atServiceShopList)
                }
            }
        })
    }

    fun onServiceStoreRefresh(id: String, lat: String, lng: String, shopType: String) {
        pageNo = 1
        lastPage = true
        this.keyword = ""
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("orgId",id)
            params.put("lat",lat)
            params.put("lng",lng)
            params.put("keyword",keyword)
            params.put("shopType",shopType)
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getServiceShopList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(!data.atServiceShopList.isNullOrEmpty()){
                    serviceStoreRefresh.postValue(data.atServiceShopList)
                }else{
                    emptyStoreEvent.call()
                }
            }
        },isShowDialog = false,isShowToast = false)
    }

    fun onServiceStoreLoadMore(id: String, lat: String, lng: String, shopType: String) {
        pageNo +=1
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("orgId",id)
            params.put("lat",lat)
            params.put("lng",lng)
            params.put("keyword",keyword)
            params.put("shopType",shopType)
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getServiceShopList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                serviceStoreLoadMore.postValue(data.atServiceShopList)
            }
        },isShowDialog = false,isShowToast = false)
    }

    //---------维修站------------------
    var serviceFixList: MutableLiveData<List<ConverServiceEntity.AtServiceShopListBean>> = MutableLiveData()
    var serviceFixRefresh: MutableLiveData<List<ConverServiceEntity.AtServiceShopListBean>> = MutableLiveData()
    var serviceFixLoadMore: MutableLiveData<List<ConverServiceEntity.AtServiceShopListBean>> = MutableLiveData()
    val emptyFixEvent by lazy { SingleLiveEvent<Boolean>() }

    fun getServiceFixList(id: String, lat: String, lng: String, shopType: String,search:String) {
        pageNo = 1
        lastPage = true
        this.keyword = search
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("orgId",id)
            params.put("lat",lat)
            params.put("lng",lng)
            params.put("keyword",keyword)
            params.put("shopType",shopType)
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getServiceShopList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(data.atServiceShopList.isNullOrEmpty()){
                    if(TextUtils.isEmpty(search)){
                        emptyFixEvent.call()
                    }
                    defUI.toastEvent.postValue("暂无数据！")
                }else{
                    serviceFixList.postValue(data.atServiceShopList)
                }
            }
        })
    }

    fun onServiceFixRefresh(id: String, lat: String, lng: String, shopType: String) {
        pageNo = 1
        lastPage = true
        this.keyword = ""
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("orgId",id)
            params.put("lat",lat)
            params.put("lng",lng)
            params.put("keyword",keyword)
            params.put("shopType",shopType)
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getServiceShopList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(!data.atServiceShopList.isNullOrEmpty()){
                    serviceFixRefresh.postValue(data.atServiceShopList)
                }else{
                    emptyFixEvent.call()
                }
            }
        },isShowDialog = false,isShowToast = false)
    }

    fun onServiceFixLoadMore(id: String, lat: String, lng: String, shopType: String) {
        pageNo +=1
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("orgId",id)
            params.put("lat",lat)
            params.put("lng",lng)
            params.put("keyword",keyword)
            params.put("shopType",shopType)
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getServiceShopList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                serviceFixLoadMore.postValue(data.atServiceShopList)
            }
        },isShowDialog = false,isShowToast = false)
    }

    //---------充电站------------------
    var servicePowerList: MutableLiveData<List<ConverServiceEntity.AtServiceShopListBean>> = MutableLiveData()
    var servicePowerRefresh: MutableLiveData<List<ConverServiceEntity.AtServiceShopListBean>> = MutableLiveData()
    var servicePowerLoadMore: MutableLiveData<List<ConverServiceEntity.AtServiceShopListBean>> = MutableLiveData()
    val emptyPowerEvent by lazy { SingleLiveEvent<Boolean>() }

    fun getServicePowerList(id: String, lat: String, lng: String, shopType: String,search:String) {
        pageNo = 1
        lastPage = true
        this.keyword = search
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("orgId",id)
            params.put("lat",lat)
            params.put("lng",lng)
            params.put("keyword",keyword)
            params.put("shopType",shopType)
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getServiceShopList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(data.atServiceShopList.isNullOrEmpty()){
                    if(TextUtils.isEmpty(search)){
                        emptyPowerEvent.call()
                    }
                    defUI.toastEvent.postValue("暂无数据！")
                }else{
                    servicePowerList.postValue(data.atServiceShopList)
                }
            }
        })
    }

    fun onServicePowerRefresh(id: String, lat: String, lng: String, shopType: String) {
        pageNo = 1
        lastPage = true
        this.keyword = ""
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("orgId",id)
            params.put("lat",lat)
            params.put("lng",lng)
            params.put("keyword",keyword)
            params.put("shopType",shopType)
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getServiceShopList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(!data.atServiceShopList.isNullOrEmpty()){
                    servicePowerRefresh.postValue(data.atServiceShopList)
                }else{
                    emptyPowerEvent.call()
                }
            }
        },isShowDialog = false,isShowToast = false)
    }

    fun onServicePowerLoadMore(id: String, lat: String, lng: String, shopType: String) {
        pageNo +=1
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("orgId",id)
            params.put("lat",lat)
            params.put("lng",lng)
            params.put("keyword",keyword)
            params.put("shopType",shopType)
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getServiceShopList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                servicePowerLoadMore.postValue(data.atServiceShopList)
            }
        },isShowDialog = false,isShowToast = false)
    }
}