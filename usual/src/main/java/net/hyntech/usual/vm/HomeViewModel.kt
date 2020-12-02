package net.hyntech.usual.vm

import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.baselib.utils.ToastUtil

class HomeViewModel : BaseViewModel() {



    fun toast(){
        ToastUtil.showToast("点击")
    }
}