package net.hyntech.usual.vm

import androidx.lifecycle.MutableLiveData
import net.hyntech.baselib.app.manager.SingleLiveEvent
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.common.model.entity.*
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
                if(data.alarmExceptionLogList.isNullOrEmpty()){
                    defUI.emptyEvent.call()
                    defUI.toastEvent.postValue("暂无数据！")
                }else{
                    ebikeErrorList.postValue(data.alarmExceptionLogList)
                }
            }
        })
    }

    fun onEbikeRefresh() {
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
                }else{
                    defUI.emptyEvent.call()
                }
            }
        },isShowDialog = false,isShowToast = false)
    }

    fun onEbikeLoadMore() {
        pageNo +=1
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getEbikeErrorList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                ebikeListLoadMore.postValue(data.alarmExceptionLogList)
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
                if(data.atAlarmList.isNullOrEmpty()){
                    defUI.emptyEvent.call()
                    defUI.toastEvent.postValue("暂无数据！")
                }else{
                    alarmRecordList.postValue(data.atAlarmList)
                }
            }
        })
    }

    fun onAlarmRefresh() {
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
                }else{
                    defUI.emptyEvent.call()
                }
            }
        },isShowDialog = false,isShowToast = false)
    }

    fun onAlarmLoadMore() {
        pageNo +=1
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getAlarmRecordList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                alarmListLoadMore.postValue(data.atAlarmList)
            }
        },isShowDialog = false,isShowToast = false)
    }

    //-------一键报警------------------------------
    val alarmEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val alarmResultEvent: SingleLiveEvent<Any> = SingleLiveEvent()

    fun onClickAlarm(){
        onClickProxy {
            alarmEvent.call()
        }
    }

    //报警
    fun submitAlarm(params: WeakHashMap<String, Any>) {
        launchOnlyResult({
            repository.submitAlarm(params)
        }, success = {
            alarmResultEvent.call()
        },complete = {
            defUI.dismissDialog.call()
        },isShowDialog = false,isShowToast = true)
    }

    //--------------我的保单----------------------------------
    val myOrderList: MutableLiveData<List<MyOrderEntity.ListBean>> = MutableLiveData()
    val myOrderListRefresh: MutableLiveData<List<MyOrderEntity.ListBean>> = MutableLiveData()
    val myOrderListLoadMore: MutableLiveData<List<MyOrderEntity.ListBean>> = MutableLiveData()
    fun getMyOrderList() {
        pageNo = 1
        lastPage = true
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getMyOrderList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(data.list.isNullOrEmpty()){
                    defUI.emptyEvent.call()
                    defUI.toastEvent.postValue("暂无数据！")
                }else{
                    myOrderList.postValue(data.list)
                }
            }
        })
    }

    fun onMyOrderRefresh() {
        pageNo = 1
        lastPage = true
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getMyOrderList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(!data.list.isNullOrEmpty()){
                    myOrderListRefresh.postValue(data.list)
                }else{
                    defUI.emptyEvent.call()
                }
            }
        },isShowDialog = false,isShowToast = false)
    }

    fun onMyOrderLoadMore() {
        pageNo +=1
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            repository.getMyOrderList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                myOrderListLoadMore.postValue(data.list)
            }
        },isShowDialog = false,isShowToast = false)
    }



    //-----------支付----------------------
    fun onPay() {

    }

    //-----------服务包列表 （服务保障）-------------------------
    val serviceList: MutableLiveData<List<ServiceSafeEntity.ServicePackageListBean>> = MutableLiveData()
    val serviceListRefresh: MutableLiveData<List<ServiceSafeEntity.ServicePackageListBean>> = MutableLiveData()
    val serviceListLoadMore: MutableLiveData<List<ServiceSafeEntity.ServicePackageListBean>> = MutableLiveData()

    fun getServiceList(orgId:String?) {
        pageNo = 1
        lastPage = true
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            params.put("orgId",orgId)
            repository.getServiceList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(data.servicePackageList.isNullOrEmpty()){
                    defUI.emptyEvent.call()
                    defUI.toastEvent.postValue("暂无数据！")
                }else{
                    serviceList.postValue(data.servicePackageList)
                }
            }
        })
    }

    fun onServiceRefresh(orgId:String?) {
        pageNo = 1
        lastPage = true
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            params.put("orgId",orgId)
            repository.getServiceList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                if(!data.servicePackageList.isNullOrEmpty()){
                    serviceListRefresh.postValue(data.servicePackageList)
                }else{
                    defUI.emptyEvent.call()
                }
            }
        },isShowDialog = false,isShowToast = false)
    }

    fun onServiceLoadMore(orgId:String?) {
        pageNo +=1
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("PrmPageNo",pageNo)
            params.put("PrmItemsPerPage",pageSize)
            params.put("orgId",orgId)
            repository.getServiceList(params)
        }, success = {
            it?.let {data ->
                lastPage = data.page?.isLastPage?:true
                serviceListLoadMore.postValue(data.servicePackageList)
            }
        },isShowDialog = false,isShowToast = false)
    }

    //-------------保障信息---------------------------
    val serviceDetail: MutableLiveData<ServiceDetailEntity.AtServicePackageOrgBean> = MutableLiveData()
    fun getServiceDetails(id:String?) {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("servicePackageOrgId",id)
            repository.getServiceDetails(params)
        }, success = {
            it?.let { data ->
                serviceDetail.postValue(data.atServicePackageOrg)
            }
        },isShowToast = false,isShowDialog = false)
    }

    val claimProcess: MutableLiveData<List<ClaimProcessEntity.ClaimProcessBean>> = MutableLiveData()
    fun getClaimProcess(id:String?) {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("servicePackageOrgId",id)
            repository.getClaimProcess(params)
        }, success = {
            it?.let { data ->
                claimProcess.postValue(data.claim_process)
            }
        },isShowToast = false,isShowDialog = false)
    }

    val insuranceCoverange: MutableLiveData<String> = MutableLiveData()
    fun getInsuranceCoverange(id:String?) {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("servicePackageOrgId",id)
            repository.getInsuranceCoverange(params)
        }, success = {
            it?.let { data ->
                insuranceCoverange.postValue(data.insurance_coverange)
            }
        },isShowToast = false,isShowDialog = false)
    }

    //------------保障信息-----------------------
    val safeInfoList: MutableLiveData<List<SafeInfoEntity.ListBean>> = MutableLiveData()

    fun getSafeInfo(id:String?) {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("servicePackageOrgId",id)
            repository.getSafeInfo(params)
        }, success = {
            it?.let { data ->
                safeInfoList.postValue(data.list)
            }
        })
    }


    fun takeOrder(ebikeId: String, orderId: String, valueId: String) {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("ebikeId:",ebikeId)
            params.put("orderId",orderId)
            params.put("valueAddedServiceId",valueId)
            repository.takeOrder(params)
        }, success = {

        },isShowDialog = false,isShowToast = false)
    }
}