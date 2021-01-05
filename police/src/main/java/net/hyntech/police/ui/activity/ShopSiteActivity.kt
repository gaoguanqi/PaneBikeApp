package net.hyntech.police.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.ui.adapter.MyFragmentStateAdapter
import net.hyntech.police.R
import net.hyntech.police.databinding.ActivityShopSiteBinding
import net.hyntech.police.ui.fragment.SiteAddFragment
import net.hyntech.police.vm.ShopSiteViewModel

class ShopSiteActivity:BaseViewActivity<ActivityShopSiteBinding,ShopSiteViewModel>() {

    private var currentItem:Int = 0

    private val viewModel by viewModels<ShopSiteViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_shop_site


    override fun bindViewModel() {
        binding.viewModel = viewModel
    }


    override fun initData(savedInstanceState: Bundle?) {

        currentItem = intent?.getIntExtra(Constants.BundleKey.EXTRA_INDEX,0)?:0

        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })

        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtil.showToast(it)
        })


        val list: List<Fragment> = listOf(
            SiteAddFragment.getInstance(viewModel),
            SiteAddFragment.getInstance(viewModel),
            SiteAddFragment.getInstance(viewModel)
        )

        binding.pager.isUserInputEnabled = false
        val adapter: MyFragmentStateAdapter = MyFragmentStateAdapter(this, list)
        binding.pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.pager.adapter = adapter
        binding.pager.currentItem = currentItem
    }
}