package net.hyntech.police.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ScreenUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.model.entity.ServiceLoaderEntity
import net.hyntech.common.model.entity.ServiceTypeEntity
import net.hyntech.common.provider.ARouterConstants
import net.hyntech.common.ui.adapter.ShopLoaderAdapter
import net.hyntech.common.ui.adapter.ShopTypeAdapter
import net.hyntech.common.widget.popu.EbikeInfoPopu
import net.hyntech.common.widget.popu.ServiceTypePopu
import net.hyntech.common.widget.view.ClearEditText
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.ActivityConverServiceBinding
import net.hyntech.police.vm.ConverServiceViewModel

//便民服务
class ConverServiceActivity:BaseViewActivity<ActivityConverServiceBinding, ConverServiceViewModel>() {

    private var etInput: ClearEditText? = null
    private var layoutTitle: ConstraintLayout? = null

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
        mHeight = (ScreenUtils.getScreenHeight() * 0.36).toInt()) }


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


        viewModel.getServiceUploader()

    }


    private fun showInfoPopu(){
        serviceTypePopu.showPopupWindow(layoutTitle)
    }

}