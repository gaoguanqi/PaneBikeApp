package net.hyntech.usual.ui.fragment

import android.os.Bundle
import com.blankj.utilcode.util.BarUtils
import net.hyntech.common.base.BaseFragment
import net.hyntech.usual.R
import net.hyntech.usual.databinding.FragmentMineBinding
import net.hyntech.usual.vm.HomeViewModel

class MineFragment(viewModel: HomeViewModel):BaseFragment<FragmentMineBinding,HomeViewModel>(viewModel) {

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