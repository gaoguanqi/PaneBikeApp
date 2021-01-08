package net.hyntech.usual.vm

import androidx.lifecycle.MutableLiveData
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.common.model.entity.*
import net.hyntech.common.model.repository.CommonRepository
import java.util.*

class AddValViewModel:BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    private var pageNo:Int = 1
    private var pageSize:Int = 10
    var lastPage:Boolean = true

    val addValServiceList: MutableLiveData<List<AddValServiceEntity.ValueAddedServiceListBean>> = MutableLiveData()
    val addValServiceRefresh: MutableLiveData<List<AddValServiceEntity.ValueAddedServiceListBean>> = MutableLiveData()
    val addValServiceLoadMore: MutableLiveData<List<AddValServiceEntity.ValueAddedServiceListBean>> = MutableLiveData()

    fun getAddValServiceList(id:String?) {
        pageNo = 1
        lastPage = true
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("ebikeId",id)
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getAddValServiceList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(data.valueAddedServiceList.isNullOrEmpty()){
                    defUI.emptyEvent.call()
                    defUI.toastEvent.postValue("暂无数据！")
                }else{
                    addValServiceList.postValue(data.valueAddedServiceList)
                }
            }
        })
    }

    fun onAddValServiceRefresh(id:String?) {
        pageNo = 1
        lastPage = true
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("ebikeId",id)
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getAddValServiceList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(!data.valueAddedServiceList.isNullOrEmpty()){
                    addValServiceRefresh.postValue(data.valueAddedServiceList)
                }else{
                    defUI.emptyEvent.call()
                }
            }
        },isShowDialog = false,isShowToast = false)
    }

    fun onAddValServiceLoadMore(ebikeId:String?) {
        pageNo +=1
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("ebikeId",ebikeId)
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getAddValServiceList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                addValServiceLoadMore.postValue(data.valueAddedServiceList)
            }
        },isShowDialog = false,isShowToast = false)
    }
//------------------增值服务详情-------------------
    val serviceDetail: MutableLiveData<AddValDetailEntity> = MutableLiveData()

    fun getServiceDetails(ebikeId: String?, valueAddedServiceId: String?) {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("ebikeId",ebikeId)
            params.put("valueAddedServiceId",valueAddedServiceId)
            repository.getAddValServiceDetails(params)
        }, success = {
            it?.let { data ->
                serviceDetail.postValue(data)
            }
        })
    }

    val addValInfo: MutableLiveData<AddValInfoEntity> = MutableLiveData()
    fun getAddValInfo(ebikeId: String?, valueAddedServiceId: String?) {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("ebikeId",ebikeId)
            params.put("valueAddedServiceId",valueAddedServiceId)
            repository.getAddValInfo(params)
        }, success = {
            it?.let { data ->
                addValInfo.postValue(data)
            }
        })
    }
//----------我的增值服务---------------------------------------

    val myAddValServiceList: MutableLiveData<List<MyAddValServiceEntity.ListBean>> = MutableLiveData()
    val myAddValServiceRefresh: MutableLiveData<List<MyAddValServiceEntity.ListBean>> = MutableLiveData()
    val myAddValServiceLoadMore: MutableLiveData<List<MyAddValServiceEntity.ListBean>> = MutableLiveData()

    fun getMyAddValServiceList() {
        pageNo = 1
        lastPage = true
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getMyAddValServiceList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(data.list.isNullOrEmpty()){
                    defUI.emptyEvent.call()
                    defUI.toastEvent.postValue("暂无数据！")
                }else{
                    myAddValServiceList.postValue(data.list)
                }
            }
        })
    }


    fun onMyAddValServiceRefresh() {
        pageNo = 1
        lastPage = true
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getMyAddValServiceList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(!data.list.isNullOrEmpty()){
                    myAddValServiceRefresh.postValue(data.list)
                }else{
                    defUI.emptyEvent.call()
                }
            }
        },isShowDialog = false,isShowToast = false)
    }

    fun onMyAddValServiceLoadMore() {
        pageNo +=1
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getMyAddValServiceList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                myAddValServiceLoadMore.postValue(data.list)
            }
        },isShowDialog = false,isShowToast = false)
    }


}