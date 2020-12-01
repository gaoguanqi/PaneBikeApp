package net.hyntech.common.vm

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.Utils
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.app.config.Config
import net.hyntech.baselib.app.manager.SingleLiveEvent
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.db.AppDatabase
import net.hyntech.common.db.dao.User
import net.hyntech.common.global.Constants
import net.hyntech.common.global.Global
import net.hyntech.common.model.entity.CenterEntity
import net.hyntech.common.model.repository.CommonRepository
import java.lang.Exception
import java.util.*

class AccountViewModel : BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }


    val bgDrawable: ObservableField<Drawable> = ObservableField()

    val verName: ObservableField<String> = ObservableField("")
    val companyEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val forgetPwdEvent: SingleLiveEvent<Any> = SingleLiveEvent()

    val account: ObservableField<String> = ObservableField()
    val password: ObservableField<String> = ObservableField()

    val orgData: MutableLiveData<List<CenterEntity.OrgListBean>> = MutableLiveData()

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

    fun getOrgList() {
        launchOnlyResult({
            repository.getOrgList()
        }, success = {
            it?.let {data ->
                orgData.value = data.org_list
            }
        })
    }

    fun initUser() {
        AppDatabase.getInstance(BaseApp.instance).userDao().apply {
            if(this.getCurrentUser() == null){
                val user:User = User()
                user.orgName = UIUtils.getString(R.string.common_choose_company)
                user.userType = "0"
                user.accessToken = SPUtils.getInstance(BaseApp.instance.getAppPackage()).getString(Constants.SaveInfoKey.ACCESS_TOKEN,"")
                user.buildType = BaseApp.instance.getBuildType()
                user.apiUrl = Global.BASE_URL
                this.insertUser(user)
            }else{
                Global.BASE_URL = this.getCurrentUser()?.apiUrl!!
            }
        }
    }
}