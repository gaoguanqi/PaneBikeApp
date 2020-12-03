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
import net.hyntech.common.widget.decoration.GridItemDecoration
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
            SeverInfoEntity(UIUtils.getString(CR.string.common_dot_manage),CR.drawable.icon_dot_manage),
            SeverInfoEntity(UIUtils.getString(CR.string.common_device_info),CR.drawable.icon_device_info),
            SeverInfoEntity(UIUtils.getString(CR.string.common_conve_service),CR.drawable.icon_conver_service2),
            SeverInfoEntity(UIUtils.getString(CR.string.common_car_up),CR.drawable.icon_car_info),
            SeverInfoEntity(UIUtils.getString(CR.string.common_up_record),CR.drawable.icon_up_record),
            SeverInfoEntity(UIUtils.getString(CR.string.common_find_car),CR.drawable.icon_find_car)
        )

        binding.rvMain.layoutManager = GridLayoutManager(requireContext(),3)
        binding.rvMain.addItemDecoration(GridItemDecoration(requireContext()))
        val adapter: SeverListAdapter = SeverListAdapter(requireContext(),CR.layout.item_sever_police,list)
        adapter.setListener(object :SeverListAdapter.OnClickListener{
            override fun onItemClick(item: SeverInfoEntity?) {
                item?.let {
                    ToastUtil.showToast(it.name)
                }
            }
        })
        binding.rvMain.adapter = adapter
    }


}