package net.hyntech.police.ui.activity

import android.os.Bundle
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.ActivityEbikeRegisterBinding
import net.hyntech.police.vm.EbikeRegisterViewModel

class EbikeRegisterActivity:BaseViewActivity<ActivityEbikeRegisterBinding,EbikeRegisterViewModel>() {

    private val viewModel by viewModels<EbikeRegisterViewModel>()


    override fun getLayoutId(): Int = R.layout.activity_ebike_register

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<EbikeRegisterActivity>(UIUtils.getString(CR.string.common_title_ebike_register)).onBack<EbikeRegisterActivity> {
            onFinish()
        }.setRightTxt<EbikeRegisterActivity>("已有信息在册").onSide<EbikeRegisterActivity> {
            ToastUtil.showToast("已有信息在册")
        }
    }

}