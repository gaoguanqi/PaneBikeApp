package net.hyntech.usual.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.usual.R
import net.hyntech.common.R as CR
import net.hyntech.usual.databinding.ActivityConverServiceBinding
import net.hyntech.usual.vm.ServiceViewModel

//便民服务
class ConverServiceActivity:BaseViewActivity<ActivityConverServiceBinding,ServiceViewModel>() {

    private val viewModel by viewModels<ServiceViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_conver_service

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {

        setTitle<ConverServiceActivity>(UIUtils.getString(CR.string.common_title_conver_service)).onBack<ConverServiceActivity> {
            onFinish()
        }.onSide<ConverServiceActivity> {
            ToastUtil.showToast("搜索")
        }

        findViewById<ImageView>(R.id.iv_right)?.visibility = View.VISIBLE

        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })

        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtil.showToast(it)
        })
    }
}