package net.hyntech.usual.ui.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_alarm_record.*
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.model.entity.AlarmRecordEntity
import net.hyntech.usual.R
import net.hyntech.common.R as CR
import net.hyntech.usual.databinding.ActivityAlarmRecordBinding
import net.hyntech.usual.ui.adapter.AlarmRecordAdapter
import net.hyntech.usual.vm.ControllerViewModel

class AlarmRecordActivity:BaseViewActivity<ActivityAlarmRecordBinding,ControllerViewModel>() {

    private val viewModel by viewModels<ControllerViewModel>()

    private val alarmRecordAdapter by lazy { AlarmRecordAdapter(this).apply {
        this.setListener(object : AlarmRecordAdapter.OnClickListener{ override fun onItemClick(item: AlarmRecordEntity.AtAlarmListBean?) {} }) } }

    override fun getLayoutId(): Int = R.layout.activity_alarm_record

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<AlarmRecordActivity>(UIUtils.getString(CR.string.common_title_alarm_record)).onBack<AlarmRecordActivity> {
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
        rv.adapter = alarmRecordAdapter

        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })

        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtil.showToast(it)
        })

        viewModel.alarmRecordList.observe(this, Observer {
            alarmRecordAdapter.setData(it)
        })
        viewModel.alarmListRefresh.observe(this, Observer {
            alarmRecordAdapter.setData(it)
            finishRefresh()
        })
        viewModel.alarmListLoadMore.observe(this, Observer {
            alarmRecordAdapter.updataList(it)
            finishLoadMore()
        })

        viewModel.getAlarmRecordList()
    }


    private fun onRefreshData(){
        viewModel.onAlarmRefreshData()
    }

    private fun onLoadMoreData(){
        if(viewModel.lastPage){
            finishLoadMore()
        }else{
            viewModel.onAlarmLoadMoreData()
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