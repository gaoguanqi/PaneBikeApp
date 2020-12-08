package net.hyntech.common.vm

import net.hyntech.baselib.app.manager.SingleLiveEvent
import net.hyntech.baselib.base.BaseViewModel

class UserInfoViewModel : BaseViewModel() {


    val avatarEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val phoneEvent: SingleLiveEvent<Any> = SingleLiveEvent()


    fun onClickAvatar(){
        onClickProxy {
            avatarEvent.call()
        }
    }

    fun onClickPhone(){
        onClickProxy {
            phoneEvent.call()
        }
    }
}