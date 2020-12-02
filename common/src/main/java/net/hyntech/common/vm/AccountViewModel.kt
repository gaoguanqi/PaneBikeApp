package net.hyntech.common.vm

import android.graphics.drawable.Drawable
import android.text.TextUtils
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
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.db.AppDatabase
import net.hyntech.common.db.dao.User
import net.hyntech.common.global.Constants
import net.hyntech.common.global.Global
import net.hyntech.common.model.entity.CenterEntity
import net.hyntech.common.model.repository.CommonRepository
import okhttp3.internal.userAgent
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
    val orgDataList:MutableList<CenterEntity.OrgListBean> = arrayListOf()
    val searchOrgList:MutableList<CenterEntity.OrgListBean> = arrayListOf()

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
            AppDatabase.getInstance(BaseApp.instance).userDao().getCurrentUser()?.let {
                if(TextUtils.isEmpty(it.orgId)){
                    ToastUtil.showToast("请选择公安局")
                    return@onClickProxy
                }
            }

            val phone = account.get()
            val pwd = password.get()
            if(TextUtils.isEmpty(phone) || TextUtils.isEmpty(pwd)){
                ToastUtil.showToast("请输入账号和密码")
                return@onClickProxy
            }

            login(phone!!,pwd!!)
        }
    }


    private fun login(phone:String,pwd:String){
        launchOnlyResult({
            val params:WeakHashMap<String,Any> = WeakHashMap()
            params.put("phone","410727190810161215")
            params.put("pwd","民用")

//            params.put("phone","410205198203062517")
//            params.put("pwd","警用")

            repository.loginPhone(params)
        }, success = {
            LogUtils.logGGQ("---success>>>${it?.toString()}")
            it?.let {u ->
                AppDatabase.getInstance(BaseApp.instance).userDao().apply {
                    this.getCurrentUser()?.let { user ->
                        user.username = phone
                        user.password = pwd
                        user.userId = u.userId
                        user.accessToken = u.accessToken
                        user.expiresIn = u.expiresIn
                        this.updateUser(user)
                    }
                }
            }

        })
    }

    fun getOrgList() {
        launchOnlyResult({
            repository.getOrgList()
        }, success = {
            it?.let {data ->
                orgDataList.clear()
                orgDataList.addAll(data.org_list)
                orgData.value = orgDataList
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

    fun searchInput(input: String) {
        searchOrgList.clear()
        orgData.value?.forEach {
            if(it.orgName.contains(input)){
                searchOrgList.add(it)
            }
        }
        orgData.value = searchOrgList
        if(searchOrgList.isEmpty()){
            ToastUtil.showToast("未搜索到相关内容")
        }
    }

    fun searchRecover() {
        orgData.value = orgDataList
    }

    fun openOrg(){
        onClickProxy {
            companyEvent.call()
        }
    }

    fun getVerifyCode(phone: String) {
        launchOnlyResult({
            val params:WeakHashMap<String,Any> = WeakHashMap()
            params.put("phone",phone)
            params.put("resetPwd","qqqqqq")
            params.put("length","4")
            repository.getVerifyCode(params)
        }, success = {
            ToastUtil.showToast(it?.msg)

        }, error = {

        })
    }
}