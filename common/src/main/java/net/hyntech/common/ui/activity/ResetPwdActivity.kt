package net.hyntech.common.ui.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.databinding.ActivityResetPwdBinding
import net.hyntech.common.provider.ARouterConstants
import net.hyntech.common.vm.AccountViewModel
@Route(path = ARouterConstants.RESET_PWD_PAGE)
class ResetPwdActivity: BaseViewActivity<ActivityResetPwdBinding, AccountViewModel>() {

    private val viewModel by viewModels<AccountViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_reset_pwd

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }


    override fun initData(savedInstanceState: Bundle?) {
        setTitle<ResetPwdActivity>(UIUtils.getString(R.string.common_title_reset_pwd)).onBack<ResetPwdActivity>{
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
        viewModel.resetEvent.observe(this, Observer {
            ToastUtil.showToast("修改密码")
        })
    }
}