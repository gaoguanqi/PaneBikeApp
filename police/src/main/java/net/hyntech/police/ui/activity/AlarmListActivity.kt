package net.hyntech.police.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_payment_state.*
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.model.entity.AlarmInfoEntity
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.ActivityAlarmListBinding
import net.hyntech.police.ui.adapter.AlarmInfoAdapter
import net.hyntech.police.vm.AlarmViewModel

//报警信息(消息)
class AlarmListActivity:BaseViewActivity<ActivityAlarmListBinding,AlarmViewModel>() {

    private val alarmInfoAdapter:AlarmInfoAdapter by lazy {
        AlarmInfoAdapter(this).apply {
            this.setListener(object :AlarmInfoAdapter.OnClickListener{
                override fun onItemClick(item: AlarmInfoEntity.AlarmInfoListBean?) {
                    item?.let {
                        startActivity(Intent(this@AlarmListActivity,FindEbikeActivity::class.java).putExtra(Constants.BundleKey.EXTRA_OBJ,it))
                    }
                }
            })
        }
    }
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

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = alarmInfoAdapter

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

        viewModel.alarmInfoList.observe(this, Observer {
            if (refreshLayout.visibility == View.GONE) {
                refreshLayout.visibility = View.VISIBLE
            }
            if (vsEmpty.visibility == View.VISIBLE) {
                vsEmpty.visibility = View.GONE
            }
            alarmInfoAdapter.setData(it)
            refreshLayout.setEnableRefresh(true)//是否启用下拉刷新功能
        })
        viewModel.alarmInfoRefresh.observe(this, Observer {
            alarmInfoAdapter.setData(it)
            finishRefresh()
        })
        viewModel.alarmInfoLoadMore.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                alarmInfoAdapter.updataList(it)
            }
            finishLoadMore()
        })

        viewModel.getAlarmInfoList()
    }


    private fun onRefreshData() {
        viewModel.onAlarmInfoRefresh()
    }

    private fun onLoadMoreData() {
        if (viewModel.lastPage) {
            finishLoadMore()
        } else {
            viewModel.onAlarmInfoLoadMore()
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