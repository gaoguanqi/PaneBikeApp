package net.hyntech.police.vm

import android.text.TextUtils
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.baidu.location.BDLocation
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.app.manager.SingleLiveEvent
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.common.db.AppDatabase
import net.hyntech.common.model.entity.UserInfoEntity
import net.hyntech.common.model.repository.CommonRepository
import java.util.*

class HomeViewModel:BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    val messageCount: ObservableField<String> = ObservableField("0")
    
    val currentLatLng:MutableLiveData<BDLocation> = MutableLiveData()

    val userInfo: MutableLiveData<UserInfoEntity> = MutableLiveData()

    val avatarUrl: MutableLiveData<String> = MutableLiveData()

    //item event
    val accountEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val changePwdEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val logoutEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val initLoadingEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val noticeEvent: SingleLiveEvent<Any> = SingleLiveEvent()


    fun onClickNotice(){
        onClickProxy {
            noticeEvent.call()
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
                AppDatabase.getInstance(BaseApp.instance).userDao().apply {
                    this.getCurrentUser()?.let {user ->
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

    fun onClickAccount(){
        onClickProxy {
            accountEvent.call()
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