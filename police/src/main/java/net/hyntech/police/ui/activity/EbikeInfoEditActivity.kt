package net.hyntech.police.ui.activity

import android.os.Bundle
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.ActivityEbikeInfoEditBinding
import net.hyntech.police.vm.RegisterEditViewModel

/**
 * 车辆信息修改
 */
class EbikeInfoEditActivity:BaseViewActivity<ActivityEbikeInfoEditBinding, RegisterEditViewModel>() {

    private val viewModel by viewModels<RegisterEditViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_ebike_info_edit

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }


    override fun initData(savedInstanceState: Bundle?) {
        setTitle<EbikeInfoEditActivity>(UIUtils.getString(CR.string.common_title_edit_info)).onBack<EbikeInfoEditActivity> {
            onFinish()
        }
    }

}