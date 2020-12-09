package net.hyntech.common.vm

import net.hyntech.baselib.app.manager.SingleLiveEvent
import net.hyntech.baselib.base.BaseViewModel

class AccountSafetyViewModel:BaseViewModel() {
    val phoneEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val idCardEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val backEvent: SingleLiveEvent<Any> = SingleLiveEvent()

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
}