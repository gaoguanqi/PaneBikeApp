package net.hyntech.police.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_alarm_search.*
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.model.entity.AlarmInfoEntity
import net.hyntech.common.widget.view.ClearEditText
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.ActivityAlarmSearchBinding
import net.hyntech.police.ui.adapter.AlarmInfoAdapter
import net.hyntech.police.vm.AlarmViewModel

class AlarmSearchActivity:BaseViewActivity<ActivityAlarmSearchBinding, AlarmViewModel>() {

    private var keyword:String = ""

    private var etInput: ClearEditText? = null

    private val alarmInfoAdapter: AlarmInfoAdapter by lazy {
        AlarmInfoAdapter(this).apply {
            this.setListener(object : AlarmInfoAdapter.OnClickListener{
                override fun onItemClick(pos:Int,item: AlarmInfoEntity.AlarmInfoListBean?) {
                    item?.let {
                        //按原来代码的逻辑处理
                        // 点击item 时先判断消息是否为已读
                        //如果已读 直接进去 车辆查找页面
                        //如果是未读 先提交接口为已读 再 进入车辆查找页面
                        val state = it.state
                        if(state == 0){ //未读
                            onMarkRead(pos,it)
                        }else{
                            startActivity(Intent(this@AlarmSearchActivity,FindEbikeActivity::class.java).putExtra(Constants.BundleKey.EXTRA_OBJ,it))
                        }
                    }
                }
            })
        }
    }


    private val viewModel by viewModels<AlarmViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_alarm_search

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        onBack<AlarmSearchActivity> {
            onFinish()
        }

        etInput = this.findViewById(R.id.et_input)
        etInput?.hint = "请输入车牌号、地址"
        setRightTxt<AlarmSearchActivity>(UIUtils.getString(CR.string.common_search)).onBack<AlarmSearchActivity>{
            onFinish(true)
        }.onSide<AlarmSearchActivity> {
            val input:String = etInput?.text?.trim().toString()
            if(!TextUtils.isEmpty(input)){
                searchAlarm(input)
            }else{
                ToastUtil.showToast("请输入车牌号、地址")
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

        viewModel.notifyPosition.observe(this, Observer {
            if(alarmInfoAdapter.itemCount >= it){
                alarmInfoAdapter.notifyItemChanged(it)
            }
        })

        viewModel.markRead.observe(this, Observer {
            startActivity(Intent(this@AlarmSearchActivity,FindEbikeActivity::class.java).putExtra(Constants.BundleKey.EXTRA_OBJ,it))
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
    }

    private fun onRefreshData() {
        viewModel.onAlarmInfoRefresh(keyword)
    }

    private fun onLoadMoreData() {
        if (viewModel.lastPage) {
            finishLoadMore()
        } else {
            viewModel.onAlarmInfoLoadMore(keyword)
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


    private fun onMarkRead(pos:Int,entity:AlarmInfoEntity.AlarmInfoListBean){
        entity.state = 1
        viewModel.onMarkRead(pos,entity)
    }

    private fun searchAlarm(input: String) {
        keyword = input
        viewModel.getAlarmInfoList(keyword)
    }
}