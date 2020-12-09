package net.hyntech.common.ui.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.*
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.common.R
import net.hyntech.common.base.BaseActivity
import net.hyntech.common.vm.AccountSafetyViewModel

class AccountSafetyActivity: BaseActivity(),NavController.OnDestinationChangedListener {

    private var navController: NavController? = null
    val viewModel:AccountSafetyViewModel by lazy { ViewModelProvider(this).get(AccountSafetyViewModel::class.java) }

    override fun getLayoutId(): Int = R.layout.activity_account_safety

    override fun initData(savedInstanceState: Bundle?) {
        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })

        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtil.showToast(it)
        })
        viewModel.backEvent.observe(this, Observer {
            onFinish()
        })

    }

    override fun onSupportNavigateUp(): Boolean {
//        return super.onSupportNavigateUp()
        navController = findNavController(R.id.fragment_controller_safety)
        return navController?.navigateUp() ?: false
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        LogUtils.logGGQ("onDestinationChanged")
    }
}