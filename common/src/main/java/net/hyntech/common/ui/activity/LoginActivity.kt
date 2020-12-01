package net.hyntech.common.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.SPUtils
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.databinding.ActivityLoginBinding
import net.hyntech.common.db.AppDatabase
import net.hyntech.common.global.Constants
import net.hyntech.common.global.EventCode
import net.hyntech.common.vm.AccountViewModel

class LoginActivity: BaseViewActivity<ActivityLoginBinding, AccountViewModel>() {

    private val viewModel by viewModels<AccountViewModel>()



    override fun getLayoutId(): Int = R.layout.activity_login

    override fun bindViewModel(){
        binding.viewModel = viewModel
    }


    override fun hasStatusBarMode(): Boolean = true


    override fun initData(savedInstanceState: Bundle?) {
        val buildType:String? = getBundleString(Constants.GlobalValue.BUILD_TYPE)
        BaseApp.instance.setBuildType(buildType)
        when(buildType){
            Constants.BundleKey.EXTRA_USUAL ->{
                viewModel.bgDrawable.set(UIUtils.getDrawable(R.drawable.pic_usual))
            }

            Constants.BundleKey.EXTRA_POLICE ->{
                viewModel.bgDrawable.set(UIUtils.getDrawable(R.drawable.pic_police))
            }
        }

        viewModel.initUser()

        AppDatabase.getInstance(BaseApp.instance).userDao().apply {
            this.getCurrentUser()?.let {
                binding.tvOrgName.text = it.orgName
            }?:let {
                binding.tvOrgName.text = UIUtils.getString(R.string.common_choose_company)
            }
        }

        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })

        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtil.showToast(it)
        })

        viewModel.companyEvent.observe(this, Observer {
            startActivityForResult(Intent(LoginActivity@this,OrgActivity::class.java),EventCode.EVENT_CODE_ORG)
        })

        viewModel.forgetPwdEvent.observe(this, Observer {
            startActivity(Intent(LoginActivity@this,ForgetPwdActivity::class.java))
        })



        binding.etAccount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.account.set(s.toString())
            }
        })

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.password.set(s.toString())
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                EventCode.EVENT_CODE_ORG ->{
                    AppDatabase.getInstance(BaseApp.instance).userDao().apply {
                        this.getCurrentUser()?.let {
                            binding.tvOrgName.text = it.orgName
                        }
                    }
                }
            }
        }
    }

}