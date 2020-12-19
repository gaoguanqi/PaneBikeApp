package net.hyntech.usual.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_alarm_record.*
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.model.entity.MyOrderEntity
import net.hyntech.common.widget.decoration.SimpleItemDecoration
import net.hyntech.usual.R
import net.hyntech.usual.databinding.ActivityMyOrderBinding
import net.hyntech.common.R as CR
import net.hyntech.usual.ui.adapter.MyOrderAdapter
import net.hyntech.usual.vm.ControllerViewModel

//我的保单
class MyOrderActivity:BaseViewActivity<ActivityMyOrderBinding,ControllerViewModel>() {

    private val orderAdapter by lazy { MyOrderAdapter(this).apply {
        this.setListener(object :MyOrderAdapter.OnClickListener{
            override fun onBuyNowClick(item: MyOrderEntity.ListBean?) {
                item?.let {
                    ToastUtil.showToast(it.ebikeNo)
                    val price = item.realPrice.toString()
                    startActivity(Intent(this@MyOrderActivity,PayActivity::class.java).putExtra(Constants.BundleKey.EXTRA_PRICE,price))
                }
            }
        })
    } }


    private val viewModel by viewModels<ControllerViewModel>()


    override fun getLayoutId(): Int = R.layout.activity_my_order


    override fun bindViewModel() {
        binding.viewModel = viewModel
    }


    override fun initData(savedInstanceState: Bundle?) {
        setTitle<MyOrderActivity>(UIUtils.getString(CR.string.common_title_my_order)).onBack<MyOrderActivity> {
            onFinish()
        }

        refreshLayout.setEnableRefresh(true)//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(true)//是否启用上拉加载功能
        refreshLayout.setOnRefreshListener { ref ->
            onRefreshData()
        }

        refreshLayout.setOnLoadMoreListener { ref ->
            onLoadMoreData()
        }

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = orderAdapter

        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })

        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtil.showToast(it)
        })

        viewModel.defUI.emptyEvent.observe(this, Observer {
            finishRefresh()
            refreshLayout.visibility = View.GONE
            vsEmpty.inflate()
        })

        viewModel.myOrderList.observe(this, Observer {
            orderAdapter.setData(it)
        })
        viewModel.myOrderListRefresh.observe(this, Observer {
            orderAdapter.setData(it)
            finishRefresh()
        })
        viewModel.myOrderListLoadMore.observe(this, Observer {
            if(!it.isNullOrEmpty()){
                orderAdapter.updataList(it)
            }
            finishLoadMore()
        })
        viewModel.getMyOrderList()
    }


    private fun onRefreshData(){
        viewModel.onMyOrderRefresh()
    }

    private fun onLoadMoreData(){
        if(viewModel.lastPage){
            finishLoadMore()
        }else{
            viewModel.onMyOrderLoadMore()
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