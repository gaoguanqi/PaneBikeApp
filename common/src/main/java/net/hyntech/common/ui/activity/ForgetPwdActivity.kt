package net.hyntech.common.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.RegexUtils
import com.maple.player.widget.timer.MyCountDownTimerListener
import kotlinx.android.synthetic.main.activity_forget_pwd.*
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.databinding.ActivityForgetPwdBinding
import net.hyntech.common.db.AppDatabase
import net.hyntech.common.global.EventCode
import net.hyntech.common.vm.AccountViewModel
import net.hyntech.common.widget.view.ClearEditText

class ForgetPwdActivity: BaseViewActivity<ActivityForgetPwdBinding, AccountViewModel>() {

    private var tvOrgName:TextView? = null
    private var etPhone:ClearEditText? = null
    private var etNewPwd:ClearEditText? = null
    private var etConfirmPwd:ClearEditText? = null
    private var etVerifyCode:ClearEditText? = null

    private val viewModel by viewModels<AccountViewModel>()

    override fun hasEventKeyBack(): Boolean = true

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun getLayoutId(): Int = R.layout.activity_forget_pwd

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<ForgetPwdActivity>(UIUtils.getString(R.string.common_title_forget_pwd)).onBack<ForgetPwdActivity>{
            onFinish(true)
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

        tvOrgName = findViewById(R.id.tv_org_name)
        etPhone = findViewById(R.id.et_phone)
        etNewPwd = findViewById(R.id.et_new_pwd)
        etConfirmPwd = findViewById(R.id.et_confirm_pwd)
        etVerifyCode = findViewById(R.id.et_verify_code)
        btn_verify_code.setOnClickListener {
            onGetVerifyCode()
        }

        AppDatabase.getInstance(BaseApp.instance).userDao().apply {
            this.getCurrentUser()?.let {
                binding.tvOrgName.text = it.orgName
            }?:let {
                binding.tvOrgName.text = UIUtils.getString(R.string.common_choose_company)
            }
        }
        viewModel.companyEvent.observe(this, Observer {
            startActivityForResult(Intent(ForgetPwdActivity@this,OrgActivity::class.java), EventCode.EVENT_CODE_ORG)
        })
    }

    fun onGetVerifyCode(){
        onClickProxy {
            //val orgName:String? = tvOrgName?.text.toString()
            val phone:String? = etPhone?.text.toString().trim()
           // val newPwd:String? = etNewPwd?.text.toString().trim()
            //val confirmPwd:String? = etConfirmPwd?.text.toString().trim()

            if(TextUtils.isEmpty(phone) || !RegexUtils.isMobileSimple(phone)){
                ToastUtil.showToast("请输入正确的手机号")
                return@onClickProxy
            }

            viewModel.getVerifyCode(phone!!)


        }
    }


    fun onAskChangePwd(){
        onClickProxy {

        }
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

    override fun onKeyBack() {
        super.onKeyBack()
        onFinish(true)
    }
}