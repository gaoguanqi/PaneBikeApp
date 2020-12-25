package net.hyntech.usual.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.Observer
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.widget.imgloader.ImageLoader
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.glide.GlideImageConfig
import net.hyntech.usual.R
import net.hyntech.common.R as CR
import net.hyntech.usual.databinding.ActivityAddvalDetailBinding
import net.hyntech.usual.vm.AddValViewModel

class AddValDetailActivity:BaseViewActivity<ActivityAddvalDetailBinding,AddValViewModel>() {

    private var ebikeId:String?= ""
    private var valueAddedServiceId:String?= ""


    private val viewModel by viewModels<AddValViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_addval_detail

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<AddValDetailActivity>(UIUtils.getString(CR.string.common_title_addval_detail)).onBack<AddValDetailActivity> {
            onFinish()
        }

        ebikeId = getBundleString(Constants.BundleKey.EXTRA_ID)
        valueAddedServiceId = getBundleString(Constants.BundleKey.EXTRA_ID_S)

        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })

        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtil.showToast(it)
        })

        viewModel.serviceDetail.observe(this, Observer {
            binding.tvName.text = "${it.valueAddedService.serviceName}"
            binding.tvPrice.text = "￥${it.valueAddedService.servicePrice/100}"
            binding.tvContent.text = "${it.valueAddedService.serviceDesc}"
            val timeType:String = it.valueAddedService.timeType
            var des = ""
            if(TextUtils.equals("day",timeType)){
                des = "${it.valueAddedService.termRange}天"
            }else if(TextUtils.equals("month",timeType)){
                des = "${it.valueAddedService.termRange}个月"
            }else if(TextUtils.equals("year",timeType)){
                des = "${it.valueAddedService.termRange}年"
            }
            binding.tvRange.text = "${des}"
            ImageLoader.getInstance().loadImage(
                BaseApp.instance,
                GlideImageConfig(it.valueAddedService.servicePic, binding.ivPic).also { config-> config.type = TransType.NORMAL })
        })

        binding.btnBuy.setOnClickListener {
            onClickProxy {
                startActivity(Intent(this,AddValInfoActivity::class.java).putExtra(Constants.BundleKey.EXTRA_ID,ebikeId).putExtra(Constants.BundleKey.EXTRA_ID_S,valueAddedServiceId))
            }
        }

        viewModel.getServiceDetails(ebikeId,valueAddedServiceId)
    }
}