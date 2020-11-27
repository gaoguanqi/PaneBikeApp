package net.hyntech.usual.ui.activity

import android.os.Bundle
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.vm.AccountViewModel
import net.hyntech.usual.R
import net.hyntech.usual.databinding.ActivityForgetPwdBinding

class ForgetPwdActivity: BaseViewActivity<ActivityForgetPwdBinding, AccountViewModel>() {
    private val viewModel by viewModels<AccountViewModel>()

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun getLayoutId(): Int = R.layout.activity_forget_pwd

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<ForgetPwdActivity>(UIUtils.getString(R.string.common_title_forget_pwd)).onBack<ForgetPwdActivity>{
            onFinish()
        }
    }
}