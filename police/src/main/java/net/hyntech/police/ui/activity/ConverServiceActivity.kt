package net.hyntech.police.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ScreenUtils
import kotlinx.android.synthetic.main.activity_conver_service.*
import kotlinx.android.synthetic.main.activity_conver_service.refreshLayout
import kotlinx.android.synthetic.main.activity_conver_service.rv
import kotlinx.android.synthetic.main.activity_conver_service.vsEmpty
import kotlinx.android.synthetic.main.activity_payment_state.*
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.model.entity.ServiceLoaderEntity
import net.hyntech.common.model.entity.ServiceTypeEntity
import net.hyntech.common.ui.adapter.ShopLoaderAdapter
import net.hyntech.common.ui.adapter.ShopTypeAdapter
import net.hyntech.common.widget.popu.ServiceTypePopu
import net.hyntech.common.widget.view.ClearEditText
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.ActivityConverServiceBinding
import net.hyntech.police.ui.adapter.ConverServiceAdapter
import net.hyntech.police.vm.ConverServiceViewModel

//便民服务
class ConverServiceActivity:BaseViewActivity<ActivityConverServiceBinding, ConverServiceViewModel>() {
    private var keyword:String = ""
    private var shopType:String = ""
    private var createId:String = ""

    private var etInput: ClearEditText? = null
    private var layoutTitle: ConstraintLayout? = null
    private val serviceAdapter:ConverServiceAdapter by lazy { ConverServiceAdapter(this) }

    private val loaderAdapter by lazy { ShopLoaderAdapter(this).apply {
        this.setListener(object :ShopLoaderAdapter.OnClickListener{
            override fun onItemClick(item: ServiceLoaderEntity.UploaderListBean) {
                ToastUtil.showToast("-->>${item.createName}")
            }
        })
    } }
    private val typeAdapter by lazy { ShopTypeAdapter(this).apply {
        val list:List<ServiceTypeEntity> = listOf(
            ServiceTypeEntity("销售门店","1",false),
            ServiceTypeEntity("维修站","2",false),
            ServiceTypeEntity("充电站","3",false))
        this.setData(list)
        this.setListener(object :ShopTypeAdapter.OnClickListener{
            override fun onItemClick(item: ServiceTypeEntity) {
                ToastUtil.showToast("-->>${item.name}")
            }
        })
    } }
    private val serviceTypePopu by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { ServiceTypePopu(this,
        loaderAdapter,
        typeAdapter,
        mWidth = ScreenUtils.getScreenWidth(),
        mHeight = (ScreenUtils.getScreenHeight() * 0.36).toInt()).apply {
        this.setListener(object :ServiceTypePopu.OnClickListener{
            override fun onResetClick() {
                shopType = ""
                createId = ""
            }
            override fun onFinishClick(createId: String, shopType: String) {

                ToastUtil.showToast("createId->>${createId} -- shopType->>${shopType}")
            }
        })
    } }


    private val viewModel by viewModels<ConverServiceViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_conver_service


    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<ConverServiceActivity>(UIUtils.getString(CR.string.common_title_conver_service)).onBack<ConverServiceActivity> {
            onFinish()
        }.setRightTxt<ConverServiceActivity>("")

        layoutTitle = this.findViewById(R.id.layout_title)
        etInput = this.findViewById(R.id.et_input)
        etInput?.hint = "请输网点名称、地址"

        this.findViewById<Button>(R.id.btn_search)?.setOnClickListener {
            onClickProxy {
                val input = etInput?.text.toString().trim()
                if(TextUtils.isEmpty(input)){
                    ToastUtil.showToast("请输网点名称、地址")
                    return@onClickProxy
                }
                keyword = input
                viewModel.getServiceList(keyword,shopType,createId)
            }
        }

        this.findViewById<ImageView>(R.id.iv_right).apply {
            this.visibility = View.VISIBLE
            this.setImageDrawable(UIUtils.getDrawable(CR.drawable.ic_add))
            this.setOnClickListener {
                onClickProxy {
                    ToastUtil.showToast("添加")
                }
            }
        }
        this.findViewById<TextView>(R.id.tv_right).apply {
            this.setCompoundDrawablesWithIntrinsicBounds(UIUtils.getDrawable(CR.drawable.ic_filter),null,null,null)
            this.setOnClickListener {
                onClickProxy {
                    showInfoPopu()
                }
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
        rv.adapter = serviceAdapter


        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })

        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtil.showToast(it)
        })

        viewModel.loaderList.observe(this, Observer {
            it?.let { list ->
                loaderAdapter.setData(list)
            }
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

        viewModel.serviceShopList.observe(this, Observer {
            if (refreshLayout.visibility == View.GONE) {
                refreshLayout.visibility = View.VISIBLE
            }
            if (vsEmpty.visibility == View.VISIBLE) {
                vsEmpty.visibility = View.GONE
            }
            serviceAdapter.setData(it)
            refreshLayout.setEnableRefresh(true)//是否启用下拉刷新功能
        })

        viewModel.serviceShopRefresh.observe(this, Observer {
            serviceAdapter.setData(it)
            finishRefresh()
        })
        viewModel.serviceShopLoadMore.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                serviceAdapter.updataList(it)
            }
            finishLoadMore()
        })

        viewModel.getServiceUploader()
        viewModel.getServiceList(keyword,shopType,createId)
    }


    private fun showInfoPopu(){
        serviceTypePopu.showPopupWindow(layoutTitle)
    }


    private fun onRefreshData() {
        viewModel.onServiceListRefresh(keyword,shopType,createId)
    }

    private fun onLoadMoreData() {
        if (viewModel.lastPage) {
            finishLoadMore()
        } else {
            viewModel.onServiceListLoadMore(keyword,shopType,createId)
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