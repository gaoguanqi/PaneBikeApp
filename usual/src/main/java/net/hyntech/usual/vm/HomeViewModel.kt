package net.hyntech.usual.vm

import android.text.TextUtils
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.common.db.AppDatabase
import net.hyntech.common.model.entity.UserInfoEntity
import net.hyntech.common.model.repository.CommonRepository
import java.util.*

class HomeViewModel : BaseViewModel() {


    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    val messageCount: ObservableField<String> = ObservableField("0")

    val userInfo: MutableLiveData<UserInfoEntity> = MutableLiveData()
    val currentEbike: ObservableField<UserInfoEntity.EbikeListBean> = ObservableField()


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
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            repository.getUserInfo(params)
        }, success = {
            it?.let {data ->
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
                        this.updateUser(user)
                    }
                }
                userInfo.postValue(data)
            }
        },isShowDialog = false,isShowToast = false)
    }

}