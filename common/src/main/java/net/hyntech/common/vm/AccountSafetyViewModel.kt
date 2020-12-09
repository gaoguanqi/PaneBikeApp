package net.hyntech.common.vm

import androidx.databinding.ObservableField
import net.hyntech.baselib.app.manager.SingleLiveEvent
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.baselib.utils.ToastUtil

class AccountSafetyViewModel:BaseViewModel() {
    val phoneEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val idCardEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val backEvent: SingleLiveEvent<Any> = SingleLiveEvent()

    val userPhone: ObservableField<String> = ObservableField("")
    val userIDCard: ObservableField<String> = ObservableField("")


    fun onClickCheckPhone(){
        onClickProxy {
            phoneEvent.call()
        }
    }
    fun onClickCheckIdCard(){
        onClickProxy {
            idCardEvent.call()
        }
    }

    fun onClickBack(){
        backEvent.call()
    }

    fun onClickNextPhone(){
        onClickProxy {
            ToastUtil.showToast("点击下一步 手机号")
        }
    }

    fun onClickNextIdCard(){
        onClickProxy {
            ToastUtil.showToast("点击下一步 身份证号")
        }
    }
}