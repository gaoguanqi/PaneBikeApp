package net.hyntech.usual.ui.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_addval_service.*
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.model.entity.AddValServiceEntity
import net.hyntech.usual.R
import net.hyntech.common.R as CR
import net.hyntech.usual.databinding.ActivityAddvalServiceBinding
import net.hyntech.usual.ui.adapter.AddValServiceAdapter
import net.hyntech.usual.vm.AddValViewModel

//增值服务列表
class AddValServiceActivity:BaseViewActivity<ActivityAddvalServiceBinding,AddValViewModel>() {

    private val viewModel by viewModels<AddValViewModel>()
    private var id:String? = ""
    private val addValServiceAdapter by lazy { AddValServiceAdapter(this).apply {
        this.setListener(object : AddValServiceAdapter.OnClickListener{ override fun onItemClick(item: AddValServiceEntity.ValueAddedServiceListBean?) {
            item?.let {
                item?.let {
                    ToastUtil.showToast(it.serviceName)
                }
            } } }) } }

    override fun getLayoutId(): Int = R.layout.activity_addval_service

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<AddValServiceActivity>(UIUtils.getString(CR.string.common_title_addval_service)).onBack<AddValServiceActivity> {
            onFinish()
        }

        id = getBundleString(Constants.BundleKey.EXTRA_ID)

        refreshLayout.setEnableRefresh(true)//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(true)//是否启用上拉加载功能
        refreshLayout.setOnRefreshListener { ref ->
            onRefreshData()
        }

        refreshLayout.setOnLoadMoreListener { ref ->
            onLoadMoreData()
        }

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = addValServiceAdapter

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

        viewModel.addValServiceList.observe(this, Observer {
            addValServiceAdapter.setData(it)
        })
        viewModel.addValServiceRefresh.observe(this, Observer {
            addValServiceAdapter.setData(it)
            finishRefresh()
        })
        viewModel.addValServiceLoadMore.observe(this, Observer {
            if(!it.isNullOrEmpty()){
                addValServiceAdapter.updataList(it)
            }
            finishLoadMore()
        })

        viewModel.getAddValServiceList(id)

    }

    private fun onRefreshData(){
        viewModel.onAddValServiceRefresh(id)
    }

    private fun onLoadMoreData(){
        if(viewModel.lastPage){
            finishLoadMore()
        }else{
            viewModel.onAddValServiceLoadMore(id)
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