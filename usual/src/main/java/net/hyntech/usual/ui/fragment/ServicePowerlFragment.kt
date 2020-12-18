package net.hyntech.usual.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_service_power.*
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.common.base.BaseFragment
import net.hyntech.common.model.entity.ConverServiceEntity
import net.hyntech.usual.R
import net.hyntech.usual.databinding.FragmentServicePowerBinding
import net.hyntech.usual.ui.activity.ConverServiceActivity
import net.hyntech.usual.ui.adapter.ConverServiceAdapter
import net.hyntech.usual.vm.ServiceViewModel

//充电站
class ServicePowerlFragment(val id:String, val lat:String, val lng:String, val viewModel: ServiceViewModel):BaseFragment<FragmentServicePowerBinding,ServiceViewModel>() {

    private val shopType:String = "3"

    companion object {
        fun getInstance(id: String,lat: String,lng: String, viewModel: ServiceViewModel): ServicePowerlFragment {
            return ServicePowerlFragment(id,lat,lng,viewModel)
        }
    }

    private val serviceAdapter by lazy { ConverServiceAdapter(requireContext()).apply {
        this.setListener(object : ConverServiceAdapter.OnClickListener{ override fun onItemClick(item: ConverServiceEntity.AtServiceShopListBean?) {
            item?.let {
                ToastUtil.showToast("${it.shopName}")
            } }
            override fun onCallPhoneClick(phone: String) {
                (activity as ConverServiceActivity).callPhone(phone)
            } }) } }


    override fun getLayoutId(): Int = R.layout.fragment_service_power

    override fun initData(savedInstanceState: Bundle?) {
        refreshLayout.setEnableRefresh(true)//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(true)//是否启用上拉加载功能
        refreshLayout.setOnRefreshListener { ref ->
            onRefreshData()
        }

        refreshLayout.setOnLoadMoreListener { ref ->
            onLoadMoreData()
        }

        viewModel.emptyPowerEvent.observe(this, Observer {
            LogUtils.logGGQ("empty--->>${shopType}")
            finishRefresh()
            if(refreshLayout.visibility == View.VISIBLE){refreshLayout.visibility = View.GONE}
            if(vsEmpty.visibility == View.GONE){vsEmpty.visibility = View.VISIBLE}
        })

        viewModel.servicePowerList.observe(this, Observer {
            serviceAdapter.setData(it)
        })
        viewModel.servicePowerRefresh.observe(this, Observer {
            serviceAdapter.setData(it)
            finishRefresh()
        })
        viewModel.servicePowerLoadMore.observe(this, Observer {
            if(!it.isNullOrEmpty()){
                serviceAdapter.updataList(it)
            }
            finishLoadMore()
        })

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = serviceAdapter
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        viewModel.getServicePowerList(id,lat,lng,shopType,"")
    }

    override fun refReshData() {
        super.refReshData()
    }


    override fun bindViewModel() {
        binding.viewModel = viewModel
    }


    private fun onRefreshData(){
        viewModel.onServicePowerRefresh(id,lat,lng,shopType)
    }

    private fun onLoadMoreData(){
        if(viewModel.lastPage){
            finishLoadMore()
        }else{
            viewModel.onServicePowerLoadMore(id,lat,lng,shopType)
        }
    }

    //结束下拉刷新
    private fun finishRefresh() {
        refreshLayout?.let {
            if(it.isRefreshing)it.finishRefresh(300)
        }
    }

    //结束加载更多
    private fun finishLoadMore() {
        refreshLayout?.let {
            if(it.isLoading) it.finishLoadMore(300)
        }
    }
}