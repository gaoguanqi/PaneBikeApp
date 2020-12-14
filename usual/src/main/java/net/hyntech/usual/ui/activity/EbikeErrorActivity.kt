package net.hyntech.usual.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_ebike_error.*
import kotlinx.android.synthetic.main.activity_ebike_error.refreshLayout
import kotlinx.android.synthetic.main.activity_ebike_error.rv
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.model.entity.EbikeErrorEntity
import net.hyntech.common.model.vo.BundleAlarmVo
import net.hyntech.common.widget.dialog.CommonDialog
import net.hyntech.usual.R
import net.hyntech.usual.databinding.ActivityEbikeErrorBinding
import net.hyntech.usual.ui.adapter.EbikeErrorAdapter
import net.hyntech.common.R as CR
import net.hyntech.usual.vm.ControllerViewModel

/**
 * 首页 -> 车辆异常信息
 */
class EbikeErrorActivity:BaseViewActivity<ActivityEbikeErrorBinding,ControllerViewModel>() {

    private var currentItem:EbikeErrorEntity.AlarmExceptionLogListBean? = null

    private val viewModel by viewModels<ControllerViewModel>()
    private val ebikeErrorAdapter by lazy { EbikeErrorAdapter(this).apply {
        this.setListener(object :EbikeErrorAdapter.OnClickListener{
        override fun onItemClick(item: EbikeErrorEntity.AlarmExceptionLogListBean?) {}

        override fun onIgnoreClick(item: EbikeErrorEntity.AlarmExceptionLogListBean?) {
            currentItem = item
            if(!ignoreDialog.isShowing){
                ignoreDialog.show()
            }
        }

        override fun onAlarmClick(item: EbikeErrorEntity.AlarmExceptionLogListBean?) {
                item?.let {
                    if(it.state == 1){
                        ToastUtil.showToast("该车辆已经报警,无需重复报警")
                    }else{
                        val array = java.util.ArrayList<BundleAlarmVo>()
                        val vo:BundleAlarmVo = BundleAlarmVo()
                        vo.ebikeId = it.ebikeId
                        vo.ebikeNo = it.ebikeNo
                        vo.name = it.name
                        vo.phone = it.phone
                        vo.address = it.addr
                        array.add(vo)
                        val bundle:Bundle = Bundle()
                        bundle.putInt(Constants.BundleKey.EXTRA_TYPE,2)
                        bundle.putSerializable(Constants.BundleKey.EXTRA_OBJ,array)
                        startActivity(Intent(this@EbikeErrorActivity,AkeyAlarmActivity::class.java).putExtras(bundle))
                    }
                }
             }
        })
    } }

    private val ignoreDialog: CommonDialog by lazy { CommonDialog(this,
        UIUtils.getString(CR.string.common_tip),
        UIUtils.getString(net.hyntech.common.R.string.common_ignore_content),
        UIUtils.getString(net.hyntech.common.R.string.common_no),
        UIUtils.getString(net.hyntech.common.R.string.common_yes),object :
            CommonDialog.OnClickListener{
            override fun onCancleClick() {}
            override fun onConfirmClick() { ignoreConfirm() } },isCancelable = true) }

    override fun getLayoutId(): Int = R.layout.activity_ebike_error

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    @SuppressLint("RestrictedApi")
    override fun initData(savedInstanceState: Bundle?) {

        setTitle<EbikeErrorActivity>(UIUtils.getString(CR.string.common_title_ebike_error)).setRightTxt<EbikeErrorActivity>(UIUtils.getString(CR.string.common_title_alarm_record)
        ).onSide<EbikeErrorActivity> {
            startActivity(Intent(this,AlarmRecordActivity::class.java))
        }.onBack<EbikeErrorActivity> {
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
        rv.adapter = ebikeErrorAdapter

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
            refreshLayout.visibility = View.GONE
            vsEmpty.inflate()
        })

        viewModel.ebikeErrorList.observe(this, Observer {
            ebikeErrorAdapter.setData(it)
        })
        viewModel.ebikeListRefresh.observe(this, Observer {
            ebikeErrorAdapter.setData(it)
            finishRefresh()
        })
        viewModel.ebikeListLoadMore.observe(this, Observer {
            ebikeErrorAdapter.updataList(it)
            finishLoadMore()
        })
        viewModel.ignoreEvent.observe(this, Observer {
            //忽略后刷新数据
            viewModel.onEbikeRefreshData()
        })

        viewModel.getEbikeErrorList()
    }

    private fun onRefreshData(){
        viewModel.onEbikeRefreshData()
    }

    private fun onLoadMoreData(){
        if(viewModel.lastPage){
            finishLoadMore()
        }else{
            viewModel.onEbikeLoadMoreData()
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


    private fun ignoreConfirm(){
        ToastUtil.showToast(currentItem?.createTime)
        currentItem?.let {
            viewModel.ignoreEbikeError(it.alarmLogId)
        }
    }
}