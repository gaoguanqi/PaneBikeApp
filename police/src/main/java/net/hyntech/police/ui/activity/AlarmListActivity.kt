package net.hyntech.police.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_payment_state.*
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.ActivityAlarmListBinding
import net.hyntech.police.vm.AlarmViewModel

//报警信息(消息)
class AlarmListActivity:BaseViewActivity<ActivityAlarmListBinding,AlarmViewModel>() {

    private val viewModel by viewModels<AlarmViewModel>()


    override fun getLayoutId(): Int = R.layout.activity_alarm_list

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }


    override fun initData(savedInstanceState: Bundle?) {

        setTitle<AlarmListActivity>(UIUtils.getString(CR.string.common_title_alarm_info)).onBack<AlarmListActivity> {
            onFinish()
        }

        this.findViewById<ImageView>(R.id.iv_right)?.visibility = View.VISIBLE

        refreshLayout.setEnableRefresh(false)//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(true)//是否启用上拉加载功能
        refreshLayout.setOnRefreshListener { ref ->
            onRefreshData()
        }

        refreshLayout.setOnLoadMoreListener { ref ->
            onLoadMoreData()
        }

       // rv.layoutManager = LinearLayoutManager(this)
       // rv.adapter = registerAdapter



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
            if (refreshLayout.visibility == View.VISIBLE) {
                refreshLayout.visibility = View.GONE
            }
            if (vsEmpty.visibility == View.GONE) {
                vsEmpty.visibility = View.VISIBLE
            }
            refreshLayout.setEnableRefresh(false)//是否启用下拉刷新功能
        })

//        viewModel.registerList.observe(this, Observer {
//            if (refreshLayout.visibility == View.GONE) {
//                refreshLayout.visibility = View.VISIBLE
//            }
//            if (vsEmpty.visibility == View.VISIBLE) {
//                vsEmpty.visibility = View.GONE
//            }
//            registerAdapter.setData(it)
//            refreshLayout.setEnableRefresh(true)//是否启用下拉刷新功能
//        })
//        viewModel.registerListRefresh.observe(this, Observer {
//            registerAdapter.setData(it)
//            finishRefresh()
//        })
//        viewModel.registerListLoadMore.observe(this, Observer {
//            if (!it.isNullOrEmpty()) {
//                registerAdapter.updataList(it)
//            }
//            finishLoadMore()
//        })

        viewModel.getAlarmInfoList()
    }


    private fun onRefreshData() {
        //viewModel.onRegisterListRefresh(state.toString(), keyword)
    }

    private fun onLoadMoreData() {
        if (viewModel.lastPage) {
            finishLoadMore()
        } else {
            //viewModel.onRegisterListLoadMore(state.toString(), keyword)
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