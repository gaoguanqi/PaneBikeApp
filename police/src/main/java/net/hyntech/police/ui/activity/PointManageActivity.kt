package net.hyntech.police.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.View.NOT_FOCUSABLE
import android.widget.Button
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_point_manage.*
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.global.EventCode
import net.hyntech.common.model.entity.CollectorListEntity
import net.hyntech.common.widget.view.ClearEditText
import net.hyntech.police.R
import net.hyntech.police.databinding.ActivityPointManageBinding
import net.hyntech.common.R as CR
import net.hyntech.police.ui.adapter.PointManageAdapter
import net.hyntech.police.vm.PointManageViewModel

class PointManageActivity:BaseViewActivity<ActivityPointManageBinding,PointManageViewModel>() {

    private var etInput: ClearEditText? = null

    private var btnSearch: Button? = null

    private var keyword:String = ""
    private val viewModel by viewModels<PointManageViewModel>()

    private val pointManageAdapter by lazy { PointManageAdapter(this).apply {
        this.setListener(object : PointManageAdapter.OnClickListener{
            override fun onEditClick(item: CollectorListEntity.AtCollectorListBean?) {
                item?.let {
                    val bundle = Bundle()
                    bundle.putSerializable(Constants.BundleKey.EXTRA_OBJ,it)
                    // 0 - add  1 edit
                    bundle.putInt(Constants.BundleKey.EXTRA_TYPE,1)
                    startActivityForResult(Intent(this@PointManageActivity,PointEditActivity::class.java).putExtras(bundle),EventCode.EVENT_CODE_POINT)
                }
            } }) } }


    override fun getLayoutId(): Int = R.layout.activity_point_manage

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<PointManageActivity>(UIUtils.getString(CR.string.common_title_point_manage)).onBack<PointManageActivity> {
            onFinish()
        }

        etInput = findViewById(R.id.et_input)
        etInput?.hint = "请输入设备ID号、地址"
        etInput?.isClickable = true
        etInput?.isFocusable = false
        etInput?.isCursorVisible = false
        etInput?.setTextIsSelectable(false)

        btnSearch = findViewById(R.id.btn_search)
        btnSearch?.visibility = View.GONE
        etInput?.setOnClickListener {
            if(!UIUtils.isFastDoubleClick()){
                startActivity(Intent(this@PointManageActivity,SearchPointActivity::class.java))
            }
        }

        findViewById<Button>(R.id.btn_add)?.setOnClickListener {
            if(!UIUtils.isFastDoubleClick()){
                val bundle = Bundle()
                // 0 - add  1 edit
                bundle.putInt(Constants.BundleKey.EXTRA_TYPE,0)
                startActivityForResult(Intent(this@PointManageActivity,PointEditActivity::class.java).putExtras(bundle),EventCode.EVENT_CODE_POINT)
            }
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

        viewModel.collectorList.observe(this, Observer {
            pointManageAdapter.setData(it)
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
        viewModel.getCollectorList(keyword)
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                EventCode.EVENT_CODE_POINT ->{
                    viewModel.getCollectorList(keyword)
                }
            }
        }
    }
}