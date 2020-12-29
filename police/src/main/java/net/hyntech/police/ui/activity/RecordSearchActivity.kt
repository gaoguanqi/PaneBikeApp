package net.hyntech.police.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_payment_state.*
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.model.entity.RegisterListEntity
import net.hyntech.common.widget.view.ClearEditText
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.ActivityPaymentStateBinding
import net.hyntech.police.ui.adapter.EbikeRegisterAdapter
import net.hyntech.police.vm.PaymentViewModel

class RecordSearchActivity : BaseViewActivity<ActivityPaymentStateBinding, PaymentViewModel>() {

    private lateinit var state:String
    private var keyword: String = ""

    private var etInput: ClearEditText? = null

    private val registerAdapter by lazy {
        EbikeRegisterAdapter(this,state.toInt()).apply {
            this.setListener(object : EbikeRegisterAdapter.OnClickListener {
                override fun onEditClick(item: RegisterListEntity.AtEbikeListBean?) {
                    item?.let {
                        //缴费确认
                        startActivity(Intent(this@RecordSearchActivity,PaymentConfirmActivity::class.java).putExtra(Constants.BundleKey.EXTRA_ID,it.ebikeId))
                    }
                }
            })
        }
    }


    private val viewModel by viewModels<PaymentViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_payment_state

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {

        setTitle<RecordSearchActivity>(UIUtils.getString(CR.string.common_title_register_list)).onBack<RecordSearchActivity> {
            onFinish()
        }

        state = getBundleString(Constants.BundleKey.EXTRA_CONTENT)?:"0"

        etInput = findViewById(R.id.et_input)
        etInput?.hint = "请输入车牌号、姓名"

        this.findViewById<Button>(R.id.btn_search)?.setOnClickListener {
            onClickProxy {
                val input = etInput?.text.toString().trim()
                if(TextUtils.isEmpty(input)){
                    ToastUtil.showToast("请输入车牌号、姓名")
                    return@onClickProxy
                }
                keyword = input
                viewModel.getRegisterList(state, keyword)
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
        rv.adapter = registerAdapter

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

        viewModel.registerList.observe(this, Observer {
            if (refreshLayout.visibility == View.GONE) {
                refreshLayout.visibility = View.VISIBLE
            }
            if (vsEmpty.visibility == View.VISIBLE) {
                vsEmpty.visibility = View.GONE
            }
            registerAdapter.setData(it)
            refreshLayout.setEnableRefresh(true)//是否启用下拉刷新功能
        })
        viewModel.registerListRefresh.observe(this, Observer {
            registerAdapter.setData(it)
            finishRefresh()
        })
        viewModel.registerListLoadMore.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                registerAdapter.updataList(it)
            }
            finishLoadMore()
        })
    }


    private fun onRefreshData() {
        viewModel.onRegisterListRefresh(state!!, keyword)
    }

    private fun onLoadMoreData() {
        if (viewModel.lastPage) {
            finishLoadMore()
        } else {
            viewModel.onRegisterListLoadMore(state!!, keyword)
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