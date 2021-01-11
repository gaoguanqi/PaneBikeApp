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
import net.hyntech.common.model.entity.MyAddValServiceEntity
import net.hyntech.usual.R
import net.hyntech.common.R as CR
import net.hyntech.usual.databinding.ActivityMyAddvalServiceBinding
import net.hyntech.usual.ui.adapter.MyAddValServiceAdapter
import net.hyntech.usual.vm.AddValViewModel

class MyAddValServiceActivity:BaseViewActivity<ActivityMyAddvalServiceBinding,AddValViewModel>() {


    private val myAddValAdapter by lazy { MyAddValServiceAdapter(this).apply {
        this.setListener(object : MyAddValServiceAdapter.OnClickListener{
            override fun onBuyNowClick(item: MyAddValServiceEntity.ListBean?) {
                //我的增值服务
                item?.let {
                    ToastUtil.showToast("支付->我的增值服务")
                    val ebikeId = item.ebikeId
                    val orderId = item.orderId
                    val valueAddedServiceId = item.valueAddedServiceId
                    startActivity(Intent(this@MyAddValServiceActivity,PayActivity::class.java).putExtra(Constants.BundleKey.EXTRA_PAY_EBIKEID,ebikeId).putExtra(Constants.BundleKey.EXTRA_PAY_ORDERID,orderId).putExtra(Constants.BundleKey.EXTRA_PAY_VALUEID,valueAddedServiceId))
                }
            }
        })
    } }

    private val viewModel by viewModels<AddValViewModel>()


    override fun getLayoutId(): Int = R.layout.activity_my_addval_service


    override fun bindViewModel() {
        binding.viewModel = viewModel
    }


    override fun initData(savedInstanceState: Bundle?) {
        setTitle<MyAddValServiceActivity>(UIUtils.getString(CR.string.common_title_my_addval_service)).onBack<MyAddValServiceActivity> {
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
        rv.adapter = myAddValAdapter

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

        viewModel.myAddValServiceList.observe(this, Observer {
            myAddValAdapter.setData(it)
        })


        viewModel.myAddValServiceRefresh.observe(this, Observer {
            myAddValAdapter.setData(it)
            finishRefresh()
        })
        viewModel.myAddValServiceLoadMore.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                myAddValAdapter.updataList(it)
            }
            finishLoadMore()
        })

        viewModel.getMyAddValServiceList()
    }


    private fun onRefreshData() {
        viewModel.onMyAddValServiceRefresh()
    }

    private fun onLoadMoreData() {
        if (viewModel.lastPage) {
            finishLoadMore()
        } else {
            viewModel.onMyAddValServiceLoadMore()
        }
    }

    //结束下拉刷新
    private fun finishRefresh() {
        refreshLayout?.let {
            if (it.isRefreshing) it.finishRefresh(300)
        }
    }

    //结束加载更多
    private fun finishLoadMore() {
        refreshLayout?.let {
            if (it.isLoading) it.finishLoadMore(300)
        }
    }
}