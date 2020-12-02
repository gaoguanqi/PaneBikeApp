package net.hyntech.usual.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
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
            SeverInfoEntity(UIUtils.getString(CR.string.common_car_info),R.drawable.icon_car_locus),
            SeverInfoEntity(UIUtils.getString(CR.string.common_conve_service),R.drawable.icon_conve_service),
            SeverInfoEntity(UIUtils.getString(CR.string.common_call_police),R.drawable.icon_call_police),
            SeverInfoEntity(UIUtils.getString(CR.string.common_the_safe),R.drawable.icon_the_safe))

        binding.rvMain.layoutManager = GridLayoutManager(requireContext(),4)
        val adapter: SeverListAdapter = SeverListAdapter(requireContext(),CR.layout.item_sever_usual,list)
        binding.rvMain.adapter = adapter
    }

    override fun bindViewModel() {
        binding.viewModel = viewModel

    }


}