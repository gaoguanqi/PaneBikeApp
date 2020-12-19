package net.hyntech.usual.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.lifecycle.Observer
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.model.entity.AddValInfoEntity
import net.hyntech.common.model.entity.SafeInfoEntity
import net.hyntech.common.utils.CommonUtils
import net.hyntech.usual.R
import net.hyntech.common.R as CR
import net.hyntech.usual.databinding.ActivityAddvalInfoBinding
import net.hyntech.usual.vm.AddValViewModel

class AddValInfoActivity:BaseViewActivity<ActivityAddvalInfoBinding,AddValViewModel>() {

    private var ebikeId:String?= ""
    private var valueAddedServiceId:String?= ""

    private val span: ForegroundColorSpan by lazy { ForegroundColorSpan(Color.parseColor("#333333")) }

    private val viewModel by viewModels<AddValViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_addval_info

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<AddValInfoActivity>(UIUtils.getString(CR.string.common_title_addval_info)).onBack<AddValInfoActivity> {
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

        viewModel.addValInfo.observe(this, Observer {
            setData(it)
        })

        viewModel.getAddValInfo(ebikeId,valueAddedServiceId)
    }
    private val priceTip:String = "服务费用："
    private fun setData(it: AddValInfoEntity) {
        binding.tvEbikeNo.text = "${it.ebike.ebikeNo}"
        binding.tvLabel.text = "${it.ebike.locatorNo}"
        binding.tvName.text = "${it.ebike.userName}"
        binding.tvPhone.text = "${it.ebike.phone}"
        binding.tvIdcard.text = CommonUtils.coverIDCard(it.ebike.idNo)
        binding.tvFrameNo.text = "${it.ebike.frameNo}"
        binding.tvMotor.text = "${it.ebike.engineNo}"
        binding.tvBrand.text = "${it.ebike.ebikeType}"
        binding.tvColor.text = "${it.ebike.ebikeColor}"
        binding.tvType.text = "${it.ebike.typeName}"
        binding.tvBuyPrice.text = "${it.ebike.price/100}"
        binding.tvBuyTime.text = CommonUtils.splitDate(it.ebike.buyTime)
        binding.tvRemark.text = "${it.ebike.remark}"
        binding.tvAddVal.text = "${it.valueAddedServiceOrder.valueAddedServiceName}"
        val beginTime:String = CommonUtils.splitDate(it.valueAddedServiceOrder.beginTime)
        val endTime:String = CommonUtils.splitDate(it.valueAddedServiceOrder.endTime)
        binding.tvRange.text = "${beginTime}至${endTime}"
        binding.tvServicePrice.text = "${it.valueAddedServiceOrder.payMoney/100}元"

        val content:String = "${priceTip}￥${it.valueAddedServiceOrder.payMoney/100}"
        val ss: SpannableString = SpannableString(content)
        ss.setSpan(span,0,priceTip.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        binding.tvPrice.text = ss

        //点击投保
        binding.tvSubmit.setBackgroundColor(UIUtils.getColor(CR.color.common_color_text))
        binding.tvSubmit.setOnClickListener { v->
            onSubmit(it)
        }
    }

    private fun onSubmit(bean: AddValInfoEntity) {
        val price = (bean.valueAddedServiceOrder.payMoney/100).toString()
        startActivity(Intent(this,PayActivity::class.java).putExtra(Constants.BundleKey.EXTRA_PRICE,price))
    }
}