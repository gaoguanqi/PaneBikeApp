package net.hyntech.usual.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.ui.adapter.ClaimProcessAdapter
import net.hyntech.common.widget.imgloader.ImageLoader
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.glide.GlideImageConfig
import net.hyntech.usual.R
import net.hyntech.common.R as CR
import net.hyntech.usual.databinding.ActivityServiceDetailBinding
import net.hyntech.usual.vm.ControllerViewModel

//服务详情
class ServiceDetailActivity:BaseViewActivity<ActivityServiceDetailBinding,ControllerViewModel>() {

    private val viewModel by viewModels<ControllerViewModel>()

    private var id:String ? = ""

    private val claimProcessAdapter:ClaimProcessAdapter by lazy { ClaimProcessAdapter(this) }

    override fun getLayoutId(): Int = R.layout.activity_service_detail

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<ServiceDetailActivity>(UIUtils.getString(CR.string.common_title_service_detail)).onBack<ServiceDetailActivity> {
            onFinish()
        }

        id = getBundleString(Constants.BundleKey.EXTRA_ID)

        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })

        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtil.showToast(it)
        })

        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = claimProcessAdapter

        viewModel.serviceDetail.observe(this, Observer {
            binding.tvName.text = "${it.insuranceName}"
            binding.tvPrice.text = "￥${it.insurancePrice/100}"
            binding.tvRange.text = "${it.termRange}年"
            binding.tvArea.text = "${it.orgName}"

            ImageLoader.getInstance().loadImage(
                BaseApp.instance,
                GlideImageConfig(it.insurancePic, binding.ivPic).also { config-> config.type = TransType.NORMAL })
        })

        viewModel.claimProcess.observe(this, Observer {
            claimProcessAdapter.setData(it)
        })

        viewModel.insuranceCoverange.observe(this, Observer {
            binding.tvLimit.text = it
        })

        binding.btnBuy.setOnClickListener {
            onClickProxy {
                val content:String? = viewModel.insuranceCoverange.value
                startActivity(Intent(this,SafeInfoActivity::class.java).putExtra(Constants.BundleKey.EXTRA_ID,id).putExtra(Constants.BundleKey.EXTRA_CONTENT,content))
            }
        }


        viewModel.getServiceDetails(id)
        viewModel.getClaimProcess(id)
        viewModel.getInsuranceCoverange(id)
    }
}