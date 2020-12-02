package net.hyntech.usual.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.provider.ARouterConstants
import net.hyntech.common.ui.adapter.MyFragmentStateAdapter
import net.hyntech.usual.R
import net.hyntech.usual.databinding.ActivityHomeBinding
import net.hyntech.usual.ui.fragment.MainFragment
import net.hyntech.usual.vm.HomeViewModel

@Route(path = ARouterConstants.USUAL_HOME_PAGE)
class HomeActivity : BaseViewActivity<ActivityHomeBinding,HomeViewModel>() {

    private var lastBackPressedMillis: Long = 0

    private val viewModel by viewModels<HomeViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_home

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        val list: List<Fragment> = listOf(
            MainFragment.getInstance(viewModel),
            MainFragment.getInstance(viewModel)
        )


        binding.pager.isUserInputEnabled = false
        val adapter: MyFragmentStateAdapter = MyFragmentStateAdapter(this, list)
        binding.pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.pager.adapter = adapter
        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bnav.menu.getItem(position).isChecked = true
            }
        })
        binding.bnav.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.item_nav_main ->{
                    binding.pager.currentItem = 0
                }
                R.id.item_nav_mine ->{
                    binding.pager.currentItem = 1
                }
            }
            false
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (lastBackPressedMillis + 2000 > System.currentTimeMillis()) {
                //moveTaskToBack(true)
                this@HomeActivity.finish()
            } else {
                lastBackPressedMillis = System.currentTimeMillis()
                ToastUtil.showToast("再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


}
