package net.hyntech.usual.vm

import android.text.TextUtils
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.baidu.location.BDLocation
import com.baidu.mapapi.model.LatLng
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.app.manager.SingleLiveEvent
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.common.db.AppDatabase
import net.hyntech.common.model.entity.UserInfoEntity
import net.hyntech.common.model.repository.CommonRepository
import java.util.*

class HomeViewModel : BaseViewModel() {


    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    val messageCount: ObservableField<String> = ObservableField("0")
    val currentLatLng:MutableLiveData<BDLocation> = MutableLiveData()

    val userInfo: MutableLiveData<UserInfoEntity> = MutableLiveData()
    val currentEbike: ObservableField<UserInfoEntity.EbikeListBean> = ObservableField()
    val ebikeLockFlag: MutableLiveData<Int> = MutableLiveData()

    //item event
    val accountEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val carInfoEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val myOrderEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val myAddValEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val myMessageEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val changePwdEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val logoutEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val initLoadingEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun onClickNotice(){
        onClickProxy {
            ToastUtil.showToast("点击消息")
        }
    }


    fun getMessageCount() {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            repository.getMessageCount(params)
        }, success = {
            it?.let {data ->
                messageCount.set(data.messageCount)
            }
        },isShowDialog = false,isShowToast = false)
    }

    fun getUserInfo(isInit:Boolean = false){
        if(isInit){
            initLoadingEvent.postValue(true)
        }
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            repository.getUserInfo(params)
        }, success = {
            it?.let {data ->
                val ebike1 = UserInfoEntity.EbikeListBean()
                ebike1.ebikeNo = "11111"
                data.ebike_list.add(ebike1)

                AppDatabase.getInstance(BaseApp.instance).userDao().apply {
                    this.getCurrentUser()?.let {user ->
                        if(data.ebike_list != null && data.ebike_list.isNotEmpty()){
                            currentEbike.set(data.ebike_list.first())
                            if(TextUtils.isEmpty(user.ebikeNo)){
                                data.ebike_list.first().apply {
                                    this.isSelected = true
                                    user.ebikeNo = this.ebikeNo
                                    currentEbike.set(this)
                                }
                            }else{
                                data.ebike_list.forEach { item ->
                                    if(TextUtils.equals(user.ebikeNo,item.ebikeNo)){
                                        item.isSelected = true
                                        currentEbike.set(item)
                                    }else{
                                        item.isSelected = false
                                    }
                                }
                            }
                        }

                        data.user?.let { u ->
                            user.phone = u.phone
                            user.idCard = u.idNo
                            user.username = u.name
                            user.userType = u.usetType
                            user.avatar = u.headimgurl
                        }
                        this.updateUser(user)
                    }
                }
                userInfo.postValue(data)
            }
        },complete = {
            if(isInit){
                initLoadingEvent.postValue(false)
            }
        },isShowDialog = false,isShowToast = false)
    }


    fun onLockState() {
        currentEbike.get()?.let {ebike ->
            val lockFlag:Int = if(ebike.lockFlag == 0) 1 else 0
            launchOnlyResult({
                val params: WeakHashMap<String, Any> = WeakHashMap()
                params.put("ebikeId",ebike.ebikeId)
                params.put("lockFlag",lockFlag)
                repository.ebikeLock(params)
            }, success = {
                ebike.lockFlag = lockFlag
                ebikeLockFlag.postValue(ebike.lockFlag)
            },isShowDialog = false)
        }

    }


    fun onClickAccount(){
        onClickProxy {
            accountEvent.call()
        }
    }

    fun onClickCarInfo(){
        onClickProxy {
            carInfoEvent.call()
        }
    }

    fun onClickMyOrder(){
        onClickProxy {
            myOrderEvent.call()
        }
    }

    fun onClickMyAddVal(){
        onClickProxy {
            myAddValEvent.call()
        }
    }

    fun onClickMyMessage(){
        onClickProxy {
            myMessageEvent.call()
        }
    }

    fun onClickChangePwd(){
        onClickProxy {
            changePwdEvent.call()
        }
    }

    fun onLogout(){
        onClickProxy {
            logoutEvent.call()
        }
    }




}