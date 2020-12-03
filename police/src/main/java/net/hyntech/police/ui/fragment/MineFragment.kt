package net.hyntech.police.ui.fragment

import android.os.Bundle
import net.hyntech.common.base.BaseFragment
import net.hyntech.police.R
import net.hyntech.police.databinding.FragmentMineBinding
import net.hyntech.police.vm.HomeViewModel


class MineFragment(viewModel: HomeViewModel):BaseFragment<FragmentMineBinding,HomeViewModel>(viewModel){

    companion object {
        fun getInstance(viewModel: HomeViewModel): MineFragment {
            return MineFragment(viewModel)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

}