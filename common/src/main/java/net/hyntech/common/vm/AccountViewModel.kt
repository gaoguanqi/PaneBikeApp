package net.hyntech.common.vm

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.JsonUtils
import net.hyntech.baselib.app.manager.SingleLiveEvent
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.common.global.Constants.ApiParams.phone
import net.hyntech.common.global.Constants.ApiParams.pwd
import net.hyntech.common.model.entity.CommonEntity
import net.hyntech.common.model.entity.TestEntity
import net.hyntech.common.model.repository.CommonRepository
import net.hyntech.common.utils.HttpParamsUtils
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.*

class AccountViewModel : BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }


    val bgDrawable: ObservableField<Drawable> = ObservableField()

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
            login()
        }

    }


    private fun login(){

        launchOnlyResult({
            val params:WeakHashMap<String,Any> = WeakHashMap()
            params.put("phone","13717591366")
            params.put("pwd","1111111")
            repository.loginPhone(params)
        }, success = {
            LogUtils.logGGQ("---success>>>${it?.toString()}")
        })



    }
}