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
                val bean = ConverServiceEntity.AtServiceShopListBean()
                bean.addr = "开封市尉氏县"
                bean.createId = "2019FYO6dbhfni"
                bean.createIdFlag = 0
                bean.createName = "李鹏"
                bean.createTime = "2020-06-27 11:44:34"
                bean.delFlag = 1
                bean.distance = 0.0
                bean.keyword = ""
                bean.lat = 34.3741294
                bean.lng = 114.0133395
                bean.loginUserId = ""
                bean.name = ""
                bean.orgId = "2018LENoOyAYmq"
                bean.orgName = ""
                bean.phone = "13837865119"
                bean.relevantPic = "http://oss-public.hyntech.net/appUpload/20200627/2018LENoOyAYmq/c0501b2dbc464ff0be809c43e68b68e1.JPEG"
                bean.remark = ""
                bean.serviceShopId = "2020FaLshJWvTh"
                bean.shopName = "李鹏车行"
                bean.shopType = "销售门店,维修站,充电站"
                bean.state = 1
                bean.updateId = "2019FYO6dbhfni"
                bean.updateIdFlag = 0
                bean.updateTime = "2020-07-16 15:25:17"
                data.atServiceShopList.add(bean)

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