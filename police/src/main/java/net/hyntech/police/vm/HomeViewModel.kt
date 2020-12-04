package net.hyntech.police.vm

import androidx.databinding.ObservableField
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.common.model.repository.CommonRepository
import java.util.*

class HomeViewModel:BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    val messageCount: ObservableField<String> = ObservableField("0")


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
}