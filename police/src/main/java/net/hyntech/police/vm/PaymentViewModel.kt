package net.hyntech.police.vm

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import net.hyntech.baselib.app.manager.SingleLiveEvent
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.common.model.entity.RegisterListEntity
import net.hyntech.common.model.repository.CommonRepository
import java.util.*

class PaymentViewModel : BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    val paymentEvent: SingleLiveEvent<Any> = SingleLiveEvent()

    fun paymentConfirm(id: String, pwd: String, remark: String) {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("ebikeId", id)
            params.put("pwd", pwd)
            params.put("remark", remark)
            repository.paymentConfirm(params)
        }, success = {
            it?.let { data ->
                //PWD001     | Integer|  是  |密码参数|0正确，1错误|
                if (TextUtils.equals(data.pwD001, "0")) {
                    //成功 眺到 登记记录(待付款)页
                    paymentEvent.call()
                } else {
                    defUI.toastEvent.postValue("密码错误，请重新输入或重置密码！")
                }
            }
        })
    }


    //-----登记记录 待缴费Pending-------------
    private var pageNo: Int = 1
    private var pageSize: Int = 10
    var lastPage: Boolean = true

    val registerList: MutableLiveData<List<RegisterListEntity.AtEbikeListBean>> = MutableLiveData()
    val registerListRefresh: MutableLiveData<List<RegisterListEntity.AtEbikeListBean>> =
        MutableLiveData()
    val registerListLoadMore: MutableLiveData<List<RegisterListEntity.AtEbikeListBean>> =
        MutableLiveData()

    fun getRegisterList(state: String, keyword: String) {
        pageNo = 1
        lastPage = true
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("keyword", keyword)
            params.put("state", state)
            params.put("PrmPageNo", pageNo)
            params.put("PrmItemsPerPage", pageSize)
            repository.getRegisterList(params)
        }, success = {
            it?.let { data ->
                lastPage = data.page?.isLastPage ?: true
                if (data.atEbikeList.isNullOrEmpty()) {
                    defUI.emptyEvent.call()
                    defUI.toastEvent.postValue("暂无数据！")
                } else {
                    registerList.postValue(data.atEbikeList)
                }
            }
        })
    }

    fun onRegisterListRefresh(state: String, keyword: String) {
        pageNo = 1
        lastPage = true
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("keyword", keyword)
            params.put("state", state)
            params.put("PrmPageNo", pageNo)
            params.put("PrmItemsPerPage", pageSize)
            repository.getRegisterList(params)
        }, success = {
            it?.let { data ->
                lastPage = data.page?.isLastPage ?: true
                if (!data.atEbikeList.isNullOrEmpty()) {
                    registerListRefresh.postValue(data.atEbikeList)
                } else {
                    defUI.emptyEvent.call()
                }
            }
        }, isShowDialog = false, isShowToast = false)
    }

    fun onRegisterListLoadMore(state: String, keyword: String) {
        pageNo += 1
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("keyword", keyword)
            params.put("state", state)
            params.put("PrmPageNo", pageNo)
            params.put("PrmItemsPerPage", pageSize)
            repository.getRegisterList(params)
        }, success = {
            it?.let { data ->
                lastPage = data.page?.isLastPage ?: true
                registerListLoadMore.postValue(data.atEbikeList)
            }
        }, isShowDialog = false, isShowToast = false)
    }

}