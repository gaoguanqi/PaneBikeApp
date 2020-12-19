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

}