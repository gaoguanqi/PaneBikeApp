package net.hyntech.usual.ui.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_main.*
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseFragment
import net.hyntech.common.model.entity.SeverInfoEntity
import net.hyntech.common.ui.adapter.SeverListAdapter
import net.hyntech.usual.R
import net.hyntech.common.R as CR
import net.hyntech.usual.databinding.FragmentMainBinding
import net.hyntech.usual.vm.HomeViewModel

class MainFragment(viewModel: HomeViewModel):BaseFragment<FragmentMainBinding,HomeViewModel>(viewModel) {


    companion object {
        fun getInstance(viewModel: HomeViewModel): MainFragment {
            return MainFragment(viewModel)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun initData(savedInstanceState: Bundle?) {
        val list:List<SeverInfoEntity> = arrayListOf(
            SeverInfoEntity(UIUtils.getString(CR.string.common_car_info),CR.drawable.icon_car_locus),
            SeverInfoEntity(UIUtils.getString(CR.string.common_conve_service),CR.drawable.icon_conver_service),
            SeverInfoEntity(UIUtils.getString(CR.string.common_call_police),CR.drawable.icon_call_police),
            SeverInfoEntity(UIUtils.getString(CR.string.common_the_safe),CR.drawable.icon_the_safe))

        binding.rvMain.layoutManager = GridLayoutManager(requireContext(),4)
        val adapter: SeverListAdapter = SeverListAdapter(requireContext(),CR.layout.item_sever_usual,list)
        adapter.setListener(object :SeverListAdapter.OnClickListener{
            override fun onItemClick(item: SeverInfoEntity?) {
                item?.let {
                    ToastUtil.showToast(it.name)
                }
            }
        })
        binding.rvMain.adapter = adapter
    }

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }


    override fun lazyLoadData() {
        super.lazyLoadData()
        LogUtils.logGGQ("lazyLoadData--->>")
    }

    override fun refReshData() {
        super.refReshData()
        LogUtils.logGGQ("refReshData--->>")
        viewModel.getMessageCount()

    }


}