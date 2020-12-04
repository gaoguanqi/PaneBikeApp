package net.hyntech.police.vm

import androidx.databinding.ObservableField
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.baselib.utils.ToastUtil

class HomeViewModel:BaseViewModel() {


    val messageCount: ObservableField<String> = ObservableField("0")


    fun onClickNotice(){
        onClickProxy {
            ToastUtil.showToast("点击消息")
        }
    }
}