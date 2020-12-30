package net.hyntech.police.ui.activity

import android.os.Bundle
import android.widget.Button
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.police.R
import net.hyntech.police.databinding.ActivityOwnerInfoEditBinding
import net.hyntech.police.vm.RegisterEditViewModel

/**
 * 车主信息修改
 */
class OwnerInfoEditActivity:BaseViewActivity<ActivityOwnerInfoEditBinding, RegisterEditViewModel>() {
    private val viewModel by viewModels<RegisterEditViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_owner_info_edit


    override fun bindViewModel() {
        LogUtils.logGGQ("vm2->${viewModel}")
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {


        this.findViewById<Button>(R.id.btn_save)?.setOnClickListener {
            onClickProxy {

            }
        }

    }


}