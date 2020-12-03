package net.hyntech.usual.vm

import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.baselib.utils.ToastUtil

class HomeViewModel : BaseViewModel() {



    fun onClickNotice(){
        onClickProxy {
            ToastUtil.showToast("点击消息")
        }
    }

}