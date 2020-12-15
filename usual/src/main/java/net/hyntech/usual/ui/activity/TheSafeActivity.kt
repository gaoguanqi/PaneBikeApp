package net.hyntech.usual.ui.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_alarm_record.*
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.model.entity.AlarmRecordEntity
import net.hyntech.common.model.entity.ServiceSafeEntity
import net.hyntech.usual.R
import net.hyntech.common.R as CR
import net.hyntech.usual.databinding.ActivityTheSafeBinding
import net.hyntech.usual.ui.adapter.TheSafeAdapter
import net.hyntech.usual.vm.ControllerViewModel

class TheSafeActivity:BaseViewActivity<ActivityTheSafeBinding,ControllerViewModel>() {

    private val viewModel by viewModels<ControllerViewModel>()

    private var orgId:String? = ""

    private val theSafeAdapter by lazy { TheSafeAdapter(this).apply {
        this.setListener(object : TheSafeAdapter.OnClickListener{ override fun onItemClick(item: ServiceSafeEntity.ServicePackageListBean?) {} }) } }


    override fun getLayoutId(): Int = R.layout.activity_the_safe

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<TheSafeActivity>(UIUtils.getString(CR.string.common_title_the_safe)).onBack<TheSafeActivity> {
            onFinish()
        }
        orgId = getBundleString(Constants.BundleKey.EXTRA_CONTENT)

        refreshLayout.setEnableRefresh(true)//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(true)//是否启用上拉加载功能
        refreshLayout.setOnRefreshListener { ref ->
            onRefreshData()
        }

        refreshLayout.setOnLoadMoreListener { ref ->
            onLoadMoreData()
        }

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = theSafeAdapter

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

        viewModel.serviceList.observe(this, Observer {
            theSafeAdapter.setData(it)
        })
        viewModel.serviceListRefresh.observe(this, Observer {
            theSafeAdapter.setData(it)
            finishRefresh()
        })
        viewModel.serviceListLoadMore.observe(this, Observer {
            if(!it.isNullOrEmpty()){
                theSafeAdapter.updataList(it)
            }
            finishLoadMore()
        })

        viewModel.getServiceList(orgId)

    }


    private fun onRefreshData(){
        viewModel.onServiceRefreshData(orgId)
    }

    private fun onLoadMoreData(){
        if(viewModel.lastPage){
            finishLoadMore()
        }else{
            viewModel.onServiceLoadMoreData(orgId)
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