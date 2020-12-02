package net.hyntech.police.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseFragment
import net.hyntech.common.model.entity.BannerEntity
import net.hyntech.common.model.entity.SeverInfoEntity
import net.hyntech.common.ui.adapter.MyBannerAdapter
import net.hyntech.common.ui.adapter.SeverListAdapter
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.FragmentMainBinding
import net.hyntech.police.vm.HomeViewModel

class MainFragment(viewModel: HomeViewModel):BaseFragment<FragmentMainBinding,HomeViewModel>(viewModel) {

    companion object {
        fun getInstance(viewModel: HomeViewModel): MainFragment {
            return MainFragment(viewModel)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }


    override fun initData(savedInstanceState: Bundle?) {
        val bannerList:List<BannerEntity> = arrayListOf(BannerEntity("banner1",R.drawable.pic_banner),BannerEntity("banner2",R.drawable.pic_banner))
        binding.banner.addBannerLifecycleObserver(this).setAdapter(MyBannerAdapter(requireContext(),bannerList)).setIndicator(
            CircleIndicator(requireContext())
        ).setOnBannerListener(object :OnBannerListener<BannerEntity>{
            override fun OnBannerClick(data: BannerEntity?, position: Int) {
                data?.let {
                    ToastUtil.showToast(it.name)
                }
            }
        })

        val list:List<SeverInfoEntity> = arrayListOf(
            SeverInfoEntity(UIUtils.getString(net.hyntech.common.R.string.common_car_info),R.drawable.icon_car_info),
            SeverInfoEntity(UIUtils.getString(net.hyntech.common.R.string.common_car_info),R.drawable.icon_car_info),
            SeverInfoEntity(UIUtils.getString(net.hyntech.common.R.string.common_car_info),R.drawable.icon_car_info),
            SeverInfoEntity(UIUtils.getString(net.hyntech.common.R.string.common_car_info),R.drawable.icon_car_info),
            SeverInfoEntity(UIUtils.getString(net.hyntech.common.R.string.common_car_info),R.drawable.icon_car_info),
            SeverInfoEntity(UIUtils.getString(net.hyntech.common.R.string.common_car_info),R.drawable.icon_car_info)
        )

        binding.rvMain.layoutManager = GridLayoutManager(requireContext(),3)
        val adapter: SeverListAdapter = SeverListAdapter(requireContext(),CR.layout.item_sever_police,list)
        binding.rvMain.adapter = adapter
    }


}