package net.hyntech.common.ui.activity

import android.os.Bundle
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.databinding.ActivityForgetPwdBinding
import net.hyntech.common.vm.AccountViewModel

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