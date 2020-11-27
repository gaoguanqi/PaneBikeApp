package net.hyntech.common.vm

import android.text.TextUtils
import androidx.databinding.ObservableField
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.AppUtils
import net.hyntech.baselib.app.manager.SingleLiveEvent
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.common.model.repository.CommonRepository
import net.hyntech.common.provider.ARouterConstants

class AccountViewModel : BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }


    val verName: ObservableField<String> = ObservableField("")
    val companyEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val forgetPwdEvent: SingleLiveEvent<Any> = SingleLiveEvent()

    val org: ObservableField<String> = ObservableField()
    val account: ObservableField<String> = ObservableField()
    val password: ObservableField<String> = ObservableField()


    init {
        verName.set("当前版本：${AppUtils.getAppVersionName()}")
    }


    fun onChooseCompany() {
        onClickProxy {
            companyEvent.call()
        }
    }


    fun onForgetPassword() {
        onClickProxy {
            forgetPwdEvent.call()
        }
    }

    fun onLogin() {
        onClickProxy {
            val phone: String? = account.get()
            val pwd: String? = password.get()
            if (TextUtils.isEmpty(phone) && TextUtils.isEmpty(pwd)) {
                ToastUtil.showToast("请输入账号和密码")
            } else {
                login(phone!!, pwd!!)
            }
        }
    }

    private fun login(phone: String, pwd: String) {

//        launch(
//            {
//                val result: String = repository.loginPhone(phone, pwd).apply {
//
//                }
//            },
//            {
//                defUI.toastEvent.postValue("error:${it.code} -- ${it.errMsg}")
//            },
//            {
//                LogUtils.logGGQ("回调完成 complete")
//            }, false
//        )

        ARouter.getInstance().build(ARouterConstants.TEST_PAGE).navigation();

    }
}