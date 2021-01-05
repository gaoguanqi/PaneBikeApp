package net.hyntech.police.ui.fragment

import android.os.Bundle
import net.hyntech.common.base.BaseFragment
import net.hyntech.police.R
import net.hyntech.police.databinding.FragmentSiteAddBinding
import net.hyntech.police.vm.ShopSiteViewModel

//增加网点
class SiteAddFragment(val viewModel: ShopSiteViewModel):BaseFragment<FragmentSiteAddBinding, ShopSiteViewModel>() {

    companion object {
        fun getInstance(viewModel: ShopSiteViewModel): SiteAddFragment {
            return SiteAddFragment(viewModel)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_site_add


    override fun bindViewModel() {
        binding.viewModel = viewModel
    }


    override fun initData(savedInstanceState: Bundle?) {

    }
}