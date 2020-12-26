package net.hyntech.police.ui.fragment

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseFragment
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.FragmentRegEbikeInfoBinding
import net.hyntech.police.ui.activity.EbikeRegisterActivity
import net.hyntech.police.vm.EbikeRegisterViewModel

class RegEbikeInfoFragment(val viewModel: EbikeRegisterViewModel):BaseFragment<FragmentRegEbikeInfoBinding,EbikeRegisterViewModel>() {
    private lateinit var act: EbikeRegisterActivity

    companion object {
        fun getInstance(viewModel: EbikeRegisterViewModel): RegEbikeInfoFragment {
            return RegEbikeInfoFragment(viewModel)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_reg_ebike_info

    override fun initData(savedInstanceState: Bundle?) {
        act = activity as EbikeRegisterActivity

        view?.apply {
            this.findViewById<TextView>(R.id.tv_title).text = UIUtils.getString(CR.string.common_title_ebike_info)
            this.findViewById<LinearLayout>(R.id.ll_left).setOnClickListener {
                onClickProxy {
                    //返回
                    act.onBack()
                }
            }
            this.findViewById<Button>(R.id.btn_commit).setOnClickListener {
                onClickProxy {

                }
            }
        }
    }

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }
}