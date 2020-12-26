package net.hyntech.police.ui.fragment

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseFragment
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.FragmentRegOwnerInfoBinding
import net.hyntech.police.ui.activity.EbikeRegisterActivity
import net.hyntech.police.vm.EbikeRegisterViewModel

class RegOwnerInfoFragment(val viewModel: EbikeRegisterViewModel):BaseFragment<FragmentRegOwnerInfoBinding,EbikeRegisterViewModel>() {

    private lateinit var act: EbikeRegisterActivity

    companion object {
        fun getInstance(viewModel: EbikeRegisterViewModel): RegOwnerInfoFragment {
            return RegOwnerInfoFragment(viewModel)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_reg_owner_info

    override fun initData(savedInstanceState: Bundle?) {
        act = activity as EbikeRegisterActivity
        view?.apply {
            this.findViewById<TextView>(R.id.tv_title).text = UIUtils.getString(CR.string.common_title_owner_info)
            this.findViewById<LinearLayout>(R.id.ll_left).setOnClickListener {
                onClickProxy {
                    //返回
                    act.onBack()
                }
            }

            this.findViewById<Button>(R.id.btn_next).setOnClickListener {
                onClickProxy {
                    act.onNextByIndex(2)
                }
            }
        }

    }


    override fun refReshData() {
        super.refReshData()
        viewModel.userInfo.value?.let {
            binding.tvType.text = "${it.idType}"
            binding.tvOrgName.text = "${it.orgName}"
        }
    }


    override fun bindViewModel() {
        binding.viewModel = viewModel
    }
}