package net.hyntech.police.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search_point.*
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.model.entity.CollectorListEntity
import net.hyntech.common.widget.view.ClearEditText
import net.hyntech.police.R
import net.hyntech.police.databinding.ActivitySearchPointBinding
import net.hyntech.police.ui.adapter.PointManageAdapter
import net.hyntech.police.vm.PointManageViewModel

class SearchPointActivity:BaseViewActivity<ActivitySearchPointBinding,PointManageViewModel>() {

    private var etInput:ClearEditText? = null
    private var llRight:LinearLayout? = null
    private var tvRight:TextView? = null

    private var keyword:String = ""
    private val viewModel by viewModels<PointManageViewModel>()

    private val pointManageAdapter by lazy { PointManageAdapter(this).apply {
        this.setListener(object : PointManageAdapter.OnClickListener{
            override fun onEditClick(item: CollectorListEntity.AtCollectorListBean?) {

            } }) } }


    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun getLayoutId(): Int = R.layout.activity_search_point

    override fun initData(savedInstanceState: Bundle?) {
        onBack<SearchPointActivity> {
            onFinish()
        }

        etInput = findViewById(R.id.et_input)
        llRight = findViewById(R.id.ll_right)
        tvRight = findViewById(R.id.tv_right)
        tvRight?.text = "搜索"
        llRight?.setOnClickListener {
            if(!UIUtils.isFastDoubleClick()){
                val input:String? = etInput?.text.toString().trim()
                if(TextUtils.isEmpty(input)){
                    ToastUtil.showToast("请输入设备ID号、地址")
                    return@setOnClickListener
                }
                keyword = input!!
                viewModel.getCollectorList(keyword)
            }
        }


        refreshLayout.setEnableRefresh(false)//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(true)//是否启用上拉加载功能
        refreshLayout.setOnRefreshListener { ref ->
            onRefreshData()
        }

        refreshLayout.setOnLoadMoreListener { ref ->
            onLoadMoreData()
        }

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = pointManageAdapter

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
            if(refreshLayout.visibility == View.VISIBLE){refreshLayout.visibility = View.GONE}
            if(vsEmpty.visibility == View.GONE){vsEmpty.visibility = View.VISIBLE}
            refreshLayout.setEnableRefresh(false)//是否启用下拉刷新功能
        })

        viewModel.collectorList.observe(this, Observer {
            if(refreshLayout.visibility == View.GONE){refreshLayout.visibility = View.VISIBLE}
            if(vsEmpty.visibility == View.VISIBLE){vsEmpty.visibility = View.GONE}
            pointManageAdapter.setData(it)
            refreshLayout.setEnableRefresh(true)//是否启用下拉刷新功能
        })
        viewModel.collectorListRefresh.observe(this, Observer {
            pointManageAdapter.setData(it)
            finishRefresh()
        })
        viewModel.collectorListLoadMore.observe(this, Observer {
            if(!it.isNullOrEmpty()){
                pointManageAdapter.updataList(it)
            }
            finishLoadMore()
        })
    }

    private fun onRefreshData(){
        viewModel.onCollectorListRefresh(keyword)
    }

    private fun onLoadMoreData(){
        if(viewModel.lastPage){
            finishLoadMore()
        }else{
            viewModel.onCollectorListLoadMore(keyword)
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